/**[EnemyFireDoge.java]
 * Enemy class children FireDoge : the flaming tanky doge
 */
public class EnemyFireDoge extends Enemy{
    public int enemyID = Value.enemyFiredoge; //enemyID

    /**[EnemyFireDoge]
     * Class constructor that spawns in enemy
     */
    public EnemyFireDoge(){
        spawnEnemy(enemyID); //spawn
        //stats
        this.health = 1000;
        this.walkSpeed = 30;
        this.damage = 20;
    }
}
