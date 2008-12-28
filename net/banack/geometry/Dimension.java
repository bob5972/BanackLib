package net.banack.geometry;

public class Dimension
{
	public int width,height;
	
	public Dimension(int width, int height)
	{
		this.width=width;
		this.height=height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setWidth(int w)
	{
		width=w;
	}
	
	public void setHeight(int h)
	{
		height=h;
	}
}
