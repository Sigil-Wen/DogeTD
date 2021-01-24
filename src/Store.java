import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.*;
public class Store {
    public static int shopWidth = 8;
    public static int buttonSize = 52;
    public static int cellSpace = 2;
    public static int awayFromRoom = 25;
    public static int iconSize = 20;
    public static int iconSpace = 3;
    public static int iconTextY = 15;
    public static int itemIn = 4;
    public static int heldID = -1;
    public static int realID = -1;
    public static int[] buttonID = {Value.airMario ,Value.airCreeper,Value.airMad,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airCancel};
    public static int[] buttonPrice = {10,25,50,0,0,0,0,0};

    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonCoins;

    public boolean holdsItem = false;

    public Store() {
        define();
    }

    public void click(int mouseButton){
        if(mouseButton == 1){//left mouse button click
            for(int i = 0; i<button.length; i++){
                if(buttonID[i] !=Value.airAir){
                if(button[i].contains(Screen.mse)){
                    if(buttonID[i] == Value.airCancel) {
                        holdsItem = false;
                        heldID = Value.airAir;
                    }else{
                        heldID = buttonID[i];
                        realID = i;
                        holdsItem = true;
                    }
                }
                }

            }
            if(holdsItem){
                if(Screen.money >= buttonPrice[realID]){
                    for(int y = 0; y <Screen.room.blocks.length;y++){
                        for(int x = 0; x <Screen.room.blocks[0].length;x++){
                            if(Screen.room.blocks[y][x].contains(Screen.mse)){
                                if(Screen.room.blocks[y][x].groundID != Value.groundRoad && Screen.room.blocks[y][x].airID == Value.airAir ){
                                    Screen.room.blocks[y][x].airID = heldID;
                                    Screen.money -= buttonPrice[realID];
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void define(){
        for (int i = 0; i < button.length; i++){
            button[i] = new Rectangle((Screen.myWidth/2)- (shopWidth*(buttonSize+cellSpace)/2) + ((buttonSize+cellSpace)*i), (Screen.room.blocks[Screen.room.worldHeight-1][0].y)+Screen.room.blockSize+awayFromRoom, buttonSize, buttonSize);
        }

        buttonHealth = new Rectangle(Screen.room.blocks[0][0].x - 1, button[0].y, iconSize, iconSize );
        buttonCoins = new Rectangle(Screen.room.blocks[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize );

    }
    public void draw (Graphics g) {
        for (int i = 0; i < button.length; i++) {
            if (button[i].contains(Screen.mse)){ //if the mouse hovers over the button within the store
                g.setColor(new Color(255,255,255,150)); //new transparent white color
                g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
            }
            g.drawImage(Screen.tileset_res[0],button[i].x, button[i].y, button[i].width, button[i].height, null);
            if(buttonID[i] != Value.airAir) {
                g.drawImage(Screen.tileset_air[buttonID[i]],button[i].x +itemIn, button[i].y + itemIn, button[i].width - (itemIn*2), button[i].height - itemIn*2, null);
            }
            if(buttonPrice[i] > 0) {
                g.setColor(new Color(255,255,255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                g.drawString("$" + buttonPrice[i], button[i].x +itemIn, button[i].y + itemIn +10);
            }

        }
        g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
        g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);
        g.setFont(new Font("Courier New", Font.BOLD, 14));
        g.setColor(new Color(255,255,255));
        g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width+ iconSpace , buttonHealth.y + iconTextY );
        g.drawString("" + Screen.money , buttonCoins.x + buttonCoins.width+ iconSpace , buttonCoins.y + iconTextY );

        if(holdsItem){
            g.drawImage(Screen.tileset_air[heldID], Screen.mse.x + itemIn -(button[0].width-(itemIn*2))/2, Screen.mse.y + itemIn, button[0].width-(itemIn*2), button[0].height-(itemIn*2), null );
        }
    }
}
