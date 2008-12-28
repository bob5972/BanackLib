package net.banack.geometry;

public class DPoint
{
	public double x,y;
	
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
	
	public DPoint(Point p)
	{
		this.x=(double)p.x;
		this.y=(double)p.y;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}
	
	public DPoint add(DPoint p)
	{
		return new DPoint(x+p.x,y+p.y);
	}
	
}
