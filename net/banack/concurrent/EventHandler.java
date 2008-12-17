package net.banack.concurrent;

public interface EventHandler
{
	//return true iff the event was processed
	//must return true if willProcess(o) returns true
	public boolean processEvent(Object o);
	
	public boolean willProcess(Object o);
}
