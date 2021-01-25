import javax.swing.*;
import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*; //file management

public class Screen extends JPanel implements Runnable{ //runnable creates a thread
    public Thread thread = new Thread(this); //creates a thread and runs run()

    //importing sprites
    public static Image[] tileset_ground = new Image[100]; //ground sprites
    public static Image[] tileset_air = new Image[100]; //air sprites
    public static Image[] tileset_res = new Image[100]; //resources sprites
    public static Image[] tileset_enemy = new Image[100]; //enemy sprites

    public static int myWidth, myHeight;
    public static int money = 10; //money
    public static int health = 100; //health
    public static int killed = 0, level = 1; //kills to win, killed, and level
    public static int winTime = 2000, winFrame = 0; // delay until next level starts
    public static int maxLevel =9;
    public static int spawnTime = 500, spawnFrame = 0; //spawn times
    public static double speedDivisor = 1; //speed of each level
    public static int spawnCount = 0;
    public static int[] wave; //placeholder wave of enemies
    public static String[] levelMessages = {"Well done! Level 2 introduces Speedy, quick and feisty!"};

    public static boolean isFirst = true;//isFirst frame
    public static boolean isDebug = true;//toggles debug mode, allows you to visualize tower ranges
    public static boolean isWin = false;//if the level has been won

    public static Point mse = new Point(0,0); //mouse location

    public static Room room;
    public static Save save;
    public static Store store;

    public static SimpleLinkedList<Enemy> enemyList = new SimpleLinkedList<Enemy>();

    public Screen(Frame frame){
        frame.addMouseListener(new KeyHandler());
        frame.addMouseMotionListener(new KeyHandler());
        thread.start(); //Multithreading implemented to simultaneously run a MouseListener and MouseMotionListener
    }

    public static void hasWon(){
        if(level == 9){
            System.out.println(spawnTime);
        }else {
            System.out.println(wave.length + " : " + (killed) + " : " + enemyList.size());
            boolean flag = true;
            for (int i = 0; i < enemyList.size(); i++) {
                if (!enemyList.get(i).isDead()) {
                    flag = false;
                }
            }
            if (flag && spawnCount == wave.length) {
                isWin = true;
                killed = 0;
                money = 0 + level * 25;
            }
        }
    }

    public void define(){ //defines methods
        room = new Room(); //defines room
        save = new Save(); //defines Save
        store = new Store(); //defines store

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
        tileset_enemy[2] = new ImageIcon("resources/speedy.png").getImage();
        tileset_enemy[3] = new ImageIcon("resources/doge2.png").getImage();
        tileset_enemy[4] = new ImageIcon("resources/zapdog.png").getImage();
        tileset_enemy[5] = new ImageIcon("resources/firedoge.png").getImage();
        tileset_enemy[6] = new ImageIcon("resources/icedog.png").getImage();
        tileset_enemy[7] = new ImageIcon("resources/angeldoge.png").getImage();


        save.loadSave(new File("save/mission"+level+".save")); // loads in saved file as the map
        System.out.println("Level :" + level);
        System.out.println("Spawntime :" + spawnTime);
        System.out.println("SpeedDivisor :" + speedDivisor);
        System.out.print("Wave : ");
        for(int i = 0; i< wave.length; i++){
            System.out.print(wave[i] + " ");
        }
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

        for(int i = 0; i < enemyList.size(); i++){
            if (enemyList.get(i).inGame){
                enemyList.get(i).draw(g);
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
                g.setColor(new Color(0, 0, 0));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.setColor(new Color(255, 255, 255));
                g.setFont(new Font("Courier New", Font.BOLD, 14));
                if(level == 8){
                    g.drawString("YOU BEAT DOGE TOWER DEFENCE! CONGRATULATIONS. Prepare for endless mode >:)", 70, myHeight / 2);
                } else {
                    g.drawString("YOU WON! PREPARE FOR  LEVEL " + (level+1), myWidth / 2 - 70, myHeight / 2);
                }
        }
    }

    public void setSpawnTime(int spawnTime){
        this.spawnTime = spawnTime;
    }

    //spawner
    public void enemySpawner() {
        if(level == 9){ //endless mode
            if (spawnFrame > spawnTime) {
                int random_enemy = (int)(Math.random() * (6 + 1));
                enemyList.add(new Enemy(random_enemy));
                spawnCount++;
                spawnFrame = 0;
                if(spawnTime >= 50){
                    spawnTime -=4;
                }
                if (spawnCount >= 200){ //certain death >:)
                    enemyList.add(new Enemy(7));
                }
            }else{
                spawnFrame += 1;
            }
        }else {
            if (spawnFrame > spawnTime) {
                if (spawnCount != wave.length) {
                    enemyList.add(new Enemy(wave[spawnCount]));
                    spawnCount++;
                }
                spawnFrame = 0;
            } else {
                spawnFrame += 1;
            }
        }
    }

    public void run() { //game loop
        while(true){
            if(!isFirst && health>0 && !isWin){ //checks if not dead, not won, and not the first frame
                room.physic();
                enemySpawner();
                for(int i = 0; i<enemyList.size(); i++){
                    if(enemyList.get(i).inGame){
                        enemyList.get(i).physic();
                    }
                }
            }else{
                    if (isWin) {
                        if (winFrame > winTime) {
                            if (level >= maxLevel) {
                                System.out.println("Exiting: " + level);
                                System.exit(0);
                            } else {
                                level += 1;
                                if(level < 9) {
                                    System.out.println("Starting level: " + level);
                                    isFirst = true;
                                    save.loadSave(new File("save/mission" + level + ".save")); // loads in saved file as the map
                                    enemyList = new SimpleLinkedList<Enemy>();
                                    isWin = false;
                                    spawnCount = 0;
                                }else{//endless mode
                                    System.out.println("Starting level: " + level);
                                    isFirst = true;
                                    spawnTime = 2500;
                                    enemyList = new SimpleLinkedList<Enemy>();
                                    isWin = false;
                                    spawnCount = 0;
                                }
                            }
                            winFrame = 0;
                        } else {
                            winFrame += 1;
                        }
                    }
            }
            repaint();
            try {
                Thread.sleep(1);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

}
