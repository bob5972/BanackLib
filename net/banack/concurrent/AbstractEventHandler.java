package net.banack.concurrent;

public abstract class AbstractEventHandler implements EventHandler
{

	public abstract boolean processEvent(Object o);

	public boolean willProcess(Object o)
	{
		return true;
	}

}
