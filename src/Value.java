/**[Value.java]
 * Value stores global values used throughout the program- allows for consistency between, towers, groundTiles, icons, enemies, and death rewards
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */
public class Value {
    //Values set for ground Tiles, matches with ground Tileset graphics
    public static int groundGrass = 0;
    public static int groundRoad = 1;
    public static int groundWood =2;
    public static int groundMagma = 3;
    public static int groundIce = 4;

    //Values set for air Tiles, Values matches with air Tileset graphics
    public static int airAir = -1;
    public static int airHome = 0;
    public static int airCancel = 1;
    public static int airMario = 2;
    public static int airMad =3;
    public static int airCreeper =4;
    public static int airPepe = 5;
    public static int airStar = 6;
    public static int airBruh = 7;
    public static int airGD = 8;

    //Values set for enemies, matches with enemy Sprites
    public static int enemyAir = -1;
    public static int enemyDoge1 = 1;
    public static int enemySpeedy = 2;
    public static int enemyDoge2 = 3;
    public static int enemyZapdog =4;
    public static int enemyFiredoge = 5;
    public static int enemyIcedog =6;
    public static int enemyAngeldoge = 7;

    //death reward for each enemy
    public static int[] deathReward = {0,1,3,5,10,15,25, 1000};
}
