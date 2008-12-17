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
