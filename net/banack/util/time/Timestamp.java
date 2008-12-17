package net.banack.util.time;

import java.util.Date;

/**
 * Represents an instant in time down to millisecond accuracy.
 * 
 * @author Michael Banack <bob5972@banack.net>
 */
public class Timestamp
{
	private Date myDate;

	/**
	 * Constructs a new Timestamp using the current time.
	 */
	public Timestamp()
	{
		super();
		myDate = new Date();
	}
	
	/**
	 * Constructs a new Timestamp representing t milliseconds since the standard base time (January 1, 1970, 00:00:00 GMT).
	 */
	public Timestamp(long t)
	{
		super();
		myDate = new Date(t);
	}
	
	/**
	 * Returns a string representation of this Timestamp.
	 */
	public String toString()
	{
		return myDate.toString();
	}
	
	/**
	 * Returns the number of milliseconds since January 1, 1970, 00:00:00 GMT. 
	 */
	public long getTime()
	{
		return myDate.getTime();
	}

}