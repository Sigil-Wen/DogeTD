import javax.swing.*;
import java.awt.*;


public class Room {

    public int worldWidth = 12;
    public int worldHeight = 8;
    public int blockSize = 52;
    public Block[][] blocks;

    public Room() {
        define();

    }
    public void define(){
        blocks = new Block[worldHeight][worldWidth];

        for (int y = 0; y<blocks.length;y++){
            for(int x = 0; x<blocks[0].length;x++){
                blocks[y][x] = new Block((Screen.myWidth/2)-((worldWidth*blockSize)/2)+ (x*blockSize), y*blockSize, blockSize, blockSize, Value.groundGrass, Value.airAir); //defines all of the blocks
            }
        }
    }

    public void physic() {
        for (int y =0; y<blocks.length; y++ ){
            for(int x = 0;x<blocks[0].length;x++){
                blocks[y][x].physic();
            }
        }
    }

    public void draw(Graphics g){
        for (int y = 0; y<blocks.length;y++){
            for(int x = 0; x<blocks[0].length;x++){
                blocks[y][x].draw(g); //draws each of the blocks
            }
        }
        for (int y = 0; y<blocks.length;y++){
            for(int x = 0; x<blocks[0].length;x++){
                blocks[y][x].attack(g); //draws each attacking tower
            }
        }
    }
}
