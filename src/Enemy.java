import java.awt.*;
public class Enemy extends Rectangle{
    public int xCord, yCord;
    public int health;

    //UI Elements
    public int healthSpace = 3, healthHeight = 6;
    public int enemySize = 52;
    public int enemyID = Value.enemyAir; //the type of enemy
    public int enemyWalk = 0; //number of times the enemy walks
    public int upward = 0, downward = 1, right = 2, left =3;
    public int direction = right;
    public int damage = 1;
    //physics engine
    public int walkFrame = 0, walkSpeed = 10;
    public boolean inGame = false;
    public boolean hasUpward = false, hasDownward = false, hasRight = false, hasLeft = false;

    public Enemy(int enemyID){
        spawnEnemy(enemyID);
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
        if(this.enemyID == Value.enemyDoge1){ //if the enemy is Doge1
            this.health = 50;
            this.walkSpeed = 10;
            this.damage = 3;
        }else if(this.enemyID == Value.enemySpeedy){
            this.health = 20;
            this.walkSpeed = 5;
            this.damage = 5;
        }else if(this.enemyID == Value.enemyDoge2){
            this.health = 100;
            this.walkSpeed = 4;
            this.damage = 10;
        }else if(this.enemyID == Value.enemyZapdog){
            this.health = 250;
            this.walkSpeed = 12;
            this.damage = 15;
        }else if(this.enemyID == Value.enemyFiredoge){
            this.health = 1000;
            this.walkSpeed = 30;
            this.damage = 20;
        }else if(this.enemyID == Value.enemyIcedog){
            this.health = 200;
            this.walkSpeed = 5;
            this.damage = 25;
        }else if(this.enemyID == Value.enemyAngeldoge){
            this.health = 10000;
            this.walkSpeed = 30;
            this.damage = 100;
        }

        inGame = true;
    }


    public void physic(){
        if(walkFrame >= ((int) walkSpeed/Screen.speedDivisor)) {
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
                    Screen.health-=1*damage;
                    Screen.hasWon();//checks to see if they beat the level
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
        Screen.killed+=1;
    }
    public void loseHealth(int damage){
        health -=damage;
        checkDeath();
    }
    public void checkDeath(){
        if(health <= 0){
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
        /*
             g.setColor(new Color(180,50,50));
             g.fillRect(x, y - (healthSpace + healthHeight), width, healthHeight);

            g.setColor(new Color(0,0,0));
            g.fillRect(x, y - (healthSpace + healthHeight), health, healthHeight);
            */

//health bar
        if(this.enemyID <=2) {
            g.setColor(new Color(50, 180, 50));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, health - 2, healthHeight - 2);
        }else if(this.health <= 250){
            g.setColor(new Color(180, 50, 180));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, (health - 2)/5, healthHeight - 2);
        }else if(this.health <= 1000){
            g.setColor(new Color(180, 50, 50));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, (health - 2)/20, healthHeight - 2);
        }else if(this.health <= 10000){
            g.setColor(new Color(0, 0, 0));
            g.fillRect(x + 1, y - (healthSpace + healthHeight) + 1, (health - 2)/200, healthHeight - 2);
        }

    }
}
