package net.banack.concurrent;

public interface Lock
{
	//Aquires the lock and blocks until the lock is aquired.
	public void lock() throws InterruptedException;
	
	//Releases the lock.
	public void unlock();	
}
