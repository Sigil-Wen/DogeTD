/**
 * [MyLinkedList.java]
 * Program is a simple linked list. Used to store enemy Classes
 * @author Sigil Wen
 * @version 1.0
 * @since 2020.11.6
 *
 */
public class MyLinkedList<E> {

    private Node<E> head;

    /** Constructor [SimpleLinkedList]
     * Creates an instance of SimpleLinkedList
     */
    MyLinkedList(){
        this.head = null;
    }

    /**[add]
     * adds an item to the linked list
     * @param item the item added to the linked list
     */
    public void add(E item) {


        if (head == null) {
            head = new Node<E>(item,null);

        }else{

            Node<E> tempNode = head;

            while(tempNode.getNext()!=null) {
                tempNode = tempNode.getNext();
            }

            tempNode.setNext(new Node<E>(item,null));

        }

    }

    /**[get]
     * returns the item at index
     * @param index specified index of the item
     * @return returns the item at the index
     */

    public E get(int index) {

        Node<E> tempNode = head;

        for(int i = 0; i < index; i ++){
            tempNode = tempNode.getNext();
        }
        return tempNode.getItem();
    }

    /**[remove]
     * removes the item at index
     * @param index specified index of the item
     */

    public void remove(int index) {
        Node<E> tempNode = head;
        if(index==0){
            head = tempNode.getNext();
        }else if(index ==1){
            tempNode.setNext(tempNode.getNext().getNext());
        }
        else{
            for (int i = 0; i < index-1; i++) {
                tempNode = tempNode.getNext();
            }
                tempNode.setNext(tempNode.getNext().getNext());
        }
    }


    /**[size]
     * calculates the size of the linked list
     * @return and int specifying the size of the linked list
     */

    public int size() {

        int size = 0;

        Node<E> tempNode = head;  //tempNode used to cycle through the LinkedList

        while(tempNode != null){ //while loop cycles through the LinkedList
            //everytime the LinkedList cycles through a node, the size counter will increase
            size++;
            tempNode = tempNode.getNext();
        }

        return size;  //when the end of the LinkedList has been reached, return how many nodes the loop has cycled through
    }
}


/**
 * [Node.java]
 * a node class for simple linked lists
 */
class Node<E> {
    //item stored
    private E item;
    //reference to next item
    private Node<E> next;

    /**[Node]
     * Constructor for the class sets reference to next item null, indicating that it is the end of the list
     * @param item takes in stored value and stores it
     */
    public Node(E item){
        this.item = item;
        this.next = null;
    }

    /**[Node]
     * Constructor for the class takes in stored value and sets reference to next item in the list
     * @param item Item value to be store
     * @param next reference to next item in list
     */
    public Node(E item,Node<E> next){
        this.item = item;
        this.next = next;
    }

    /**[setNext]
     * @param next sets reference to next item in list
     */
    public void setNext(Node<E>next){
        this.next = next;
    }

    /**[getNext]
     * @return returns the reference to the next item in the list
     */
    public Node<E> getNext(){
        return this.next;
    }

    /**[getItem]
     * @return returns the current item stored in the node
     */
    public E getItem(){
        return this.item;
    }

}