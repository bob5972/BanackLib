package net.banack.geometry;

public final class DTriangle
{
	private DPoint p1, p2, p3;
	
	public DTriangle(DPoint p1, DPoint p2, DPoint p3)
	{
		this.p1=p1;
		this.p2=p2;
		this.p3=p3;
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
	
}
