
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MusicPlayer {
    public static void main(String[] args){
        try {
            FileInputStream fileInputStream = new FileInputStream("resources/doge.mp3");

            System.out.print("Song is playing");
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
