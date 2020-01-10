/******************************************************************************
 *  Name:    Bar Kadosh
 *  NetID:   bkadosh
 *  Precept: P03
 *
 *  Partner Name:    Tal Bass
 *  Partner NetID:   tabass
 *  Partner Precept: P04
 * 
 *  Description:  This RandomizedQueue class has several useful methods, 
 *  but the important aspect is that it is established using arrays. However,
 *  the issue this presents is that we need to specify a size for the array,
 *  which is not something we have to do with a linkedin list. To overcome 
 *  this limitation, we start with an array of size 1. Whenever the array 
 *  is filled to its capacity, we create an array with a size of twice the 
 *  number of non-null componenets in the array, and use a for loop to transfer 
 *  all non-null items from the full array into the larger array. If the array
 *  ever shrinks to 1/4 its capacity, we count the number of non null componenets,
 *  and transfer them to a new array twice the size of that count. The 
 *  RandomizedQueue method creates a new empty array of size 1. The isEmpty method
 *  checks whether there is anything in our array/queue. The size method simply
 *  returns how many components are in the array/queue. The iterator is intended to
 *  run through the array and print the non-null entries in a random order, and it 
 *  especially should only print each entry once.
 * 
 * 
 ******************************************************************************/

// Import needed packages 
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

// RandomizedQueue with needed instance variables
public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] rq;
   private int count = 0; // number of non-null values in array
   private int capacity = 1; // represents total size of array
   private int index = 0; // index of array we are looking at
   
    // construct an empty randomized queue
    // we start with an empty array of size 1
    // Our program will resize it later when it needs to be
    public RandomizedQueue() {
    rq = (Item[]) new Object[1];    
    }
    
   // is the queue empty? 
    public boolean isEmpty() {
    return count == 0;    
    }
    
   // return the number of items on the queue
    public int size() {
    return count;
    }
    
    // takes the count value given and creates a temporary array of twice the size
    // It transfers all non-null values to this temporary array, and then assigns
    // this temp array back to the rq array. The new capacity value is now the size
    // of the array, and the starting index is assigned the value of count
    private void resize(int cap) {
        Item[] copy = (Item[]) new Object[cap];
        int j = 0;
        for (int i = 0; i < capacity; i++) {
            if (rq[i] != null) {
                copy[j] = rq[i];
                j++;
            }
        }
        rq = copy;
        capacity = cap;
        index = count;
    }
 
   // adds the item to the current index
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        
        // resize if our array is full
        if (capacity == index) {
            resize(2*count);
        }
        rq[index] = item;
        count++;
        index++; 
    }
    
   // remove and return a random item from the queue
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        
        int temp1 = StdRandom.uniform(index);
        Item temp2 = rq[temp1];
        
        // ensures that we don't return a null
        while (temp2 == null) {
            temp1 = StdRandom.uniform(index);
            temp2 = rq[temp1];
        }
        
        rq[temp1] = null;
        
        // resize if we reach 1/4 the capacity
        if (count*4 >= capacity) {
            resize(count*2);
        }
        count--;
        return temp2;
    }
    
   // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int temp1 = StdRandom.uniform(index);
        
        // ensures that we don't return a null
        while (rq[temp1] == null) {
            temp1 = StdRandom.uniform(index);
        }
        return rq[temp1];
    }
    
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        // create a new array check
        Item[] check = (Item[]) new Object[count];
        int j = 0;
        // Move all non-null items into check
        for (int i = 0; i < capacity; i++) {
            if (rq[i] != null) {
                check[j] = rq[i];
                j++;
            }
        }
        // We shuffle check, so that a random output is given each time
        StdRandom.shuffle(check);
        return new RQIterator(check);
    }
    
    // implements functions of the iterator 
    private class RQIterator implements Iterator<Item> { 
        private Item[] check2;
        private int i = 0;
        
        // creates a check2 (to avoid a constantly changing instance variable)
        private RQIterator(Item[] check) {
            check2 = check;
        }
        
        // Tells us if the array has a next item or not
        public boolean hasNext() {
            return (i < count);
        }
        
        // Returns the next item
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return check2[i++];
        }
        
        // Throws an exception if try to call remove
        public void remove() {
            throw new UnsupportedOperationException(); 
        }
    }
    
    // unit testing (required) checks that all methods working properly
    public static void main(String[] args) {
        
        // checks RandomizedQueue
        RandomizedQueue stuff = new RandomizedQueue();
        
        StdOut.println(stuff.isEmpty()); // verifies isEmpty
        StdOut.println(stuff.size()); // verifies size
        
        // A lot of these test enqueue 
        stuff.enqueue(1);
        stuff.enqueue(2);
        stuff.enqueue(3);
        stuff.enqueue(4);
        stuff.enqueue(5);
        stuff.enqueue(1);
        stuff.enqueue(2);
        stuff.enqueue(3);
        stuff.enqueue(4);
        stuff.enqueue(5);
        stuff.enqueue(1);
        stuff.enqueue(2);
        stuff.enqueue(3);
        stuff.enqueue(4);
        stuff.enqueue(5);
        stuff.enqueue(1);
        stuff.enqueue(2);
        stuff.enqueue(3);
        stuff.enqueue(4);
        stuff.enqueue(5);
        
        // tests sample 
        StdOut.println(stuff.sample());
        
        // The following 3 lines test the dequeue method
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        
        // The rest just broadly verify our programs functionality 
        stuff.enqueue(4);
        stuff.enqueue(5);
        stuff.enqueue(5);
        stuff.enqueue(1);
        stuff.enqueue(2);
        stuff.enqueue(3);
        stuff.enqueue(4);
        stuff.enqueue(5);
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue());
        StdOut.println(stuff.dequeue()); 
        StdOut.println(stuff.dequeue());
        stuff.enqueue(1);
        StdOut.println(stuff.dequeue());
        stuff.enqueue(2);
        StdOut.println(stuff.dequeue());   
        stuff.enqueue(1);
        stuff.enqueue(2);
        stuff.enqueue(3);
        stuff.enqueue(4);
        
        // Over here, we are testing our iterator
        // We run through a double nested for loop to see if each inner
        // and outer sequence is both random and complete
        StdOut.println("Starting now");
        for (Object i : stuff) {
            StdOut.println(i);
            for (Object j : stuff) {
                StdOut.println(j);
            }
        }
        
        
        
    }
}
   
