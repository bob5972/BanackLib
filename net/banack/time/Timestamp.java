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

package net.banack.time;

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