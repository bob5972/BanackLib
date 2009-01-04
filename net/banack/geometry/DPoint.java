package net.banack.geometry;

public final class DPoint
{
	private double x,y;
	
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

	public double getY()
	{
		return y;
	}

	public DPoint add(DPoint p)
	{
		return new DPoint(x+p.x,y+p.y);
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
	
	public int hashCode()
	{
		//very bad hashing, but works for now
		return (int)(x*y);
	}
	
	public String toString()
	{
		return "( "+x+","+y+"+ )";
	}
	
}
