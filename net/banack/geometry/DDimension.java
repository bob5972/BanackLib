package net.banack.geometry;

public class DDimension
{
	public double width, height;
	
	
	public DDimension(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	
	public double getWidth()
	{
		return width;
	}
	
	
	public double getHeight()
	{
		return height;
	}
	
	
	public void setWidth(double w)
	{
		width = w;
	}
	
	
	public void setHeight(double h)
	{
		height = h;
	}
}
