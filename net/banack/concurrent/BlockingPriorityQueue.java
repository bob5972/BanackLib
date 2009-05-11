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

import net.banack.util.PriorityQueue;
import java.util.Comparator;
import net.banack.util.EmptyPQException;

public class BlockingPriorityQueue<K>
{
	private PriorityQueue<K> pq;
	private int myWaitingCount;
	
	public BlockingPriorityQueue()
	{
		pq = new PriorityQueue<K>();
		myWaitingCount=0;
	}
	
	public BlockingPriorityQueue(Comparator<K> c)
	{
		pq = new PriorityQueue<K>(c);
		myWaitingCount=0;
	}
	
	public synchronized boolean isEmpty()
	{
		return pq.isEmpty();
	}
	
	public synchronized int size()
	{
		return pq.size();
	}
	
	public synchronized K getMinNow()
	{
		K oup;
		try
		{
			oup = pq.getMin();
		}
		catch (EmptyPQException e)
		{
			throw e;
		}
		return oup;
		
	}
	
	public K waitAndGetMin() throws InterruptedException
	{
		K oup;
		myWaitingCount++;
		while (isEmpty())
		{
			this.wait();
		}

		myWaitingCount--;
		oup = pq.getMin();
		return oup;
	}
	
	
	public void insert(K e)
	{
		pq.insert(e);
		if(myWaitingCount >0)
			this.notify();
	}
	
	public void trim() throws InterruptedException
	{
		pq.trim();
	}
	
	public synchronized void makeEmpty()
	{
		pq.makeEmpty();
		
	}
	
	
}
