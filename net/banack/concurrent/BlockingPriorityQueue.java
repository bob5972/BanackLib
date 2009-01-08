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
