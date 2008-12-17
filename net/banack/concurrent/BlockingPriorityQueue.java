package net.banack.concurrent;

import net.banack.util.PriorityQueue;
import java.util.Comparator;
import net.banack.util.EmptyPQException;

public class BlockingPriorityQueue
{
	private PriorityQueue pq;
	private int myWaitingCount;
	
	public BlockingPriorityQueue()
	{
		pq = new PriorityQueue();
		myWaitingCount=0;
	}
	
	public BlockingPriorityQueue(Comparator c)
	{
		pq = new PriorityQueue(c);
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
	
	public synchronized Object getMinNow()
	{
		Object oup;
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
	
	public Object waitAndGetMin() throws InterruptedException
	{
		Object oup;
		myWaitingCount++;
		while (isEmpty())
		{
			this.wait();
		}

		myWaitingCount--;
		oup = pq.getMin();
		return oup;
	}
	
	
	public void insert(Object e)
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
