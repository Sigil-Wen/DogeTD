import java.awt.*;
public class Enemy extends Rectangle{
    public int xCord, yCord;
    public int health;
    public int healthSpace = 3, healthHeight = 6;
    public int enemySize = 52;
    public int enemyID = Value.enemyAir;
    public int enemyWalk = 0;
    public int upward = 0, downward = 1, right = 2, left =3;
    public int direction = right;
    public boolean inGame = false;
    public boolean hasUpward = false, hasDownward = false, hasRight = false, hasLeft=false;
    public Enemy(){

    }
    public void spawnEnemy(int enemyID){
        for(int y=0; y < Screen.room.blocks.length; y++){
            if(Screen.room.blocks[y][0].groundID == Value.groundRoad){
                setBounds(Screen.room.blocks[y][0].x,Screen.room.blocks[y][0].y, enemySize, enemySize);
                xCord = 0;
                yCord = y;
            }
        }

        this.enemyID = enemyID;
        this.health = enemySize;

        inGame = true;

    }

    public int walkFrame = 0, walkSpeed = 10;
    public void physic(){
        if(walkFrame >= walkSpeed) {
            if(direction == right) {
                x += 1;
            }else if (direction ==upward) {
                y-=1;
            }else if (direction == downward){
                y+=1;
            }else if(direction == left){
                x-=1;
            }
            enemyWalk +=1;

            if(enemyWalk == Screen.room.blockSize){
                if(direction == right) {
                    xCord += 1;
                    hasRight=true;
                }else if (direction ==upward) {
                    yCord-=1;
                    hasUpward = true;
                }else if (direction == downward){
                    yCord+=1;
                    hasDownward = true;
                }else if (direction == left){
                    xCord-=1;
                    hasLeft = true;
                }
                if(!hasUpward) {
                    try {
                        if (Screen.room.blocks[yCord + 1][xCord].groundID == Value.groundRoad) {
                            direction = downward;
                        }
                    } catch (Exception e) {
                    }
                }
                if(!hasDownward){
                try {
                    if (Screen.room.blocks[yCord - 1][xCord].groundID == Value.groundRoad) {
                        direction = upward;
                    }
                }catch (Exception e){}
                }
                if(!hasLeft) {
                    try {
                        if (Screen.room.blocks[yCord][xCord + 1].groundID == Value.groundRoad) {
                            direction = right;
                        }
                    } catch (Exception e) {
                    }
                }
                if(!hasRight) {
                    try {
                        if (Screen.room.blocks[yCord][xCord - 1].groundID == Value.groundRoad) {
                            direction = left;
                        }
                    } catch (Exception e) {
                    }
                }

                if(Screen.room.blocks[yCord][xCord].airID == Value.airHome){
                    deleteEnemy();
                    Screen.health-=1;
                    //
                }

                hasUpward = false;
                hasDownward = false;
                hasLeft=false;
                hasRight=false;
                enemyWalk = 0;
            }
            walkFrame=0;

        }else{
            walkFrame+=1;
        }
    }


    public void deleteEnemy(){
        inGame = false;
        //ensures enemy respawns correctly
        direction=right;
        enemyWalk = 0;
        Screen.room.blocks[0][0].getMoney(enemyID);

    }
    public void loseHealth(int damage){
        health -=damage;
        checkDeath();
    }
    public void checkDeath(){
        if(health == 0){
            deleteEnemy();
        }
}
    public boolean isDead(){
        if(inGame){
            return false;
        }else{
            return true;
        }
}


    public void draw(Graphics g){
            g.drawImage(Screen.tileset_enemy[enemyID], x, y, width,height,null);
//health
             g.setColor(new Color(180,50,50));
             g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

            g.setColor(new Color(0,0,0));
            g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);

            g.setColor(new Color(50,180,50));
            g.fillRect(x+1, y - (healthSpace + healthHeight)+1, health-2, healthHeight-2);


    }
}
