/**[EnemyChad.java]
 * Enemy class children Chad DOGE : the CHAD doge
 */
public class EnemyChad extends Enemy{
    public int enemyID = Value.enemyDoge2;//enemyID

    /**[EnemyChad]
     * Class constructor that spawns in enemy
     */
    public EnemyChad(){
        spawnEnemy(enemyID);//spawn
        //stats
        this.health = 100;
        this.walkSpeed = 7;
        this.damage = 10;
    }
}
