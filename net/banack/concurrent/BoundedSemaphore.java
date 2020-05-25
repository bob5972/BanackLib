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

public class BoundedSemaphore extends Semaphore
{
	private int myMax;
	
	//Constructs a BoundedSemaphore with a maximum of max, and max free resources
	public BoundedSemaphore(int max)
	{
		super(max);
		myMax=max;
	}
	
	//Constructs a BoundedSempahore with a maxmimum of max, and x free resources
	//If x>max, then once the count reaches max, it will never go above it again.
	public BoundedSemaphore(int max, int x)
	{
		super(x);
		myMax=max;
	}
	
	public void lock() throws InterruptedException
	{
		super.lock();		
	}
	
	public void unlock()
	{
		if(freeCount() + 1 > myMax)
			throw new IllegalStateException("Attempted to overflow a BoundedSemaphore maximum of "+myMax+" by 1");
		unlock();
	}
	
	//Adds x free resources
	public void unlock(int x)
	{
		if(x<0)
			throw new IllegalArgumentException("Illegal value: x < 0");
		if(freeCount() + x > myMax)
			throw new IllegalStateException("Attempted to overflow a BoundedSemaphore maximum of "+myMax+" by "+(freeCount()+x-myMax));
		while(x>0)
		{
			unlock();
			x--;
		}
	}

}
