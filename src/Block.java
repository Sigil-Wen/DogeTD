import java.awt.*;

public class Block extends Rectangle {

    public Rectangle towerRange;
    public int towerRangeSize=130;
    public int groundID;
    public int airID;
    public int shotEnemy = -1;
    public int loseTime = 25, loseFrame = 0;
    public boolean attacking = false;

    public Block(int x, int y, int width, int height, int groundID, int airID){
        setBounds(x,y,width,height);
        towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        this.groundID = groundID;
        this.airID = airID;
    }

    public void draw(Graphics g){
        g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null);

        if(airID != Value.airAir){
            g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
        }
    }

    public void physic() {
        if (shotEnemy != -1 && towerRange.intersects(Screen.enemies[shotEnemy])) {
            attacking = true;
        }else{
            attacking = false;
        }
        if(!attacking) {
            if (airID == Value.airMario) {
                for (int i = 0; i < Screen.enemies.length; i++) {
                    if (Screen.enemies[i].inGame) {
                        if (towerRange.intersects(Screen.enemies[i])) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
        }
        if(attacking){
            if(loseFrame >= loseTime){
                Screen.enemies[shotEnemy].loseHealth(1);
                loseFrame = 0;
            }else{
                loseFrame+=1;
            }
            if(Screen.enemies[shotEnemy].isDead()){
                attacking=false;
                shotEnemy = -1;
                Screen.killed+=1;
                Screen.hasWon();
            }

        }
    }
    public void getMoney(int enemyID){
                Screen.money += Value.deathReward[enemyID];
    }

    public void attack(Graphics g){
        if(Screen.isDebug) {
            if (airID == Value.airMario) {
                g.drawRect(towerRange.x, towerRange.y, towerRange.width, towerRange.height);
            }
        }
            if(attacking){
                g.setColor(new Color(255,255,0));
                g.drawLine(x+(width/2),y+(height/2), Screen.enemies[shotEnemy].x+(Screen.enemies[shotEnemy].width/2), Screen.enemies[shotEnemy].y +(Screen.enemies[shotEnemy].height/2));
            }


    }

}
