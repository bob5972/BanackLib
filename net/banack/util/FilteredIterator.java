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

public class FilteredIterator<E> implements Iterator<E>
{
	private Iterator<E> myIterator;
	private boolean hasNext;
	private E myNext;
	private Filter<E> myFilter;
	
	public FilteredIterator(Iterator<E> i, Filter<E> f)
	{
		myIterator = i;
		myFilter = f;
		
		hasNext = i.hasNext();
		
		loadNext();
	}

	public boolean hasNext()
	{
		return hasNext;
	}


	public E next()
	{
		if(!hasNext)
			throw new NoSuchElementException();
		E oup = myNext;
		loadNext();
		return oup;
	}

	public void remove()
	{
		throw new UnsupportedOperationException();		
	}
	
	
	private void loadNext()
	{
		if(!hasNext)
			return;
		while(myIterator.hasNext())
		{
			E n = myIterator.next();
			if(myFilter.test(n))
			{
				myNext = n;
				hasNext=true;
				return;
			}
		}
		
		hasNext = false;
		myNext=null;
	}
}
