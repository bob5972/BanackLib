package net.banack.util;

public class MethodNotImplementedException extends RuntimeException
{
	public MethodNotImplementedException()
	{

	}

	public MethodNotImplementedException(String arg0)
	{
		super(arg0);
	}

	public MethodNotImplementedException(Throwable arg0)
	{
		super(arg0);
	}

	public MethodNotImplementedException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}
