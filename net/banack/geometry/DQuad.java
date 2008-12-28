package net.banack.geometry;

import java.util.Iterator;

public class DQuad
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


	public DPoint getP1()
	{
		return p1;
	}

	public void setP1(DPoint p1)
	{
		this.p1 = p1;
	}

	public DPoint getP2()
	{
		return p2;
	}

	public void setP2(DPoint p2)
	{
		this.p2 = p2;
	}

	public DPoint getP3()
	{
		return p3;
	}

	public void setP3(DPoint p3)
	{
		this.p3 = p3;
	}

	public DPoint getP4()
	{
		return p4;
	}

	public void setP4(DPoint p4)
	{
		this.p4 = p4;
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
	
	public Iterator iterator()
	{
		return new Iterator(){
			private int n=1;
			public boolean hasNext(){
				return n<=4;
			}
			
			public Object next()
			{
				return getVertex(n++);
			}
			public void remove()
			{
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
}
