package net.banack.concurrent;

import net.banack.concurrent.CompositeEventHandler;

public class EventThread extends Thread implements Runnable
{
	private CompositeEventHandler eh;
	private BlockingQueue q;
	private boolean ignoreBadEvents;
	
	public EventThread()
	{
		super();
		eh = new CompositeEventHandler();
		q = new BlockingQueue();
		ignoreBadEvents=false;
		setDaemon(true);
	}
	
	public EventThread(EventHandler e)
	{
		this();
		eh.add(e);
	}
	
	synchronized public void addEventHandler(EventHandler e)
	{
		eh.add(e);
	}
	
	synchronized public void removeEventHandler(EventHandler e)
	{
		eh.remove(e);
	}
	
	public void setIgnoreBadEvents(boolean b)
	{
		ignoreBadEvents=b;
	}
	
	public boolean getIgnoreBadEvents()
	{
		return ignoreBadEvents;
	}
	
	public void postEvent(Object o)
	{
		q.enqueue(o);
	}
	
	public void run()
	{
		while(true)
		{
			Object cur;
			EventHandler handler;
			try{ 
				cur = q.waitAndDequeue();
			}
			catch(InterruptedException e)
			{
				//I guess someone wants us to stop?
				return;
			}
			
			synchronized(this)
			{
				//make sure eh doesn't get mucked with if people are calling add/remove at the same time
				handler=eh.getEventHandler(cur);
			}
			
			if(handler != null)
				handler.processEvent(cur);
			else if(!ignoreBadEvents)
				throw new BadEventException("Unable to process event.");
		}
	}
	
	
}
