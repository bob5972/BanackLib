package net.banack.util;

/**
 * Stack class with linked-list implementation
 * 
 * @author Michael Banack
 */

public class Stack<V> implements Pile<V>
{
	
	// Instance Variables
	ListNode<V> myTop;
	
	int mySize;
	
	// Methods
	public Stack()
	{
		myTop = null;
		mySize = 0;
	}
	
	public V top()
	{
		if (!isEmpty())
		{
			return myTop.value();
		}
		throw new EmptyStackException("Topped an Empty Stack");
	}
	
	public boolean isEmpty()
	{
		return (mySize == 0);
	}
	
	public int size()
	{
		return mySize;
	}
	
	public void push(V obj)
	{
		myTop = new ListNode<V>(obj, myTop);
		mySize++;
	}
	
	public void makeEmpty()
	{
		myTop = null;
		mySize = 0;
	}
	
	public V pop()
	{
		if (!isEmpty())
		{
			ListNode<V> tempNode = myTop;
			myTop = myTop.getNext();
			mySize--;
			return tempNode.value();
		}
		throw new EmptyStackException("Popped an Empty Stack");
	}
	
	public final void add(V v)
	{
		push(v);
	}
	
	public final V peek()
	{
		return top();
	}
	
	public final V next()
	{
		return pop();
	}
	
	public String toString()
	{
		if (mySize == 0)
			return new String("Stack: Empty");
		String str = new String("Stack: Size=" + mySize + " Elements=");
		ListNode<V> current = myTop;
		while (current.getNext() != null)
		{
			str += current.value() + ",";
			current = current.getNext();
		}
		str += current.value();
		return str;
	}
	
	public Object clone()
	{
		Stack<V> oup = new Stack<V>();
		if (isEmpty())
			return oup;
		ListNode<V> cur = myTop;
		while (!cur.isTail())
		{
			oup.push(cur.value());
			cur = cur.getNext();
		}
		return oup;
	}
	
	// with the top item in position 0
	public Object[] toArray()
	{
		if (isEmpty())
			return new Object[0];
		Object[] oup = new Object[mySize];
		ListNode<V> cur = myTop;
		for (int x = 0; x < mySize; x++)
		{
			oup[x] = cur.value();
			cur = cur.getNext();
		}
		return oup;
	}	
}