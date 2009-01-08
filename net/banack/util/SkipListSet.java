package net.banack.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

public class SkipListSet<E> extends SkipList<E> implements SortedSet<E>
{
	
	public SkipListSet()
	{
		super();
		setIsASet(true);
	}
	
	
	public SkipListSet(Comparator<E> c)
	{
		super(c);
		setIsASet(true);
	}
	
	
	public SkipListSet(Collection<? extends E> list)
	{
		super(list,true);		
	}
	
	
	@SuppressWarnings("unchecked")
	public SortedSet<E> headSet(E toElement)
	{
		return (SortedSet<E>)headCollection(toElement);
	}
	
	
	@SuppressWarnings("unchecked")
	public SortedSet<E> subSet(E fromElement, E toElement)
	{
		return (SortedSet<E>)subCollection(fromElement,toElement);
	}
	
	
	@SuppressWarnings("unchecked")
	public SortedSet<E> tailSet(E fromElement)
	{
		return (SortedSet<E>)tailCollection(fromElement);
	}
	
}
