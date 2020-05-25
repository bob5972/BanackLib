/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <github@banack.net>
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

import java.util.AbstractSet;
import java.util.Collection;

/**
 * Set implementation using linear searching in an array.
 * 
 * @author Michael Banack
 */
public class ArraySet<E> extends AbstractSet<E>
{
	private static final int DEFAULT_CAPACITY = 8;

	private Object[] myStuff;

	private int mySize;

	private int myModCount;// only incremented on removes

	/**
	 * Creates a new ArraySet with initial capacity of 8.
	 */
	public ArraySet()
	{
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructs a new ArraySet with the specified initial capacity.
	 */
	public ArraySet(int initialCapacity)
	{
		super();
		// check for lame parameters
		if (initialCapacity <= 0)
			initialCapacity = 1;
		myStuff = new Object[initialCapacity];
		mySize = 0;
		myModCount = 0;
	}

	/**
	 * Constructs a new ArraySet containing the elements of c. If two elements of c are equivalent according to their
	 * equals method it will add one of them.
	 */
	public ArraySet(Collection<E> c)
	{
		this(c.size());

		addAll(c);
	}


	public boolean add(E o)
	{
		if (o == null)
			throw new NullPointerException();

		for (int x = 0; x < mySize; x++) {
			if (myStuff[x].equals(o))
				return false;
		}
		// its not here yet
		if (mySize == myStuff.length)
			increaseCapacity();
		myStuff[mySize++] = o;
		return true;
	}


	public boolean remove(Object o)
	{
		if (o == null)
			throw new NullPointerException();
		for (int x = 0; x < mySize; x++) {
			if (myStuff[x].equals(o)) {
				remove(x);
				return true;
			}
		}
		// its not here
		return false;
	}

	private void increaseCapacity()
	{
		Object[] oup = new Object[myStuff.length * 2];
		for (int x = 0; x < mySize; x++) {
			oup[x] = myStuff[x];
		}
		myStuff = oup;
	}

	public boolean containsAll(Collection<?> c)
	{
		//"Logic is the beginning of wisdom...not the end." Spock (Star Trek VI)
		if(c.size()>mySize)
			return false;
		
		return super.containsAll(c);
	}

	public boolean contains(Object o)
	{
		for (int x = 0; x < mySize; x++) {
			if (myStuff[x].equals(o))
				return true;
		}
		return false;
	}

	/**
	 * @see java.util.Collection#size()
	 */
	public int size()
	{
		return mySize;
	}

	/**
	 * @see java.util.Collection#isEmpty()
	 */
	public boolean isEmpty()
	{
		return mySize == 0;
	}

	/**
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c)
	{
		boolean oup = false;
		for (int x = 0; x < mySize; x++) {
			if (!c.contains(myStuff[x])) {
				remove(x);
				x--;
				oup = true;
			}
		}
		return oup;
	}

	/**
	 * @see java.util.Set#removeAll(java.util.Collection c)
	 */
	public boolean removeAll(Collection<?> c)
	{
		boolean oup = false;
		for (int x = 0; x < mySize; x++) {
			if (c.contains(myStuff[x])) {
				remove(x);
				x--;
				oup = true;
			}
		}
		return oup;
	}

	// increment mod count here
	private void remove(int i)
	{
		myStuff[i] = myStuff[mySize - 1];
		myStuff[mySize - 1] = null;
		mySize--;
		myModCount++;
	}

	/**
	 * @see java.util.Set#clear()
	 */
	public void clear()
	{
		mySize = 0;
		myStuff = new Object[myStuff.length];
		myModCount++;
	}

	/**
	 * Returns the item at position i.<br>
	 * Added for ease of iteration. After removing an item at index i, index i now contains a new item and the size() is
	 * decreased by one.  So be careful...
	 * 
	 * @return The item at position i.
	 */
	@SuppressWarnings("unchecked")
	public E get(int i)
	{
		if (i > mySize || i < 0)
			throw new java.util.NoSuchElementException();
		return (E)myStuff[i];
	}

	/**
	 * @see java.util.Set#toArray()
	 */
	public Object[] toArray()
	{
		Object[] oup = new Object[mySize];

		System.arraycopy(myStuff, 0, oup, 0, mySize);

		return oup;
	}

	/**
	 * @see java.util.Collection#iterator()
	 */
	public java.util.Iterator<E> iterator()
	{
		return new ArraySetIterator();
	}

	private class ArraySetIterator implements java.util.Iterator<E>
	{
		private int myPos;

		private boolean canRemove;

		private int myItModCount;

		public ArraySetIterator()
		{
			myPos = 0;
			myItModCount = myModCount;
			canRemove = false;
		}

		public boolean hasNext()
		{
			checkModCount();
			return myPos < mySize;
		}

		@SuppressWarnings("unchecked")
		public E next()
		{
			checkModCount();
			if (hasNext()) {
				canRemove = true;
				return (E)myStuff[myPos++];
			} else
				throw new java.util.NoSuchElementException();
		}

		public void remove()
		{
			checkModCount();
			if (myPos == 0 || !canRemove)
				throw new IllegalStateException();
			ArraySet.this.remove(myPos);
			myPos--;
			canRemove = false;
			myItModCount = myModCount;
		}

		private void checkModCount()
		{
			if (myItModCount != myModCount)
				throw new java.util.ConcurrentModificationException();
		}
	}

}
