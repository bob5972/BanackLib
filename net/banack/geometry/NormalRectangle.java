package net.banack.geometry;

public class NormalRectangle extends Rectangle
{
	private int myX,myY;
	private int myWidth,myHeight;
	
	//x,y of upper left
	public NormalRectangle(int x, int y, int width, int height)
	{
		myX=x;
		myY=y;
		myWidth=width;
		myHeight=height;
	}
	
	public NormalRectangle(Point ul, Dimension d)
	{
		myX=ul.x;
		myY=ul.y;
		myWidth=d.width;
		myHeight=d.height;
	}
	
	public NormalRectangle(Point ul, Point ur, Point br, Point bl)
	{
		myX=ul.x;
		myY=ul.y;
		myWidth = ur.x-ul.x;
		myHeight=ul.y-bl.y;
		Point p = getBottomRight();
		if(p.x != br.x || p.y != br.y)
			throw new IllegalArgumentException("Given points do not make a rectangle!");
	}
	
	public NormalRectangle(Point ul, int width, int height)
	{
		myX=ul.x;
		myY=ul.y;
		myWidth=width;
		myHeight=height;
	}
	
	public int getX()
	{
		return myX;
	}
	
	public int getY()
	{
		return myY;
	}
	
	public int getWidth()
	{
		return myWidth;
	}
	
	public int getHeight()
	{
		return myHeight;
	}
	
	public Point getUpperLeft()
	{
		Point oup = new Point(myX,myY);
		return oup;
	}
	
	public Point getUpperRight()
	{
		Point oup = new Point(myX+myWidth,myY);
		return oup;
	}
	
	public Point getBottomLeft()
	{
		Point oup = new Point(myX,myY-myHeight);
		return oup;
	}
	
	public Point getBottomRight()
	{
		Point oup = new Point(myX+myWidth,myY-myHeight);
		return oup;
	}	
}
