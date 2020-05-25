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

package net.banack.util;

public class IntPair
{
	public int x,y;
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int n)
	{
		x=n;
	}
	
	
	public void setY(int n)
	{
		y=n;
	}
	
	public IntPair()
	{
		x=y=0;
	}
	
	public IntPair(int nx, int ny)
	{
		x=nx;
		y=ny;
	}
	
	public void swap()
	{
		int temp = x;
		x = y;
		y = temp;
	}
	
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!(obj instanceof IntPair))
			return false;
		IntPair t = (IntPair)obj;
		return (this.x==t.x && this.y==t.y);
	}
	
	public int hashCode()
	{
		return ((x<<16)|(x>>>16))+y;
	}
}
