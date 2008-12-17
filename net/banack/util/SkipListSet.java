package net.banack.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

public class SkipListSet extends SkipList implements SortedSet
{
	
	public SkipListSet()
	{
		super();
		setIsASet(true);
	}
	
	
	public SkipListSet(Comparator c)
	{
		super(c);
		setIsASet(true);
	}
	
	
	public SkipListSet(Collection list)
	{
		super(list,true);		
	}
	
	
	public SortedSet headSet(Object toElement)
	{
		return (SortedSet)headCollection(toElement);
	}
	
	
	public SortedSet subSet(Object fromElement, Object toElement)
	{
		return (SortedSet)subCollection(fromElement,toElement);
	}
	
	
	public SortedSet tailSet(Object fromElement)
	{
		return (SortedSet)tailCollection(fromElement);
	}
	
}
