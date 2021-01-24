import java.io.*;
import java.util.*;
public class Save {
    public void loadSave(File loadPath){ //takes in a file path
        try {
            Scanner loadScanner = new Scanner(loadPath); //creates a scanner that scans through the file

            while(loadScanner.hasNext()){
                Screen.killsToWin = loadScanner.nextInt();
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
