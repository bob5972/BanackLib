package net.banack.util;

import java.util.Comparator;

public final class NaturalComparator implements Comparator {

	public NaturalComparator()
	{
		
	}
	
	public int compare(Object o1, Object o2)
	{
		Comparable c1,c2;
		c1 =(Comparable)o1; 
		c2 =(Comparable)o2;

		return c1.compareTo(c2);
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
