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
