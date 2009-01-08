package net.banack.geometry;

import java.util.Iterator;

public abstract class Rectangle
{
	public abstract Point getUpperLeft();
	
	public abstract Point getUpperRight();
	
	public abstract Point getBottomLeft();

	public abstract Point getBottomRight();
	
	public Point getVertex(int n)
	{
		switch(n)
		{
			case 0:
				return getUpperLeft();
			case 1:
				return getUpperRight();
			case 2:
				return getBottomLeft();
			case 3:
				return getBottomRight();
		}
		throw new IllegalArgumentException("Bad n value of "+n);
	}
	
	public Iterator<Point> iterator()
	{
		return new Iterator<Point>(){
			private int n=0;
			public boolean hasNext(){
				return n<4;
			}
			
			public Point next()
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
