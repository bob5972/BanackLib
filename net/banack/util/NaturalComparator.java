package net.banack.util;

import java.util.Comparator;

public final class NaturalComparator<E extends Comparable<E> > implements Comparator<E>
{

	public NaturalComparator()
	{
		
	}
	
	public int compare(E o1, E o2)
	{
		return o1.compareTo(o2);
	}
	
	public boolean equals(Object obj)
	{
		//quick test
		if(this==obj)
			return true;
		
		if(obj instanceof NaturalComparator)
		{
			return true;
		}
		return false;
	}

}
