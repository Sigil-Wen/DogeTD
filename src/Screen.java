/**[Screen.java]
 * Screen Class extends JPanel and implements Runnable, allowing the creation of the game's GUI and a threaded Game Loop.
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */
import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*; //file management

public class Screen extends JPanel implements Runnable{ //runnable creates a thread

    public Thread thread = new Thread(this); //creates a thread and runs run()

    //creating tile sets of sprites
    public static Image[] tileset_ground = new Image[9]; //ground sprites
    public static Image[] tileset_air = new Image[9]; //air sprites
    public static Image[] tileset_res = new Image[3]; //resources sprites
    public static Image[] tileset_enemy = new Image[8]; //enemy sprites

    public static int myWidth, myHeight; //width and height of screen
    public static int money = 300; //money
    public static int health = 100; //health
    public static int killed = 0, level = 7; //kills to win, killed, and level
    public static int winTime = 2000, winFrame = 0; // delay until next level starts
    public static int spawnTime = 500, spawnFrame = 0; //spawn times
    public static double speedDivisor = 1; //speed of each level
    public static int spawnCount = 0; //number of enemies spawned
    public static int[] wave; //placeholder wave of enemies
    public static String[] levelMessages = {"Well done! Level 2 introduces Speedy, quick and feisty!"};

    public static boolean isFirst = true;//isFirst frame
    public static boolean isDebug = true;//toggles debug mode, allows you to visualize tower ranges
    public static boolean isWin = false;//if the level has been won

    public static Point mse = new Point(0,0); //mouse location

    public static Room room; //room
    public static Save save; //save
    public static Store store; //store

    public static MyLinkedList<Enemy> enemyList = new MyLinkedList<Enemy>(); //linked list story all the enemies

    /**[Screen]
     * constructor for Screen, adds KeyHandler's
     * @param frame Frame class, used to add mouse listener and mouse motion listener
     */
    public Screen(Frame frame){
        frame.addMouseListener(new KeyHandler());
        frame.addMouseMotionListener(new KeyHandler());
        thread.start(); //threading implemented to run game loop with MouseListener and MouseMotionListener
    }

    /**[hasWon]
     * checks to see if room is beaten
     */
    public static void hasWon(){
        if(level != 9){ //if level isn't level 9 (endless mode),
            boolean flag = true; //flag used to determine is all enemies are dead
            for (int i = 0; i < enemyList.size(); i++) {
                if (!enemyList.get(i).isDead()) {
                    flag = false;
                }
            }
            if (flag && spawnCount == wave.length) { //if all enemies are dead AND the spawn count is the same as the wave.length, the game is won
                isWin = true; //isWin is true
                killed = 0; //resets kill count
                money = 0 + level * 25; //sets money of next round
            }
        }
    }

    /**[define]
     * Loads in and initializes the level
     */
    private void define(){
        room = new Room(); //creates a new room
        save = new Save(); //creates a new Save object
        store = new Store(); //creates a new store

        //Crops squares in the tileset_ground.png sprite sheet and adds them to tileset_ground[]
        for (int i=0; i < tileset_ground.length;i++){
            tileset_ground[i] = new ImageIcon("resources/tileset_ground.png").getImage(); //importing image
            tileset_ground[i] = createImage( new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26))); //created new image and cropping image
        }
        //Crops squares in the tileset_air.png sprite sheet and adds them to tileset_air[]
        for (int i=0; i < tileset_air.length;i++){
            tileset_air[i] = new ImageIcon("resources/tileset_air.png").getImage(); //importing image
            tileset_air[i] = createImage( new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26))); //created new image and cropping image
        }
        //loads up tileset_resources
        tileset_res[0] = new ImageIcon("resources/cell.png").getImage();
        tileset_res[1] = new ImageIcon("resources/heart.png").getImage();
        tileset_res[2] = new ImageIcon("resources/coin.png").getImage();

        //loads up enemy sprites into tileset_enemy
        tileset_enemy[1] = new ImageIcon("resources/doge1.png").getImage();
        tileset_enemy[2] = new ImageIcon("resources/speedy.png").getImage();
        tileset_enemy[3] = new ImageIcon("resources/doge2.png").getImage();
        tileset_enemy[4] = new ImageIcon("resources/zapdog.png").getImage();
        tileset_enemy[5] = new ImageIcon("resources/firedoge.png").getImage();
        tileset_enemy[6] = new ImageIcon("resources/icedog.png").getImage();
        tileset_enemy[7] = new ImageIcon("resources/angeldoge.png").getImage();

        //loads in level from /save directory (wave, map, speed, spawn rate)
        save.loadSave(new File("save/mission"+level+".save")); // loads in saved file as the room map, enemy wave, spawn frequency
    }

    /**[paintComponent]
     * paints the GUI of Screen
     * @param g Graphics
     */
    public void paintComponent(Graphics g){
        if(isFirst){ //if the frame is the first frame
            myWidth = getWidth(); //sets width
            myHeight = getHeight(); //sets height
            define(); //initializes the Screen
            isFirst = false; //sets isFirst back
        }
        g.setColor(new Color(100,100,100)); //sets background color to gray
        g.fillRect(0,0,getWidth(),getHeight()); //draws background on screen
        g.setColor(new Color(0,0,0));//sets color to white

        //draws boarder around Room (map of the game)
        g.drawLine(room.blocks[0][0].x-1,0,room.blocks[0][0].x-1, room.blocks[room.worldHeight-1][0].y+room.blockSize-1);//drawing the left line
        g.drawLine(room.blocks[0][room.worldWidth-1].x + room.blockSize,0,room.blocks[0][room.worldWidth-1].x + room.blockSize, room.blocks[room.worldHeight-1][0].y+room.blockSize-1); //drawing the right line
        g.drawLine(room.blocks[0][0].x, room.blocks[room.worldHeight-1][0].y + room.blockSize, room.blocks[0][room.worldWidth-1].x + room.blockSize, room.blocks[room.worldHeight-1][0].y + room.blockSize); // drawing the bottom line
        room.draw(g); //Drawing the room

        //loops through each of the enemies and draws them if they are alive
        for(int i = 0; i < enemyList.size(); i++){
            if (enemyList.get(i).inGame){
                enemyList.get(i).draw(g);
            }
        }
        store.draw(g); //Drawing the store

        //if player died, displays game over
        if(health < 1 ){
            g.setColor(new Color(240,20,20)); //sets color red
            g.fillRect(0,0, myWidth, myHeight); //fills screen with red
            g.setColor(new Color(255,255,255)); //writes game over
            g.setFont(new Font("Courier New",Font.BOLD, 14));
            g.drawString("GAME OVER", myWidth/2-30,myHeight/2);
        }

        //if player beat the room
        if(isWin){
                g.setColor(new Color(0, 0, 0)); //sets background color to black
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                //if level 8 is beaten, begin ENDLESSE MODE
                if(level == 8){
                    g.drawString("YOU BEAT DOGE TOWER DEFENCE! CONGRATULATIONS. Prepare for endless mode >:)", 70, myHeight / 2);
                } else { //displays next level WIN message
                    g.drawString("YOU WON! PREPARE FOR LEVEL " + (level+1), myWidth / 2 - 70, myHeight / 2);
                }
        }
    }

    /**[setSpawnTime]
     * updates the spawn frequency of enemies
     * @param spawnTime
     */
    public void setSpawnTime(int spawnTime){
        this.spawnTime = spawnTime;
    }

    /**[spawn]
     * spawns in enemy of Value ID and increases spawn count
     * @param ID value of enemy spawned
     */
    private void spawn(int ID){
        switch(ID){
            case 1://adds Doge to enemyList
                enemyList.add(new EnemyDoge());
                break;
            case 2://adds Speedy to enemyList
                enemyList.add(new EnemySpeedy());
                break;
            case 3: //adds Chad to enemyList
                enemyList.add(new EnemyChad());
                break;
            case 4: //adds Zap to enemyList
                enemyList.add(new EnemyZap());
                break;
            case 5://adds Fire to enemyList
                enemyList.add(new EnemyFireDoge());
                break;
            case 6: //adds Ice to enemyList
                enemyList.add(new EnemyIceDoge());
                break;
            case 7: //adds Angel to enemyList
                enemyList.add(new EnemyAngelDoge());
                break;
        }
        spawnCount++;
    }

    /**[enemySpawner]
     * continually spawns all of the enemies of the Room. Infinitely spawns if player reaches endless mode
     */
    public void enemySpawner() {
        if(level == 9){ //endless mode begins
            if (spawnFrame > spawnTime) { //spawnTime dictates spawn frequency. Spawns every spawnTime frames passes
                //spawns a random enemy
                int random_enemy = (int)(Math.random() * (6 + 1));
                spawn(random_enemy); //spawns random enemy
                spawnFrame = 0; //resets spawnFrame
                if(spawnTime >= 250){ //spawnTime continuously decreases every spawn, increasing spawn rate in endless mode
                    spawnTime -=1;
                }
                if (spawnCount >= 250){ //once 250 enemies had spawned certain death wave comes on >:)
                    spawn(Value.enemyAngeldoge);  //spawns Boss enemy Angel Doge
                }
            }else{
                spawnFrame += 1; //increases spawnFrame
            }
        }else {//if wave is normal
            if (spawnFrame > spawnTime) {  //spawns enemy from room wave incrementally
                if (spawnCount != wave.length) { //if wave has not been fully spawned, spawn next enemy in wave
                    spawn(wave[spawnCount]);
                }
                spawnFrame = 0;
            } else { //increases spawn frame
                spawnFrame += 1;
            }
        }
    }

    /**[run]
     * Game loop
     */
    public void run() { //game loop
        while(true){
            if(!isFirst && health>0 && !isWin){ //checks if not dead, not won, and not the first frame
                room.physic(); //runs physics for the room
                enemySpawner(); //calls enemySpawner() to spawn enemy
                for(int i = 0; i<enemyList.size(); i++){ //applies physic() to each of the enemies that are in the game
                    if(enemyList.get(i).inGame){
                        enemyList.get(i).physic();
                    }
                }
            }else{ //if the wave has been beaten
                    if (isWin) { //checks if the wave has been won
                        if (winFrame > winTime) { //has a buffer before starting next level
                                level += 1; //increases level
                                if(level < 9) { //if the level has not reached endless
                                    isFirst = true; //sets frame back to isFirst
                                    save.loadSave(new File("save/mission" + level + ".save")); // loads in saved file as the room map, respawn rate, enemy speed
                                    enemyList = new MyLinkedList<Enemy>(); //clears enemy list
                                    isWin = false; //sets isWin back to false
                                    spawnCount = 0; //resets spawn count
                                }else{//endless mode
                                    isFirst = true;
                                    spawnTime = 2500; //sets initial spawn time to 2500
                                    enemyList = new MyLinkedList<Enemy>(); //clears enemy list
                                    isWin = false;
                                    spawnCount = 0; //resets spawn count
                                }
                            winFrame = 0;
                        } else {
                            winFrame += 1; //appends winFrame
                        }
                    }
            }
            repaint(); //repaints frame
            try {
                Thread.sleep(1); //run() game loop thread sleeps for 1 millisecond
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
