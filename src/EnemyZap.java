/**[EnemyZap.java]
 * Enemy class children Zap Doge : the electric doge
 */
public class EnemyZap extends Enemy{
    public int enemyID = Value.enemyZapdog;//enemyID

    /**[EnemyZap]
     * Class constructor that spawns in enemy
     */
    public EnemyZap(){
        spawnEnemy(enemyID);//spawn
        //stats
        this.health = 250;
        this.walkSpeed = 12;
        this.damage = 15;
    }
}
