/**[Room.java]
 * Room Class is used to create each Room (or Level) that the player needs to defend
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */

import java.awt.*;

public class Room {

    public int worldWidth = 12; //world width
    public int worldHeight = 8; //world height
    public int blockSize = 52; //size of each block
    public Block[][] blocks; //2D array of blocks, used to represent the map of the room

    /**
     * [Room]
     * constructor for Room that runs define()
     */
    public Room() {
        define();
    }

    /**
     * [define]
     * defines every block within the 2d array of blocks
     */
    public void define(){
        blocks = new Block[worldHeight][worldWidth]; //defining 2D array blocks

        for (int y = 0; y<blocks.length;y++){
            for(int x = 0; x<blocks[0].length;x++){
                blocks[y][x] = new Block((Screen.myWidth/2)-((worldWidth*blockSize)/2)+ (x*blockSize), y*blockSize, blockSize, blockSize, Value.groundGrass, Value.airAir); //defines all of the blocks
            }
        }
    }

    /**
     * [physic]
     * loops through each block and runs physic
     */
    public void physic() {
        for (int y =0; y<blocks.length; y++ ){
            for(int x = 0;x<blocks[0].length;x++){
                blocks[y][x].physic();
            }
        }
    }

    /** [draw]
     * loops through each block and draws each block as well as their attacks
     * @param g Graphics
     */
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
