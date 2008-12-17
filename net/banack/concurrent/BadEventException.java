package net.banack.concurrent;

public class BadEventException extends RuntimeException
{
    private static final long serialVersionUID = 2345342907606341757L;

	public BadEventException()
	{
	}

	public BadEventException(String arg0)
	{
		super(arg0);
	}

	public BadEventException(Throwable arg0)
	{
		super(arg0);
	}

	public BadEventException(String arg0, Throwable arg1)
	{
		super(arg0, arg1);
	}

}
