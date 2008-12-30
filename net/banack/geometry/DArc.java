package net.banack.geometry;

public final class DArc
{
	private DPoint center;
	private double radius, span, start;
	
	public DArc(DPoint center, double radius, double angleStart, double angleSpan)
	{
		this.center = center;
		this.radius = radius;
		this.span = angleSpan;
		this.start = angleStart;
	}

	public DPoint getCenter()
	{
		return center;
	}

	public double getRadius()
	{
		return radius;
	}

	public double getAngleSpan()
	{
		return span;
	}
	
	public double getAngleMidpoint()
	{
		return start+(span/2);
	}
	
	public double getAngleEnd()
	{
		return start+span;
	}

	public double getAngleStart()
	{
		return start;
	}
	
	public DArc rotate(double r)
	{
		return new DArc(center, radius, start+r,span);
	}
	
	
}
