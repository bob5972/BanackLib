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

public class Mutex implements Lock
{
	private boolean amOccupied;
	private Object myMonitor;
	
	public Mutex()
	{
		amOccupied=false;
		myMonitor=new Object();
	}

	public void lock() throws InterruptedException
	{
		synchronized(myMonitor)
		{
			while(amOccupied)
			{
				myMonitor.wait();
			}
			amOccupied=true;
			return;
		}
	}

	public void unlock()
	{
		synchronized(myMonitor)
		{
			if(!amOccupied)
				throw new IllegalStateException("unlock() called on empty Mutex!");
			amOccupied=false;
			myMonitor.notify();
		}
	}
	
	public boolean isOccupied()
	{
		return amOccupied;
	}

}
