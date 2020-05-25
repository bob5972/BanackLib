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

package net.banack.geometry;

public final class DPoint
{
	private double x,y;
	
	public final static DPoint ORIGIN = new DPoint(0,0);
	
	public DPoint()
	{
		x=0;
		y=0;
	}
	
	public DPoint(double x, double y)
	{
		this.x=x;
		this.y=y;
	}
	

	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public static DPoint newPolar(double radius, double angle)
	{
		DPoint oup = new DPoint();
		oup.x = radius*Math.cos(angle);
		oup.y = radius*Math.sin(angle);
		
		return oup;
	}
		
	
	public double getRadius()
	{
		return Math.sqrt(x*x+y*y);
	}
	
	public double getTheta()
	{
		double t= Math.atan(y/x);
		
		if(Double.isNaN(t))
		{
			if(x==0 && y==0)
				return 0;
			else if(y>0)
				return Math.PI/2;
			else
				return 3*Math.PI/2;
		}
		
		if(x<0)
			return t+Math.PI;
		else if(y <0)
			return t+Math.PI*2;
		else
			return t;
	}

	public DPoint add(DPoint p)
	{
		return new DPoint(x+p.x,y+p.y);
	}
	
	public DPoint subtract(DPoint p)
	{
		return new DPoint(x-p.x,y-p.y);
	}
	
	//subtracts d from both coordinates
	public DPoint subtract(double d)
	{
		return new DPoint(x-d,y-d);
	}
	
	//adds d to both coordinates
	public DPoint add(double d)
	{
		return new DPoint(x+d,y+d);
	}
	
	public DPoint invert()
	{
		return new DPoint(-x,-y);
	}
	
	public DPoint invertX()
	{
		return new DPoint(-x,y);
	}
	
	public DPoint invertY()
	{
		return new DPoint(x,-y);
	}
	
	public boolean equals(Object o)
	{
		if(this == o)
			return true;
		if(!(o instanceof DPoint))
			return false;
		DPoint p = (DPoint)o;
		
		if(p.x == x && p.y == y)
			return true;
		return false;
	}
	
	public boolean equals(DPoint p, double tolerance)
	{
		double dx= this.x-p.x;
		double dy = this.y-p.y;
		if(-tolerance <= dx && dx <= tolerance)
		{
			if(-tolerance <= dy && dy <= tolerance)
			{
				return true;
			}
		}
		return false;
	}
	
	public int hashCode()
	{
		//very bad hashing, but works for now
		return (int)(x*y);
	}
	
	public String toString()
	{
		return "("+x+","+y+")";
	}
	
}
