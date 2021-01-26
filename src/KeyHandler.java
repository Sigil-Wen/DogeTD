/**[KeyHandler.java]
 * KeyHandler implements MouseMotionListener, and MouseListener in order to track user input for the game
 * @author Sigil Wen
 * @version 1.0
 * @since 2021.1.25
 */
import java.awt.event.*;
import java.awt.*;
public class KeyHandler implements MouseMotionListener, MouseListener {
    /**[mouseClicked]
     * runs if mouse is clicked
     * @param e MouseEvent
     */
    public void mouseClicked(MouseEvent e) { }

    /**[mousePressed]
     * sends the button clicked on the mouse to the Store
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        Screen.store.click(e.getButton());
    }

    /**[mouseReleased]
     * runs if mouse is released
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) { }
    /**[mouseEntered]
     * runs if mouse Entered
     * @param e MouseEvent
     */
    public void mouseEntered(MouseEvent e) { }
    /**[mouseExited]
     * runs if mouse exited
     * @param e MouseEvent
     */
    public void mouseExited(MouseEvent e) { }

    /**[mouseDragged]
     * if mouse is dragged, it updates the location of the mouse to Screen.mse (mouse position)
     * @param e MouseEvent
     */
    public void mouseDragged(MouseEvent e) {
        Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.myWidth)/2) ,(e.getY())-((Frame.size.height-(Screen.myHeight)) - (Frame.size.width - Screen.myWidth)/2));
    }
    /**[mouseMoved]
     * if mouse moved, it updates the location of the mouse to Screen.mse (mouse position)
     * @param e MouseEvent
     */
    public void mouseMoved(MouseEvent e) {
        Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.myWidth)/2) ,(e.getY())-((Frame.size.height-(Screen.myHeight)) - (Frame.size.width - Screen.myWidth)/2));
    }
}
