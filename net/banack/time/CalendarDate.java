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
import java.util.GregorianCalendar;
import java.util.Calendar;

/**
 * Class for representing calendar days.
 * 
 * @author Michael Banack
 */
public class CalendarDate
{
	public static int JANUARY=1;
	public static int FEBRUARY=2;
	public static int MARCH=3;
	public static int APRIL=4;
	public static int MAY=5;
	public static int JUNE=6;
	public static int JULY=7;
	public static int AUGUST=8;
	public static int SEPTEMBER=9;
	public static int OCTOBER=10;
	public static int NOVEMBER=11;
	public static int DECEMBER=12;
	
	public static int SUNDAY=1;
	public static int MONDAY=2;
	public static int TUESDAY=3;
	public static int WEDNESDAY=4;
	public static int THURSDAY=5;
	public static int FRIDAY=6;
	public static int SATURDAY=7;
	
	private GregorianCalendar myCalendar;
	
	/**
	 * Constructs a new CalendarDate using the current day.
	 */
	public CalendarDate()
	{
		super();
		myCalendar = new GregorianCalendar();
	}
	
	/**
	 * Constructs a new Calendar date using the given values.
	 */
	public CalendarDate(int year, int month, int dayOfMonth)
	{
		//GregorianCalendar numbers the months 0 (January) through 11 (December).
		myCalendar = new GregorianCalendar(year,month-1,dayOfMonth);
	}
	
	/**
	 * Constructs a new Calendar date using the given Timestamp.
	 */
	public CalendarDate(Timestamp t)
	{
		myCalendar = new GregorianCalendar();
		Date d = new Date(t.getTime());
		myCalendar.setTime(d);
	}
	
	/**
	 * Returns the year of this CalendarDate.
	 */
	public int getYear()
	{
		return myCalendar.get(Calendar.YEAR);
	}
	
	/**
	 * Returns the month of this CalendarDate.
	 */
	public int getMonth()
	{
		//GregorianCalendar numbers the months 0 (January) through 11 (December).
		return myCalendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * Returns the day of the month of this CalendarDate.
	 */
	public int getDay()
	{
		return myCalendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * Returns the weekday of this CalendarDate.
	 */
	public int getWeekday()
	{
		return myCalendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * Returns the datestamp of this CalendarDate.
	 * 
	 * The datestamp is an integer representation of the year,month, and day as follows:
	 *  YYYYMMDD
	 */
	public int getDatestamp()
	{
		int oup = getYear();
		oup*=Math.pow(10,2);
		oup +=getMonth();
		oup*=Math.pow(10,2);
		oup += getDay();
		return oup;
	}
	
	/**
	 * Returns a string representation of this CalendarDate.
	 */
	public String toString()
	{
		return myCalendar.toString();
	}
	
	/**
	 * Returns true iff rhs is a CalendarDate and represents the same year, month, and day as this does. 
	 */
	public boolean equals(Object rhs)
	{
		if(this==rhs)
			return true;
		if(rhs instanceof CalendarDate)
		{
			CalendarDate cr = (CalendarDate)rhs;
			if(cr.getYear() != this.getYear())
				return false;
			if(cr.getMonth() != this.getMonth())
				return false;
			if(cr.getDay() != this.getDay())
				return false;
			return true;
		}
		return false;
	}
}