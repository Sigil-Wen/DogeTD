/**[Store.java]
 * Store creates the GUI store in which towers are bought as well as displaying money and health
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */
import java.awt.*;
public class Store {

    //Store GUI Variables
    private int shopWidth = 8;
    private int buttonSize = 52;
    private int cellSpace = 2;
    private int awayFromRoom = 25;
    private int iconSize = 20;
    private int iconSpace = 3;
    private int iconTextY = 15;
    private int itemIn = 4;

    private int heldID = -1; //ID of tower held
    private int realID = -1;//real ID of tower held

    private int[] buttonID = {Value.airMario ,Value.airCreeper,Value.airMad,Value.airPepe,Value.airStar,Value.airGD,Value.airBruh,Value.airCancel}; //Towers and Icons used in the store bar
    private int[] buttonPrice = {10,25,50,75,100,150,250,0}; //store prices

    private Rectangle[] button = new Rectangle[shopWidth]; //Array of Rectangles for each button
    private Rectangle buttonHealth; //Rectangle to display health
    private Rectangle buttonCoins; //Rectangle to display money

    private boolean holdsItem = false; //boolean variable to store if the player is holding an item

    /**[Store]
     * Constructor for Store, runs define()
     */
    public Store() {
        define();
    }

    /**[click]
     * checks mouse clicks to see if the mouse position in on a store item to be bought, or is holding a placeable tower to be placed or canceled
     * @param mouseButton takes in button clicked
     */
    public void click(int mouseButton){
        if(mouseButton == 1){//left mouse button click
            for(int i = 0; i<button.length; i++){
                if(buttonID[i] !=Value.airAir){
                    if(button[i].contains(Screen.mse)){
                        if(buttonID[i] == Value.airCancel) { //if selected item is Cancel
                            holdsItem = false;//deselects item
                            heldID = Value.airAir; //sets held to transparent
                     }else{ //if selected item is a buyable item
                            heldID = buttonID[i];//sets heldID to buttonID[i]
                            realID = i; //sets realID to i
                            holdsItem = true; //sets holdsItem to true
                        }
                    }
                }

            }
            if(holdsItem){//if the mouse if holding an item when clicked
                if(Screen.money >= buttonPrice[realID]){ //checks to see if player can afford the tower
                    for(int y = 0; y <Screen.room.blocks.length;y++){
                        for(int x = 0; x <Screen.room.blocks[0].length;x++){
                            if(Screen.room.blocks[y][x].contains(Screen.mse)){ //if the block contains the mouse and the block isn't a road or has a tower, it places the tower
                                if(Screen.room.blocks[y][x].groundID != Value.groundRoad && Screen.room.blocks[y][x].airID == Value.airAir ){
                                    Screen.room.blocks[y][x].airID = heldID; //places tower
                                    Screen.money -= buttonPrice[realID]; //deducts price from money
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * [define]
     * defines each button, health, and coins when store is initialized
     */
    public void define(){
        for (int i = 0; i < button.length; i++){
            button[i] = new Rectangle((Screen.myWidth/2)- (shopWidth*(buttonSize+cellSpace)/2) + ((buttonSize+cellSpace)*i), (Screen.room.blocks[Screen.room.worldHeight-1][0].y)+Screen.room.blockSize+awayFromRoom, buttonSize, buttonSize);
        }
        //defines button for health and coins
        buttonHealth = new Rectangle(Screen.room.blocks[0][0].x - 1, button[0].y, iconSize, iconSize );
        buttonCoins = new Rectangle(Screen.room.blocks[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize );

    }

    /**[draw]
     * Draws the store
     * @param g Graphics
     */
    public void draw (Graphics g) {
        for (int i = 0; i < button.length; i++) { //loops through each button to draw
            if (button[i].contains(Screen.mse)){ //if the mouse hovers over the button within the store
                g.setColor(new Color(255,255,255,150)); //new transparent white color
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height); //draws the button with a selection glow
            }
            g.drawImage(Screen.tileset_res[0],button[i].x, button[i].y, button[i].width, button[i].height, null); //draws cell boarder
            if(buttonID[i] != Value.airAir) { //if item isn't empty, draws the tower
                g.drawImage(Screen.tileset_air[buttonID[i]],button[i].x +itemIn, button[i].y + itemIn, button[i].width - (itemIn*2), button[i].height - itemIn*2, null);
            }
            if(buttonPrice[i] > 0) { //draws the price of the tower
                g.setColor(new Color(255,255,255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("$" + buttonPrice[i], button[i].x +itemIn, button[i].y + itemIn +10);
            }

        }
        g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null); //draws the Health icon
        g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null); //draws the money icon
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255,255,255));
        g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width+ iconSpace , buttonHealth.y + iconTextY ); //draws value of health
        g.drawString("" + Screen.money , buttonCoins.x + buttonCoins.width+ iconSpace , buttonCoins.y + iconTextY ); //draws value of money

        if(holdsItem){ //draws a tower on cursor if selected
            g.drawImage(Screen.tileset_air[heldID], Screen.mse.x + itemIn -(button[0].width-(itemIn*2))/2, Screen.mse.y + itemIn, button[0].width-(itemIn*2), button[0].height-(itemIn*2), null );
        }
    }
}
