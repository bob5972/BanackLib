/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <github@banack.net>
 * 
 * BanackLib is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * BanackLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with BanackLib.  If not, see <http://www.gnu.org/licenses/>.
 */

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
