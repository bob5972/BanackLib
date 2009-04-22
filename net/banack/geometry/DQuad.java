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
}
