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

import net.banack.concurrent.CompositeEventHandler;

public class EventThread extends Thread implements Runnable
{
	private CompositeEventHandler eh;
	private BlockingQueue<Object> q;
	private boolean ignoreBadEvents;
	
	public EventThread()
	{
		super();
		eh = new CompositeEventHandler();
		q = new BlockingQueue<Object>();
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
