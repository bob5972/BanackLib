/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <bob5972@banack.net>
 * 
 * BanackLib is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * BanackLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with BanackLib.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.banack.util;

/**
 * Queue class with linked List implementation.
 * 
 * @author Michael Banack
 */

public class Queue<V> implements Pile<V>
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
	
	public final void add(V obj)
	{
		enqueue(obj);
	}
	
	public final V peek()
	{
		return front();
	}
	
	public final V next()
	{
		return dequeue();
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
