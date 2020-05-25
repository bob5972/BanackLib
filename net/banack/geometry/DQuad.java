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

import java.util.Iterator;

public final class DQuad
{
	private DPoint p1, p2, p3, p4;
	
	public DQuad(DPoint p1, DPoint p2, DPoint p3, DPoint p4)
	{
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
	}
	
	public DQuad()
	{
		this.p1 = new DPoint();
		this.p2 = new DPoint();
		this.p3 = new DPoint();
		this.p4 = new DPoint();		
	}
	
	public DQuad(DQuad r)
	{
		this(r.p1,r.p2,r.p3,r.p4);
	}

	public DPoint getP1()
	{
		return p1;
	}

	public DPoint getP2()
	{
		return p2;
	}

	public DPoint getP3()
	{
		return p3;
	}

	public DPoint getP4()
	{
		return p4;
	}

	public DPoint getVertex(int n)
	{
		switch(n)
		{
			case 1:
				return getP1();
			case 2:
				return getP2();
			case 3:
				return getP3();
			case 4:
				return getP4();
		}
		throw new IllegalArgumentException("Bad n value of "+n);
	}
	
	public DQuad add(DPoint p)
	{
		DQuad oup = new DQuad();
		oup.p1 = p1.add(p);
		oup.p2 = p2.add(p);
		oup.p3 = p3.add(p);
		oup.p4 = p4.add(p);
		
		return oup;
	}
	
	public DQuad subtract(DPoint p)
	{
		DQuad oup = new DQuad();
		oup.p1 = p1.subtract(p);
		oup.p2 = p2.subtract(p);
		oup.p3 = p3.subtract(p);
		oup.p4 = p4.subtract(p);
		
		return oup;
	}
	
	public DPoint getCenter()
	{
		return new DPoint((p1.getX()+p2.getX()+p3.getX()+p4.getX())/4,(p1.getY()+p2.getY()+p3.getY()+p4.getY())/4);
	}
	
	public Iterator<DPoint> iterator()
	{
		return new Iterator<DPoint>(){
			private int n=1;
			public boolean hasNext(){
				return n<=4;
			}
			
			public DPoint next()
			{
				return getVertex(n++);
			}
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	public String toString()
	{
		return "( "+p1+", "+p2+", "+p3+", "+p4+" )";
	}
	
	public DQuad getBoundingRectangle()
	{
		double minx,maxx,miny,maxy;
		minx = maxx = p1.getX();
		miny = maxy = p1.getY();
		
		double cx,cy;
		DPoint v;
		
		for(int x=2;x<=4;x++)
		{
			v = getVertex(x);
			cx = v.getX();
			cy = v.getY();
			if(minx > cx)
				minx = cx;
			if(maxx < cx)
				maxx = cx;
			if(miny > cy)
				miny = cy;
			if(maxy < cy)
				maxy = cy;
		}
		
		return getBoundingRectangle(minx,maxx,miny,maxy);
	}
	
	public static DQuad getBoundingRectangle(double minx, double maxx, double miny, double maxy)
	{
		DPoint p1 = new DPoint(minx,maxy);
		DPoint p2 = new DPoint(maxx,maxy);
		DPoint p3 = new DPoint(maxx,miny);
		DPoint p4 = new DPoint(minx,miny);
		return new DQuad(p1,p2,p3,p4);
	}
	
	public static DQuad getBoundingRectangle(double radius)
	{
		return new DQuad(new DPoint(-radius,radius),new DPoint(radius,radius),new DPoint(radius,-radius),new DPoint(-radius,-radius));
	}
}
