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

public class Semaphore implements Lock
{
	private int myCount;
	private Object myMonitor;
	
	//Constructs a new Semaphore with count free resources.
	//If count is negative, it means that no one will be granted a lock until unlock is called more than count times.
	//Once the count reaches 0, it will never again go below 0.
	public Semaphore(int count)
	{
		myCount=count;
		myMonitor = new Object();
	}

	//Obtains the nonexclusive lock, by using up a single free resource.
	public void lock() throws InterruptedException
	{
		synchronized(myMonitor)
		{
			while(myCount <= 0)
			{
				myMonitor.wait();
			}
			
			myCount--;
			return;
		}
	}
	

	//Releases an obtained lock, by adding a single free resource.
	public void unlock()
	{
		synchronized(myMonitor)
		{
			myCount++;
			myMonitor.notify();
		}
	}
	
	//Returns the number of free resources
	public int freeCount()
	{
		return myCount;
	}

}
