import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*; //file management

public class Screen extends JPanel implements Runnable{ //runnable creates a thread
    public Thread thread = new Thread(this); //creates a thread and runs run()

    public static Image[] tileset_ground = new Image[100];
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_res = new Image[100];
    public static Image[] tileset_enemy = new Image[100];

    public static int myWidth, myHeight;
    public static int money = 10;
    public static int health = 100;
    public static int killed = 0, killsToWin = 100, level = 1;
    public static int winTime = 4000, winFrame = 0;
    public static int maxLevel =3;


    public static boolean isFirst = true;
    public static boolean isDebug = false;//toggles debug mode
    public static boolean isWin = false;

    public static Point mse = new Point(0,0); //mouse location

    public static Room room;
    public static Save save;
    public static Store store;

    public static Enemy[] enemies = new Enemy[100];

    public Screen(Frame frame){
        frame.addMouseListener(new KeyHandler());
        frame.addMouseMotionListener(new KeyHandler());
        thread.start();
    }

    public static void hasWon(){
        if(killed  >= killsToWin){
            isWin = true;
            killed=0;
            money = 0;

        }
    }

    public void define(){ //defines methods
        room = new Room(); //defines room
        save = new Save(); //defines Save
        store = new Store(); //defines store
        money = 10;
        health = 100;

        for (int i=0; i < tileset_ground.length;i++){
            tileset_ground[i] = new ImageIcon("resources/tileset_ground.png").getImage(); //importing image
            tileset_ground[i] = createImage( new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26*i, 26, 26))); //created new image and cropping image
        }
        for (int i=0; i < tileset_air.length;i++){
            tileset_air[i] = new ImageIcon("resources/tileset_air.png").getImage(); //importing image
            tileset_air[i] = createImage( new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26*i, 26, 26))); //created new image and cropping image
        }
        tileset_res[0] = new ImageIcon("resources/cell.png").getImage();
        tileset_res[1] = new ImageIcon("resources/heart.png").getImage();
        tileset_res[2] = new ImageIcon("resources/coin.png").getImage();

        tileset_enemy[1] = new ImageIcon("resources/doge1.png").getImage();


        save.loadSave(new File("save/mission"+level+".save")); // loads in saved file as the map

        for(int i = 0; i< enemies.length; i++){ //defines all of the mob
            enemies[i] = new Enemy();
        }
        enemies[0].spawnEnemy(1);

    }

    public void paintComponent(Graphics g){
        if(isFirst){
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        g.setColor(new Color(100,100,100));
        g.fillRect(0,0,getWidth(),getHeight()); //draws blank space on screen
        g.setColor(new Color(0,0,0));
        g.drawLine(room.blocks[0][0].x-1,0,room.blocks[0][0].x-1, room.blocks[room.worldHeight-1][0].y+room.blockSize-1);//drawing the left line
        g.drawLine(room.blocks[0][room.worldWidth-1].x + room.blockSize,0,room.blocks[0][room.worldWidth-1].x + room.blockSize, room.blocks[room.worldHeight-1][0].y+room.blockSize-1); //drawing the right line
        g.drawLine(room.blocks[0][0].x, room.blocks[room.worldHeight-1][0].y + room.blockSize, room.blocks[0][room.worldWidth-1].x + room.blockSize, room.blocks[room.worldHeight-1][0].y + room.blockSize); // drawing the bottom line
        room.draw(g); //Drawing the room
        for(int i = 0; i < enemies.length; i++){
            if (enemies[i].inGame){
                enemies[i].draw(g);
            }
        }

        store.draw(g); //Drawing the store

        if(health < 1 ){
            g.setColor(new Color(240,20,20));
            g.fillRect(0,0, myWidth, myHeight);
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Courier New",Font.BOLD, 14));
            g.drawString("GAME OVER", myWidth/2-30,myHeight/2);
        }
        if(isWin){
            g.setColor(new Color(0,0,0));
            g.fillRect(0,0, getWidth(), getHeight());
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Courier New",Font.BOLD, 14));
            if(level>=maxLevel){
                g.drawString("YOU BEAT DOGE TOWER DEFENCE! CONGRATULATIONS", myWidth / 2 - 70, myHeight / 2);

            }else {
                g.drawString("YOU WON! PREPARE FOR THE NEXT LEVEL", myWidth / 2 - 70, myHeight / 2);
            }
        }
    }

    public int spawnTime = 500, spawnFrame = 0;
    public void enemySpawner() {
        if(spawnFrame>spawnTime){
            for(int i = 0; i<enemies.length;i++ ){
                if(!enemies[i].inGame){//!
                    enemies[i].spawnEnemy(Value.enemyDoge1);
                    break;
                }
            }
            spawnFrame = 0;
        }else{
            spawnFrame+=1;
        }
    }

    public void run() { //game loop
        while(true){
            if(!isFirst && health>0 && !isWin){
                room.physic();
                enemySpawner();
                for(int i = 0; i<enemies.length; i++){
                    if(enemies[i].inGame){
                        enemies[i].physic();
                    }
                }
            }else{
                if(isWin){
                    if(winFrame > winTime){
                        if(level>=maxLevel){
                            System.out.println("Exiting: "+level);
                            System.exit(0);

                        }else{
                            level +=1;
                            System.out.println("Starting level: "+level);
                            isFirst = true;
                            save.loadSave(new File("save/mission"+level+".save")); // loads in saved file as the map

                            isWin = false;
                        }
                        winFrame = 0;
                    }else{
                        winFrame +=1;
                    }
                }
            }
            repaint();
            try {
                Thread.sleep(1);
            }catch(Exception e){

            }
        }
    }

}
