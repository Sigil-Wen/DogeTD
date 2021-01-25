import java.io.*;
import java.util.*;
public class Save {

    public void loadSave(File loadPath){ //takes in a file path
        // first loading wave
        //second is spawn frequency
        //third is speed


        try {
            Scanner loadScanner = new Scanner(loadPath); //creates a scanner that scans through the file

            while(loadScanner.hasNext()){
                String wave = loadScanner.nextLine();
                String[] strArr = wave.split(" ");
                int[] intArr = new int[strArr.length];
                for (int i = 0; i<strArr.length;i++){
                    String num = strArr[i];
                    intArr[i] = Integer.parseInt(num);
                }
                Screen.wave = intArr;

                Screen.spawnTime = loadScanner.nextInt();
                Screen.speedDivisor = loadScanner.nextDouble();

                for(int y = 0; y<Screen.room.blocks.length; y++){
                    for(int x = 0; x<Screen.room.blocks[0].length; x++){
                        Screen.room.blocks[y][x].groundID = loadScanner.nextInt();
                    }
                }

                for(int y = 0; y<Screen.room.blocks.length; y++){
                    for(int x = 0; x<Screen.room.blocks[0].length; x++){
                        Screen.room.blocks[y][x].airID = loadScanner.nextInt();

                    }
                }

            }
            loadScanner.close();
        } catch(Exception e){

        }


    }
}
