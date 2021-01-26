/**[Save.java]
 * Save Class loads in levels from /save folder
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */

import java.io.*;
import java.util.*;
public class Save {
    /**[loadSave]
     * loads in file from /save folder to load in as a level.
     * load save loads in the enemy wave, frequency of spawn, room layout, as well as increase in enemy speed
     * @param loadPath filepath for level loading in
     */
    public void loadSave(File loadPath){ //takes in a file path
        // first loading wave
        //second is spawn frequency
        //third is speed

        try {
            Scanner loadScanner = new Scanner(loadPath); //creates a scanner that scans through the file
            while(loadScanner.hasNext()){
                //first line of level file is the enemy wave ex: 1 2 3 4 5 6 7 2 4 5 5
                // each number correlates to the enemyID
                String wave = loadScanner.nextLine();

                //parsing the line to an int[]
                String[] strArr = wave.split(" ");
                int[] intArr = new int[strArr.length];
                for (int i = 0; i<strArr.length;i++){
                    String num = strArr[i];
                    intArr[i] = Integer.parseInt(num);
                }
                Screen.wave = intArr; //sets the Screen.wave to parsed wave loaded in from the file
                Screen.spawnTime = loadScanner.nextInt(); //sets spawn time to next integer
                Screen.speedDivisor = loadScanner.nextDouble();//sets speed Divisor (how much the enemy speed increases by) to next integer

                //loops through the entire room to load in each groundID (room layout)
                for(int y = 0; y<Screen.room.blocks.length; y++){
                    for(int x = 0; x<Screen.room.blocks[0].length; x++){
                        Screen.room.blocks[y][x].groundID = loadScanner.nextInt();
                    }
                }
                //loops through the entire room to load in each airID (includes home base)
                for(int y = 0; y<Screen.room.blocks.length; y++){
                    for(int x = 0; x<Screen.room.blocks[0].length; x++){
                        Screen.room.blocks[y][x].airID = loadScanner.nextInt();

                    }
                }
            }
            loadScanner.close(); //closes scanner
        } catch(Exception e){
            e.printStackTrace(); //prints loading errors
        }
    }
}
