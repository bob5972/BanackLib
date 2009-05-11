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

package net.banack.concurrent;

import net.banack.util.Queue;
import net.banack.util.EmptyQueueException;

/**
 * Thread-safe queue class with blocking dequeue.
 * 
 * @author Michael Banack
 */
public class BlockingQueue<V>
{
	private Queue<V> myQueue;


	private int myWaitingCount;

	public BlockingQueue()
	{
		super();
		myQueue = new Queue<V>();

		myWaitingCount = 0;
	}

	/**
	 * Checks if the queue is currently empty.
	 */
	public synchronized boolean isEmpty()
	{
		return myQueue.isEmpty();
	}

	/**
	 * Returns the current size of the queue.
	 */
	public synchronized int size()
	{
		return myQueue.size();
	}

	/**
	 * Inserts the given object into the queue.
	 */
	public synchronized void enqueue( V e )
	{
		myQueue.enqueue(e);
		if (myWaitingCount > 0)
			this.notify();
	}

	/**
	 * Waits until an object is in the queue, and then removes it. No guarantee
	 * is made about the order in which blocked threads receive incoming
	 * elements. In fact, an incoming element may be removed by a call to
	 * makeEmpty() or dequeueNow() before a blocked thread gets it.
	 * 
	 * @return The object that was removed from the queue.
	 */
	public synchronized V waitAndDequeue() throws InterruptedException
	{
		V oup;
		myWaitingCount++;
		while (isEmpty())
		{
			this.wait();
		}

		myWaitingCount--;
		oup = myQueue.dequeue();
		return oup;
	}

	/**
	 * Attempts to remove the first object from the queue, and will fail if the
	 * queue is empty. While the method does not wait for elements to be added,
	 * it will wait for a write lock.
	 * 
	 * @throws EmptyQueueException if the queue is empty.
	 */
	public synchronized V dequeueNow()
	{
		V oup;
		try
		{
			oup = myQueue.dequeue();
		}
		catch (EmptyQueueException e)
		{
			throw e;
		}
		return oup;
	}

	/**
	 * Removes all elements from the queue.
	 */
	public synchronized void makeEmpty()
	{
		myQueue.makeEmpty();
	}
}