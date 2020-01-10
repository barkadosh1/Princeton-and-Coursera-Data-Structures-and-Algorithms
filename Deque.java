/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    Tal Bass
 *  Partner NetID:   tabass
 *  Partner Precept: P04
 * 
 *  Description:  This Deque class has several useful methods, but the important
 *  part is that it is established using a 2 directional linked lists, in which
 *  we are always tracking the next component to the right of first, and the 
 *  previous component from the right of last. isEmpty simply monitors whether
 *  the count is zero, or essentially if the queue is empty. Size returns the 
 *  number of elements in the queue/linked list. addFirst adds a new node in
 *  the front of the linked list, makes it the new first, and his add point 
 *  to the original first as its next. addLast adds a new node at the end of the
 *  linked list, makes it the new last, and points to the original last as its
 *  previous. removeFirst removes the first node and returns it, makes the 
 *  original next the new first, and establishes the new first's previous (as 
 *  null) and next. removeLast removes the last node and returns it, makes the 
 *  original previous the new last, and establishes the new last's previous and 
 *  next (as null). The iterator in this class is rather simple: it runs through
 *  the queue and prints out any non-null entry in order.
 * 
 ******************************************************************************/
 
// importing needed packages 
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

// Creates Deque class with needed instance variables
public class Deque<Item> implements Iterable<Item> {
   private Node first, last;
   private int count = 0; 
   
   // establishes two-direction link list
   private class Node {
       private Item item;
       private Node next; // tracks item to the right of item
       private Node prev; // tracks item to the left of item
   }

    // construct an empty deque
    public Deque() {
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        return (count == 0);
    }
    
   // returns the number of items on the deque 
    public int size() {
        return count;
    }
       
   // add the item to the front
   public void addFirst(Item item) {
       // throws exception if an item is null
       if (item == null) {
           throw new NullPointerException();
       }
       // If adding to empty queue, establish this node as both the first and last
       if (isEmpty()) {
           first = new Node();
           first.item = item;
           last = first;
       }
       // Otherwise, keep track of first, oldfrist, next, and prev (2-directions)
       else {
           Node oldfirst = first;
           first = new Node();
           first.item = item;
           first.next = oldfirst;
           oldfirst.prev = first;
           first.prev = null;
       }
       count++;
   }
   
   // add the item to the end
   public void addLast(Item item) {
       // throws exception if item is null
       if (item == null) {
           throw new NullPointerException();
       }
       // if adding to empty queue, establish that list equals first 
       if (isEmpty()) {
           last = new Node();
           last.item = item;
           first = last;
       }
       // Otherwise, keep track of first, oldfrist, next, and prev (2-directions)
       else {
           Node oldlast = last;
           last = new Node();
           last.item = item;
           last.next = null;
           last.prev = oldlast;
           oldlast.next = last;
       }
       count++;
   }
   
   // remove and return the item from the front
   public Item removeFirst() {
       // throw exception if removing from empty queue 
       if (isEmpty()) {
           throw new NoSuchElementException();
       }
       // if remove last one, get rid of first and last pointers
       else if (size() == 1) {
           count--;
           Item item = first.item;
           last = null;
           first = null;
           return item;
       }
       // otherwise, remove first, point to new next and prev, and return first
       else {
           count--;
           Item item = first.item;
           first = first.next;
           first.prev = null;
           return item;
       }
   }
   
   // remove and return the item from the end
   public Item removeLast() {
       // throw exception if removing from empty queue 
       if (isEmpty()) {
           throw new NoSuchElementException();
       }
       // if remove last one, get rid of first and last pointers
       else if (size() == 1) {
           count--;
           Item item = last.item;
           last = null;
           first = null;
           return item;
       }
       // otherwise, remove first, point to new next and prev, and return first
       else {
           count--;
           Item item = last.item;
           last = last.prev;
           last.next = null;
           return item;
       }
   }
   
   // return an iterator over items in order from front to end
   public Iterator<Item> iterator() {
       return new DequeIterator();
   }
   
   private class DequeIterator implements Iterator<Item> {
       private Node current = first;
       
       // checks if a next node exists (is not null)
       public boolean hasNext() {
           return current != null;
       }
       
       // returns the next node
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = current.item;
           current = current.next;
           return item;
       }
       
       // throws an exception if we try to use remove
       public void remove() {
           throw new UnsupportedOperationException();
       }
   }
     
   // unit testing (required) checks that all methods working properly
   public static void main(String[] args) {
       Deque<Integer> stuff = new Deque<Integer>(); // checks Deque method
       StdOut.println(stuff.isEmpty()); // checks isEmpty method
       StdOut.println(stuff.size()); // checks size method
       stuff.addFirst(6); // checks addFirst method
       stuff.addFirst(5);
       stuff.addFirst(4);
       stuff.addLast(7); // checks addLast method
       stuff.addLast(8);
       StdOut.println(stuff.removeLast()); // checks removeLast method
       StdOut.println(stuff.removeLast());
       StdOut.println(stuff.removeLast());
       StdOut.println(stuff.removeLast());
       StdOut.println(stuff.size());
       StdOut.println(stuff.removeFirst()); // checks removeFirst method
       stuff.addFirst(1);
       StdOut.println(stuff.removeFirst());
       
       StdOut.println("Starting now");
       stuff.addLast(1);
       stuff.addLast(2);
       stuff.addLast(3);
       stuff.addFirst(4);
       stuff.removeFirst();
       stuff.addFirst(6);
       stuff.addFirst(7);
       stuff.removeLast();
       
       // this method runs through our iterator to insure it functions
       for (Object i : stuff) {
           StdOut.println(i);
       }
       
   }
}