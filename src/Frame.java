/**[Frame.java]
 * Frame class extends JFrame and sets up the Game GUI to encompass Screen.java (JPanel). Run this file to launch the game!
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public static String title = "Doge Tower Defence"; //sets JFrame title to Doge Tower Defence
    public static Dimension size = new Dimension(700,550); //sets game window dimension to 700 x 500

    /**[frame]
     * constructor for Frame
     */
    public Frame(){
        setTitle(title); //sets title to title
        setSize(size); //sets game window dim to size
        setResizable(false); //does not allow for resizability
        setLocationRelativeTo(null); //null centres window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //sets exit on close

        init();//initializes screen
    }

    /**[init]
     * creates a new GridLayout for the JFrame and adds JPanel Screen
     */
    public void init(){
        setLayout(new GridLayout(1,1,0,0));
        Screen screen = new Screen(this);
        add(screen); //adds screen JPanel to the JFrame
        setVisible(true); //makes visible
    }

    /**[main]
     * Main method of Frame. RUN to launch Doge Tower Defence
     * @param args
     */
    public static void main(String args[]){
        Frame frame = new Frame();
    } //creates a new Frame object called frame
}
