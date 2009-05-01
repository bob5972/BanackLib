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
