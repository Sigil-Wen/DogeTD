/**[EnemyAngelDoge.java]
 * Enemy class children Angel Doge : the FINAL FORM OF doge
 */
public class EnemyAngelDoge extends Enemy{
    public int enemyID = Value.enemyAngeldoge; //enemyID

    /**[EnemyAngelDoge]
     * Class constructor that spawns in enemy
     */
    public EnemyAngelDoge(){
        spawnEnemy(enemyID); //spawn
        //stats
        this.health = 10000;
        this.walkSpeed = 30;
        this.damage = 100;
    }
}
