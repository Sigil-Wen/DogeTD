import java.awt.*;

public class Block extends Rectangle {

    public Rectangle towerRange; //Rectangle outlining the towers Range
    public int towerRangeSize=130; //towers Range size
    public int groundID; // groundID
    public int airID; //airID
    public int shotEnemy = -1;//
    public int loseTime = 50, loseFrame = 0;
    public boolean attacking = false;

    public Block(int x, int y, int width, int height, int groundID, int airID){
        setBounds(x,y,width,height);
        towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        this.groundID = groundID;
        this.airID = airID;
    }

    public void draw(Graphics g){
        g.drawImage(Screen.tileset_ground[groundID], x, y, width, height, null);
        if(this.airID == Value.airMad){
            towerRangeSize = 20;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }
        else if(this.airID == Value.airCreeper){
            towerRangeSize = 250;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airPepe){
            towerRangeSize = 200;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airStar){
            towerRangeSize = 50;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airGD){
            towerRangeSize = 250;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }else if(this.airID == Value.airBruh){
            towerRangeSize = 350;
            towerRange = new Rectangle(x-(towerRangeSize/2), y-(towerRangeSize/2), width+ (towerRangeSize), height+ (towerRangeSize));
        }
        //draws image if not air
        if(airID != Value.airAir){
            g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
        }

    }

    public void physic() {
        if (shotEnemy != -1 && towerRange.intersects(Screen.enemyList.get(shotEnemy))) {
            attacking = true;
        }else{
            attacking = false;
        }
        if(!attacking) {
            if (airID == Value.airMario) {
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
            if (airID == Value.airMad) { //airMad
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
            if (airID == Value.airCreeper) { //airCreeper
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
            if (airID == Value.airPepe) { //airCreeper
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
            if (airID == Value.airStar) { //airCreeper
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
            if (airID == Value.airGD) { //airCreeper
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
            if (airID == Value.airBruh) { //airCreeper
                for (int i = 0; i < Screen.enemyList.size(); i++) {
                    if (Screen.enemyList.get(i).inGame) {
                        if (towerRange.intersects(Screen.enemyList.get(i))) {
                            attacking = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
        }
        if(attacking){
            if(loseFrame >= loseTime){
                if(this.airID == Value.airMario) {
                    Screen.enemyList.get(shotEnemy).loseHealth(1);
                }else if(this.airID == Value.airMad){
                    Screen.enemyList.get(shotEnemy).loseHealth(3);
                }
                else if(this.airID == Value.airCreeper){
                    Screen.enemyList.get(shotEnemy).loseHealth(2);

                }else if(this.airID == Value.airPepe){
                    Screen.enemyList.get(shotEnemy).loseHealth(4);

                }else if(this.airID == Value.airStar){
                    Screen.enemyList.get(shotEnemy).loseHealth(12);
                }else if(this.airID == Value.airGD){
                    Screen.enemyList.get(shotEnemy).loseHealth(8);
                }else if(this.airID == Value.airBruh){
                    Screen.enemyList.get(shotEnemy).loseHealth(10);
                }
                loseFrame = 0;
            }else{
                loseFrame+=1;
            }
            if(Screen.enemyList.get(shotEnemy).isDead()){
                attacking=false;
                shotEnemy = -1;
                Screen.hasWon();
            }

        }
    }
    public void getMoney(int enemyID){
        Screen.money += Value.deathReward[enemyID];
    }

    public void attack(Graphics g){
        if(Screen.isDebug) {
            if (airID >= Value.airMario) {
                g.setColor(new Color(0,0,0, 35));
                g.drawRect(towerRange.x, towerRange.y, towerRange.width, towerRange.height);
            }
        }
            if(attacking){
                g.setColor(new Color(255,255,0));
                g.drawLine(x+(width/2),y+(height/2), Screen.enemyList.get(shotEnemy).x+(Screen.enemyList.get(shotEnemy).width/2), Screen.enemyList.get(shotEnemy).y +(Screen.enemyList.get(shotEnemy).height/2));
            }
    }

}
