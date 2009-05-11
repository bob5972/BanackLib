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

import java.util.ArrayList;

public class CompositeEventHandler implements EventHandler
{
	private ArrayList<EventHandler> eh;
	
	public CompositeEventHandler()
	{
		//I'm not expecting to get too many children
		eh = new ArrayList<EventHandler>(4);
	}
	
	public void add(EventHandler e)
	{
		eh.add(e);
	}
	
	//removes the first handler found that equals(e)
	public void remove(EventHandler e)
	{
		for(int x=0;x<eh.size();x++)
		{
			if(e.equals(eh.get(x)))
			{
				eh.remove(x);
				return;
			}
		}
	}
	
	public void removeAll()
	{
		eh.clear();
	}
	
	//processes o by the first handler for which willProcess(o) is true
	//ie the last handler added can be a "catch-all," but I wouldn't rely on this behavior...
	public boolean processEvent(Object o)
	{
		for(int x=0;x<eh.size();x++)
		{
			if(((EventHandler)eh.get(x)).willProcess(o))
			{
				((EventHandler)eh.get(x)).processEvent(o);
				return true;
			}
			
		}
		return false;

	}

	public boolean willProcess(Object o)
	{
		for(int x=0;x<eh.size();x++)
		{
			if(((EventHandler)eh.get(x)).willProcess(o))
				return true;
		}
		return false;
	}
	
	//Returns the event handler that will process o or null if none will
	//currently, if there are more than one, it returns the first one, but I wouldn't rely on this behavior...
	public EventHandler getEventHandler(Object o)
	{
		for(int x=0;x<eh.size();x++)
		{
			if(((EventHandler)eh.get(x)).willProcess(o))
				return (EventHandler)eh.get(x);
		}
		return null;
	}

}
