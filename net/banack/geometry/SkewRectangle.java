package net.banack.geometry;

public class SkewRectangle extends Rectangle
{	
	private Point ul,ur,br,bl;
	
	//doesn't actually check for right angles...
	public SkewRectangle(Point ul, Point ur, Point br, Point bl)
	{
		this.ul=ul;
		this.ur=ur;
		this.br=br;
		this.bl=bl;
	}
	
	public Point getBottomLeft()
	{
		return bl;
	}
	
	
	public Point getBottomRight()
	{
		return br;
	}
	
	
	public Point getUpperLeft()
	{
		return ul;
	}
	
	
	public Point getUpperRight()
	{
		return ur;
	}	
}
