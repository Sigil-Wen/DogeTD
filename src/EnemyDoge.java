/**[EnemyDoge.java]
 * Enemy class children Doge : the basic doge
 */
public class EnemyDoge extends Enemy{
    public int enemyID = Value.enemyDoge1;//enemyID

    /**[EnemyDoge]
     * Class constructor that spawns in enemy
     */
    public EnemyDoge(){
        spawnEnemy(enemyID); //spawn
        //enemy stats
        damage = 3;
        health = 50;
        walkSpeed = 10;
    }
}
