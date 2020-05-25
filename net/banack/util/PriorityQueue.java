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

import java.util.Comparator;

/**
 * Generic heap implementation of a priority queue.
 * 
 * @author Michael Banack
 * @version 1.0
 */
public class PriorityQueue<K>
{
	// instance variables
	private int mySize;// current size of the heap

	private Object[] myArray;// array for data storage

	private static int DEFAULT_SIZE = 16;
	private static int MIN_SIZE = 1;

	private static int ROOT = 0;
	
	private Comparator<K> myComp;

	/**
	 * Constructs an empty heap.
	 */
	@SuppressWarnings("unchecked")
	public PriorityQueue() {
		myArray = new Object[DEFAULT_SIZE];
		mySize = 0;
		myComp=new NaturalComparator();
	}

	public PriorityQueue(Comparator<K> c) {
		this();
		myComp=c;
	}

	// returns the current size of the Heap
	public int size()
	{
		return mySize;
	}

	// returns true iff the Heap is empty.
	public boolean isEmpty()
	{
		return mySize == 0;
	}

	// returns the entry with the smallest key
	@SuppressWarnings("unchecked")
	public K getMin()
	{
		return (K)myArray[ROOT];
	}

	// returns and deletes the entry with the smallest Key
	@SuppressWarnings("unchecked")
	public K removeMin()
	{
		K oup = (K)myArray[ROOT]; // output value
		myArray[ROOT] = myArray[mySize-1];
		myArray[mySize-1] = null;//get rid of the duplicate reference to help the garbage collector out later
		mySize--;
		percolateDown(ROOT);
		return oup;
	}

	//empties the PQ
	public void makeEmpty()
	{
		mySize = 0;//technically this is enough
		//but lets free all the bad references to help the garbage collector
		myArray = new Object[myArray.length];//keeps the current size
	}
	
	// adds a new entry to the heap
	public void insert(K e)
	{
		if (myArray.length == mySize) {
			Object[] temp = myArray;
			myArray = new Object[mySize * 2];
			System.arraycopy(temp, 0, myArray, 0, mySize);
		}
		myArray[mySize++] = e;
		percolateUp(mySize - 1);
	}
	
	//frees any superfluous memory
	public void trim()
	{
		if(mySize < myArray.length && myArray.length > MIN_SIZE)
		{
			Object[] temp = myArray;
			myArray = new Object[mySize>MIN_SIZE?mySize:MIN_SIZE];
			System.arraycopy(temp, 0, myArray, 0, mySize);
		}
	}

	// percolates down from a starting index to preserve heap order
	@SuppressWarnings("unchecked")
	private void percolateDown(int index)
	{
		while (!isLeaf(index)) {
			int smallestChild;// the index of the smallest child of index
			if (hasRightChild(index) && (myComp.compare((K)myArray[getLeftChild(index)],(K)myArray[getRightChild(index)]) > 0))
				smallestChild = getRightChild(index);
			else
				smallestChild = getLeftChild(index);

			if (myComp.compare((K)myArray[index],(K)myArray[smallestChild]) > 0) {
				Object temp = myArray[index];
				myArray[index] = myArray[smallestChild];
				myArray[smallestChild] = temp;
			}
			index = smallestChild;
		}
	}

	// percolates up from a starting index to preserve heap order.
	@SuppressWarnings("unchecked")
	private void percolateUp(int index)
	{
		while (!isRoot(index)) {
			if (myComp.compare((K)myArray[index],(K)myArray[getParent(index)]) < 0) {
				Object temp = myArray[index];
				myArray[index] = myArray[getParent(index)];
				myArray[getParent(index)] = temp;
			}
			index = getParent(index);
		}
	}

	// returns true iff index is the root of the heap
	private boolean isRoot(int index)
	{
		return index == ROOT;
	}

	// returns the index of index's left child
	private int getLeftChild(int index)
	{
		return 2 * index + 1;
	}

	// returns the index of index's right child
	private int getRightChild(int index)
	{
		return 2 * index + 2;
	}

	// returns true iff index is a leaf
	private boolean isLeaf(int index)
	{
		return getLeftChild(index) < mySize && getRightChild(index) < mySize;
	}

	// returns true iff index has a right child
	private boolean hasRightChild(int index)
	{
		return getRightChild(index) < mySize;
	}

	// returns the index of index's parent
	private int getParent(int index)
	{
		return (index - 1) / 2 + 1;
	}

}