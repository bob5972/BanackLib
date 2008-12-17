package net.banack.util;

import java.util.AbstractSet;
import java.util.Collection;

/**
 * Set implementation using linear searching in an array.
 * 
 * @author Michael Banack
 */
public class ArraySet extends AbstractSet
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
	 * Constructs a new ArraySet with the specificed initial capacity.
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
	public ArraySet(Collection c)
	{
		this(c.size());

		addAll(c);
	}

	/**
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean add(Object o)
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

	/**
	 * @see java.util.Set#remove(java.lang.Object)
	 */
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

	/**
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c)
	{
		//"Logic is the beginning of wisdom...not the end." Spock (Star Trek VI)
		if(c.size()>mySize)
			return false;
		
		return super.containsAll(c);
	}

	/**
	 * @see java.util.Set#contains(java.lang.Object)
	 */
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
	public boolean retainAll(Collection c)
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
	public boolean removeAll(Collection c)
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
	public Object get(int i)
	{
		if (i > mySize || i < 0)
			throw new java.util.NoSuchElementException();
		return myStuff[i];
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
	public java.util.Iterator iterator()
	{
		return new ArraySetIterator();
	}

	private class ArraySetIterator implements java.util.Iterator
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

		public Object next()
		{
			checkModCount();
			if (hasNext()) {
				canRemove = true;
				return myStuff[myPos++];
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
