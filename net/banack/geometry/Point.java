package net.banack.geometry;

public class Point
{
	public int x,y;
	
	public Point(DPoint p)
	{
		x = (int)p.getX();
		y=(int)p.getY();
	}
	
	public Point(int xc, int yc)
	{
		x=xc;
		y=yc;
	}
	
	public boolean equals(Object o)
	{
		if(! (o instanceof Point))
			return false;
		Point rhs = (Point)o;
		if(rhs.x != this.x)
			return false;
		if(rhs.y != this.y)
			return false;
		return true;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
