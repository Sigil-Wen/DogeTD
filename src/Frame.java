
import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public static String title = "Doge Tower Defence";
    public static Dimension size = new Dimension(700,550);
    public Frame(){
        setTitle(title); //sets title to title
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null); //null centres
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
    }
    public void init(){
        setLayout(new GridLayout(1,1,0,0));
        Screen screen = new Screen(this);
        add(screen); //adds screen JPanel to the JFrame
        setVisible(true);
    }

    public static void main(String args[]){
        Frame frame = new Frame();
    }
}
