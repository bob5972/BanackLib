package net.banack.geometry;

public class Point
{
	public int x,y;
	
	public Point(DPoint p)
	{
		x = (int)p.x;
		y=(int)p.y;
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

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}
}
