/**[Block.java]
 * Block Class creates a single block used within the Room grid. Stores everything within a block including the ground tiles and towers itself. Manages attacks for the towers.
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */

import java.awt.*;

public class Block extends Rectangle {

    private Rectangle towerRange; //Rectangle outlining the towers Range
    private int towerRangeSize=130; //towers Range size
    private int shotEnemy = -1;//
    private int loseTime = 50, loseFrame = 0;
    private boolean attacking = false; //whether the tower is attacking

    //ID's used to store air (tower) and ground values of the block
    public int groundID; // groundID
    public int airID; //airID

    /**[Block]
     * constructor for Block
     * @param x the x coordinate of Block
     * @param y the y coordinate of Block
     * @param width width of block
     * @param height height of block
     * @param groundID groundID of block
     * @param airID airID of block
     */
    public Block(int x, int y, int width, int height, int groundID, int airID){
        setBounds(x,y,width,height); //sets the bound of the Rectangle
        towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize)); //initializes the tower range
        this.groundID = groundID; //sets groundID
        this.airID = airID; //sets airId
    }

    /**[draw]
     * draw method that draws the block
     * @param g Graphics
     */
    public void draw(Graphics g){
        g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null); //draws the ground tile

        if(this.airID == Value.airMad){ //if the airID is the same as the tower Mad, sets up range to 20
            towerRangeSize = 20;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }
        else if(this.airID == Value.airCreeper){ //if the airID is the same as the tower Creeper, sets up range to 250
            towerRangeSize = 250;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airPepe){//if the airID is the same as the tower Pepe, sets up range to 250
            towerRangeSize = 250;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airStar){ //if the airID is the same as the tower Star, sets up range to 50
            towerRangeSize = 50;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airGD){//if the airID is the same as the tower GD, sets up range to 250
            towerRangeSize = 250;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airBruh){//if the airID is the same as the tower BRUH, sets up range to 350
            towerRangeSize = 350;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }
        if(airID != Value.airAir){ //draws tower if not air
            g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
        }

    }

    /**[physic]
     * runs attacking and targeting for the tower
     */
    public void physic() {
        if (shotEnemy != -1 && towerRange.intersects(Screen.enemyList.get(shotEnemy))) { //if the shot enemy is within range, tower is attacking
            attacking = true;
        }else{ //if the shot enemy is dead, tower stops attacking
            attacking = false;
        }
        if(!attacking) { //if the tower isn't attacking
            if (airID != Value.airAir && airID != Value.airHome) { //if the tower exists within a block
                for (int i = 0; i < Screen.enemyList.size(); i++) { //tower loops through the enemies
                    if (Screen.enemyList.get(i).inGame) { //if the enemy is in game AND intersects the range of the tower
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true; //sets attacking to true
                            shotEnemy = i; //sets shotEnemy to enemy to be attacked
                        }
                    }
                }
            }
        } else{//if the tower is attacking
            if(loseFrame >= loseTime){ //attack runs every 50 frames
                if(this.airID == Value.airMario) { //if tower is Mario, damage is 1
                    Screen.enemyList.get(shotEnemy).loseHealth(1);
                }else if(this.airID == Value.airMad){ //if tower is airMad, damage is 4
                    Screen.enemyList.get(shotEnemy).loseHealth(4);
                } else if(this.airID == Value.airCreeper){//if tower is creeper, damage is 2
                    Screen.enemyList.get(shotEnemy).loseHealth(2);
                }else if(this.airID == Value.airPepe){//if tower is pepe, damage is 3
                    Screen.enemyList.get(shotEnemy).loseHealth(3);
                }else if(this.airID == Value.airStar){//if tower is star, damage is 10
                    Screen.enemyList.get(shotEnemy).loseHealth(10);
                }else if(this.airID == Value.airGD){//if tower is GD, damage is 8
                    Screen.enemyList.get(shotEnemy).loseHealth(8);
                }else if(this.airID == Value.airBruh){//if tower is BRUH, damage is 15
                    Screen.enemyList.get(shotEnemy).loseHealth(15);
                }
                loseFrame = 0; //resets lose frame
            }else{
                loseFrame+=1; //increments lose frame
            }
            if(Screen.enemyList.get(shotEnemy).isDead()){ //if shot enemy isdead
                attacking=false; //sets attacking to false
                shotEnemy = -1; //resets shot enemy
                Screen.hasWon(); //checks to see if the room has been beaten
            }
        }
    }

    /**
     *[getMoney]
     * rewards the player with the death reward of the enemy
     * @param enemyID integer containing the enemies ID
     */
    public void getMoney(int enemyID){
        Screen.money += Value.deathReward[enemyID]; //adds death reward of enemy to money
    }

    /**[attack]
     * draws the towers attack
     * @param g Graphics
     */
    public void attack(Graphics g){
        if(Screen.isDebug) { //if the game is set in debug mode, display tower ranges
            if (airID >= Value.airMario) { //if the block has a tower
                g.setColor(new Color(0,0,0, 35));
                g.drawRect(towerRange.x, towerRange.y, towerRange.width, towerRange.height); //draws range
            }
        }
            if(attacking){ //if the tower is attacking, draw attack
                g.setColor(new Color(255,200- 25*airID,0)); //changes attack color to red the more powerful the tower
                //draws attack laser towards enemy
                g.drawLine(x+(width/2),y+(height/2), Screen.enemyList.get(shotEnemy).x+(Screen.enemyList.get(shotEnemy).width/2), Screen.enemyList.get(shotEnemy).y +(Screen.enemyList.get(shotEnemy).height/2));
            }
    }

}
