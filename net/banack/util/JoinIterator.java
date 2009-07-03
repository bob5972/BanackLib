/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <bob5972@banack.net>
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

package net.banack.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class JoinIterator<E> implements Iterator<E>
{
	private Iterator<E> myA,myB;
	public JoinIterator(Iterator<E> a, Iterator<E> b)
	{
		myA = a;
		myB = b;
	}
		
	@Override
	public boolean hasNext()
	{
		return myA.hasNext() || myB.hasNext();
	}

	@Override
	public E next()
	{
		if(myA.hasNext())
			return myA.next();
		if(myB.hasNext())
			return myB.next();
		throw new NoSuchElementException();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();		
	}
	
}
