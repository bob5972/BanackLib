package net.banack.util;

/**
 * Queue Class with linked List implementation.
 * 
 * @author Michael Banack
 */

public class Queue<V>
{
	// Instance Variables
	ListNode<V> myTop;
	
	ListNode<V> myBottom;
	
	int mySize;
	
	public Queue()
	{
		myTop = null;
		myBottom = null;
		mySize = 0;
	}
	
	public V front()
	{
		if (mySize <= 0)
			throw new EmptyQueueException("front on Empty Queue");
		return myTop.value();
	}
	
	public boolean isEmpty()
	{
		return mySize == 0;
	}
	
	public int size()
	{
		return mySize;
	}
	
	public void enqueue(V elem)
	{
		if (mySize == 0)
		{
			myBottom = new ListNode<V>(elem, null);
			myTop = myBottom;
		} else
		{
			myBottom.setNext(new ListNode<V>(elem, null));
			myBottom = myBottom.getNext();
		}
		
		mySize++;
	}
	
	public V dequeue()
	{
		if (mySize <= 0)
			throw new EmptyQueueException("dequeue on Empty Stack");
		V myReturn = myTop.value();
		myTop = myTop.getNext();
		mySize--;
		return myReturn;
	}
	
	public void makeEmpty()
	{
		myTop = null;
		mySize = 0;
		myBottom = null;
	}
	
	public String toString()
	{
		if (mySize == 0)
			return new String("Queue: Empty");
		String str = new String("Queue: Size=" + mySize + " Elements=");
		ListNode<V> current = myTop;
		while (current != myBottom)
		{
			str += current.value() + ",";
			current = current.getNext();
		}
		str += current.value();
		return str;
	}
	
	public Object clone()
	{
		Queue<V> oup = new Queue<V>();
		for (int x = 0; x < mySize; x++)
		{
			oup.enqueue(front());
			enqueue(dequeue());
		}
		return oup;
	}
}
