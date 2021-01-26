/**[Enemy.java]
 * Enemy Class creates enemy Doge's to attack your base
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */

import java.awt.*;
public class Enemy extends Rectangle{

    private int xCord, yCord; //coordinates of the enemy
    public int health; //health of the enemy
    public int damage = 1; //damage enemy does

    //UI Elements
    private int healthSpace = 3, healthHeight = 6;
    private int enemySize = 52;

    //enemy attributes
    public int enemyID = Value.enemyAir; //the type of enemy
    public boolean inGame = false; //if the enemy is alive

    //walking physics
    private int enemyWalk = 0; //number of times the enemy walks
    private int upward = 0, downward = 1, right = 2, left =3; //value assigned to each direction
    private int direction = right; //initial direction
    public int walkFrame = 0, walkSpeed = 10; //walkSpeed determines the frame that the enemy moves on
    private boolean hasUpward = false, hasDownward = false, hasRight = false, hasLeft = false; //booleans used for path finding- making sure enemy doesnt backtrack
    /**[Enemy]
     *default constructor for enemy
     */
    public Enemy(){ }
    /**[Enemy]
     *constructor for enemy, and spawns an enemy
     * @param enemyID takes in enemy ID
     */
    public Enemy(int enemyID){
        spawnEnemy(enemyID); //spawns in enemy
    }

    /**[spawnEnemy]
     * Spawns in a new enemy
     * @param enemyID enemy ID to be spawned
     */
    public void spawnEnemy(int enemyID){
        for(int y=0; y < Screen.room.blocks.length; y++){ //searches left side of the room for a road block to spawn in
            if(Screen.room.blocks[y][0].groundID == Value.groundRoad){
                setBounds(Screen.room.blocks[y][0].x,Screen.room.blocks[y][0].y, enemySize, enemySize); //setBounds for enemy and spawns it in
                xCord = 0;
                yCord = y;
            }
        }
        this.enemyID = enemyID; //initiates enemy ID
        inGame = true; //sets enemy to be in game
    }

    /**[physic]
     * path finding algorithm that allows the enemy to move throughout the room towards your base
     */
    public void physic(){
        if(walkFrame >= ((int) walkSpeed/Screen.speedDivisor)) { //determines frame frequency of walking, also impacted by Screen.speedDivisor which increases with each level
            if(direction == right) { //moves right
                x += 1;
            }else if (direction ==upward) {//moves up
                y-=1;
            }else if (direction == downward){//moves down
                y+=1;
            }else if(direction == left){//moves left
                x-=1;
            }
            enemyWalk +=1; //appends enemyWalk --> used to ensure that the enemy walks exactly the length of 1 block

            if(enemyWalk == Screen.room.blockSize){ //once enemyWalk is the same as the length of 1 block, updates enemy cord
                if(direction == right) { //if right
                    xCord += 1;
                    hasRight=true;
                }else if (direction ==upward) { //if up
                    yCord-=1;
                    hasUpward = true;
                }else if (direction == downward){//if down
                    yCord+=1;
                    hasDownward = true;
                }else if (direction == left){//if left
                    xCord-=1;
                    hasLeft = true;
                }
                //path finding algorithm
                if(!hasUpward) { //if it hasn't gone upward, check to see if lower block is road, if true set direction to be downward
                    try {
                        if (Screen.room.blocks[yCord + 1][xCord].groundID == Value.groundRoad) {
                            direction = downward;
                        }
                    } catch (Exception e) {
                    }
                }
                if(!hasDownward){ //if it hasn't gone downward, check to see if upper block is road, if true set direction to be upward
                    try {
                        if (Screen.room.blocks[yCord - 1][xCord].groundID == Value.groundRoad) {
                        direction = upward;
                     }
                 }catch (Exception e){}
                }
                if(!hasLeft) { //if it hasn't gone left, check to see if right block is road, if true set direction to be right
                    try {
                        if (Screen.room.blocks[yCord][xCord + 1].groundID == Value.groundRoad) {
                            direction = right;
                        }
                    } catch (Exception e) {
                    }
                }
                if(!hasRight) { //if it hasn't gone right, check to see if left block is road, if true set direction to be left
                    try {
                        if (Screen.room.blocks[yCord][xCord - 1].groundID == Value.groundRoad) {
                            direction = left;
                        }
                    } catch (Exception e) {
                    }
                }

                if(Screen.room.blocks[yCord][xCord].airID == Value.airHome){ //if the enemy has reached your base
                    deleteEnemy(); //enemy is deleted
                    Screen.health-=1*damage; //health is damaged by the enemy's damage
                    Screen.hasWon();//checks to see if they beat the level
                }

                //resets walked direction
                hasUpward = false;
                hasDownward = false;
                hasLeft=false;
                hasRight=false;
                enemyWalk = 0; //sets enemyWalk back to zero
            }
            walkFrame=0; //resets walk frame
        }else{
            walkFrame+=1; //increases walk frame by one
        }
    }

    /**[deleteEnemy]
     * kills the enemy and rewards the player
     */
    public void deleteEnemy(){
        inGame = false; //set inGame to false
        direction=right; //resets direction
        enemyWalk = 0; //resets enemyWalk
        Screen.room.blocks[0][0].getMoney(enemyID); //rewards the player with money
        Screen.killed+=1;//increases kill count
    }

    /**[loseHealth]
     * decreases damage from health
     * @param damage int damage dealt
     */
    public void loseHealth(int damage){
        health -=damage; //decreases
        checkDeath(); //checks if player has died
    }

    /**[checkDeath]
     * checks if the enemy is dead
     */
    public void checkDeath(){
        if(health <= 0){
            deleteEnemy();//deletes the enemy once health is below zero
        }
    }

    /**[isDead]
     * returns boolean if enemy is dead
     * @return boolean returns is dead
     */
    public boolean isDead(){
        if(inGame){
            return false;
        }else{
            return true;
        }
    }

    /**[draw]
     * draw method draws enemy and their health bar. Health bar color adjusts with enemy health
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(Screen.tileset_enemy[enemyID], x, y, width,height,null); //draws enemy
        //health bar
        if(this.enemyID <=2) { //if enemy is doge or speedy, draws a green health bar
            g.setColor(new Color(50, 180, 50));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, health - 2, healthHeight - 2);
        }else if(this.health <= 250){// if enemies health is less than or equal to 250, draw a blue health bar
            g.setColor(new Color(50, 50, 180));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, (health - 2)/5, healthHeight - 2);
        }else if(this.health <= 1000){// if enemies health is less than or equal to 1000, draw a red health bar
            g.setColor(new Color(180, 50, 50));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, (health - 2)/20, healthHeight - 2);
        }else if(this.health <= 10000){// if enemies health is less than or equal to 10000, draw a black health bar
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, (health - 2)/200, healthHeight - 2);
        }
    }
}
