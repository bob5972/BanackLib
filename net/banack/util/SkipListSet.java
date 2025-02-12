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
