package net.banack.util;

public class IntPair
{
	public int x,y;
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public void setX(int n)
	{
		x=n;
	}
	
	
	public void setY(int n)
	{
		y=n;
	}
	
	public IntPair()
	{
		x=y=0;
	}
	
	public IntPair(int nx, int ny)
	{
		x=nx;
		y=ny;
	}
	
	public void swap()
	{
		int temp = x;
		x = y;
		y = temp;
	}
	
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(!(obj instanceof IntPair))
			return false;
		IntPair t = (IntPair)obj;
		return (this.x==t.x && this.y==t.y);
	}
	
	public int hashCode()
	{
		return ((x<<16)|(x>>>16))+y;
	}
}
