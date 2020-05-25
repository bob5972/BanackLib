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

package net.banack.concurrent;

import net.banack.concurrent.Lock;

/**
 * Lock to coordinate readers and writers. Only one writer may posess the lock
 * at any given time, but any number of readers can.
 * 
 * @author Michael Banack
 */
public class ReadWriteLock implements Lock
{
	private int myWaitingReaders;

	private int myWaitingWriters;

	//0 indiciates empty lock
	//a positive value indicates the number of readers
	//-1 indicates a writer posesses the lock
	private int myAccessCount;

	//a positive value indicates that many readers are next
	//-1 indicates a writer is next
	//0 indicates no preference
	private int myNext;

	private Object myMutex;

	/**
	 * Constructs a new ReadWriteLock.
	 */
	public ReadWriteLock()
	{
		super();
		myWaitingReaders = 0;
		myWaitingWriters = 0;
		myAccessCount = 0;
		myMutex = new Object();
		myNext = 0;
	}

	/**
	 * Waits to gain non-exclusive read access to this lock.
	 */
	public void getReadLock() throws InterruptedException
	{
		synchronized (myMutex)
		{
			myWaitingReaders++;

			while ( !(myAccessCount >= 0 && myNext >= 0) )
			{
				myMutex.wait();
			}

			if (myNext > 0)
				myNext--;
			else if (myNext == 0 && myWaitingWriters > 0)
				myNext = -1;
			myWaitingReaders--;
			myAccessCount++;
		}
	}
	
	/**
	 * Obtains an exclusive write access to this lock.  See getWriteLock().
	 */
	public void lock() throws InterruptedException
	{
		getWriteLock();
	}

	/**
	 * Waits to gain exclusive write access to this lock.
	 */
	public void getWriteLock() throws InterruptedException
	{
		synchronized (myMutex)
		{
			myWaitingWriters++;

			while ( !(myAccessCount == 0 && myNext <= 0) )
			{
				myMutex.wait();
			}

			myNext = 0;
			myWaitingWriters--;
			myAccessCount = -1;
		}
	}
	
	/**
	 * Releases a previously obtained lock.  Same as release().
	 */
	public final void unlock()
	{
		release();
	}

	/**
	 * Releases a previously obtained lock.
	 */
	public void release()
	{
		synchronized (myMutex)
		{
			if (myAccessCount == 0)
				throw new IllegalStateException("release called on an unoccupied lock");

			if (myAccessCount == -1)
			{
				//I was a writer.
				myAccessCount = 0;
				if (myWaitingReaders > 0)
				{
					myNext = myWaitingReaders;
					myMutex.notifyAll();
				}
				else if (myWaitingWriters > 0)
				{
					myNext = -1;
					//There are only writers waiting, so lets just wake up one.
					myMutex.notify();
				}
				else
				{
					myNext = 0;
					//Nobody's waiting, so there's no one to wake up.
				}
			}
			else
			{
				//I was a reader.
				myAccessCount--;
				if (myAccessCount == 0)
				{
					if (myNext <= 0 && myWaitingWriters > 0)
					{
						myNext = -1;
						//If there are only writers waiting, we just need to wake one.
						//otherwise, wake everyone to make sure we get a writer.
						if (myWaitingReaders == 0)
							myMutex.notify();
						else
							myMutex.notifyAll();
					}
					else
					{
						//Either this guy ran through before any queued readers got a chance,
						//	(in which case myNext > 0 and they still have a reserved spot).
						//or nobody's waiting, so there's no one to wake up.
						//	(any readers that came should have just gone in, and not waited).
						if (myNext < 0)
							myNext = 0;
					}
				}
			}
		}
	}
}