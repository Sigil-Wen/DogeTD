/**[EnemyIceDoge.java]
 * Enemy class children IceDoge : a slick icy speed demon doge
 */
public class EnemyIceDoge extends Enemy{
    public int enemyID = Value.enemyIcedog; //enemy id

    /**[EnemyIceDoge]
     * Class constructor that spawns in enemy
     */
    public EnemyIceDoge(){
        spawnEnemy(enemyID);//spawn
        //stats
        this.health = 200;
        this.walkSpeed = 5;
        this.damage = 25;
    }
}
