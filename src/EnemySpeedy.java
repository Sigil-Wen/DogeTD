/**[EnemySpeedy.java]
 * Enemy class children Speedy : quick and nimble yet weak
 */
public class EnemySpeedy extends Enemy{
    public int enemyID = Value.enemySpeedy;  //enemyID

    /**[EnemySpeedy]
     * Class constructor that spawns in enemy
     */
    public EnemySpeedy(){
        spawnEnemy(enemyID); //spawns
        //sets stats
        this.health = 20;
        this.walkSpeed = 5;
        this.damage = 5;
    }
}
