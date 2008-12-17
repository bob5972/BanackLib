package net.banack.util;

import java.util.Comparator;

/**
 * Generic heap implementation of a priority queue.
 * 
 * @author Michael Banack
 * @version 1.0
 */
public class PriorityQueue {
	// instance variables
	private int mySize;// current size of the heap

	private Object[] myArray;// array for data storage

	private static int DEFAULT_SIZE = 16;
	private static int MIN_SIZE = 1;

	private static int ROOT = 0;
	
	private Comparator myComp;

	/**
	 * Constructs an empty heap.
	 */
	public PriorityQueue() {
		myArray = new Object[DEFAULT_SIZE];
		mySize = 0;
		myComp=new NaturalComparator();
	}

	public PriorityQueue(Comparator c) {
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
	public Object getMin()
	{
		return myArray[ROOT];
	}

	// returns and deletes the entry with the smallest Key
	public Object removeMin()
	{
		Object oup = myArray[ROOT]; // output value
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
	public void insert(Object e)
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
	private void percolateDown(int index)
	{
		while (!isLeaf(index)) {
			int smallestChild;// the index of the smallest child of index
			if (hasRightChild(index) && (myComp.compare(myArray[getLeftChild(index)],myArray[getRightChild(index)]) > 0))
				smallestChild = getRightChild(index);
			else
				smallestChild = getLeftChild(index);

			if (myComp.compare(myArray[index],myArray[smallestChild]) > 0) {
				Object temp = myArray[index];
				myArray[index] = myArray[smallestChild];
				myArray[smallestChild] = temp;
			}
			index = smallestChild;
		}
	}

	// percolates up from a starting index to preserve heap order.
	private void percolateUp(int index)
	{
		while (!isRoot(index)) {
			if (myComp.compare(myArray[index],myArray[getParent(index)]) < 0) {
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