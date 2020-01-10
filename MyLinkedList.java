package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	
	// MyLinkedList is my implementation 
	public MyLinkedList() {
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	
	// add is my implementation 
	public boolean add(E element) 
	{	
		
		if (element == null) {
	        throw new NullPointerException("Element cannot be null");
	    }
		
		else {
			LLNode<E> node = new LLNode<E>(element);
			
			node.prev = tail.prev;
			node.next = tail;
			tail.prev = node;
			(node.prev).next = node;
			
			size++;
	
			return true;
		}

	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	
	// get is my implementation 
	public E get(int index) 
	{
		if (index < 0) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else if (index > (size -1)) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else {
			LLNode<E> curr = head.next;
			for (int i = 0; i < index; i++) {
				curr = curr.next;
			}
			E element_data = curr.data;
			
			return element_data;
		}
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	
	// add is my implementation 
	public void add(int index, E element ) 
	{

		if (element == null) throw new NullPointerException("Element cannot be null");		
		else if (index < 0) throw new IndexOutOfBoundsException("Index is out of bounds");	
		else if (index > size) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else {
			
			if (index < (size/2)) {
				LLNode<E> added = new LLNode<E>(element);
				LLNode<E> curr = head.next;
				
				for (int i = 0; i < index; i++) {
					curr = curr.next;
				}
				
				added.prev = curr.prev;
				added.next = curr;
				curr.prev = added;
				(added.prev).next = added;
				
				size ++;
			}
			
			else {
				LLNode<E> added = new LLNode<E>(element);
				LLNode<E> curr = tail;
				
				for (int i = size; i > index; i--) {
					curr = curr.prev;
				}
				
				added.prev = curr.prev;
				added.next = curr;
				curr.prev = added;
				(added.prev).next = added;
				
				size ++;
			}
			
		}
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	
	// remove is my implementation 
	public E remove(int index) 
	{
		
		if (index < 0) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else if (index > (size -1)) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else {
			LLNode<E> curr = head.next;
			for (int i = 0; i < index; i++) {
				curr = curr.next;
			}
			E element_data = curr.data;
			
			(curr.prev).next = curr.next;
			(curr.next).prev = curr.prev;
			curr.next = null;
			curr.prev = null;
			
			size -- ;
			
			return element_data;
		}
		
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	
	// set is my implementation 
	public E set(int index, E element) 
	{
		
		if (element == null) {
	        throw new NullPointerException("Element cannot be null");
	    }
		
		else if (index < 0) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else if (index > (size -1)) throw new IndexOutOfBoundsException("Index is out of bounds");
		
		else {
			LLNode<E> curr = head.next;
			for (int i = 0; i < index; i++) {
				curr = curr.next;
			}
			E element_data = curr.data;
			
			curr.data = element; 
			
			return element_data;
		}

	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	
	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
