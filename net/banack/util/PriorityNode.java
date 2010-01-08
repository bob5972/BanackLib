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

public class PriorityNode<V> extends MBNode<V> implements Comparable<PriorityNode<V> >
{
	
	private int myPriority;
	
	public PriorityNode(V x, int priority)
	{
		super(x);
		myPriority = priority;
	}
	
	public final int priority()
	{
		return getPriority();
	}
	
	public int getPriority()
	{
		return myPriority;
	}
	
	// Compares the priorities
	// Two nodes with the same priorities are equal
	public int compareTo(PriorityNode<V> rhs)
	{
		// quick test for equality
		if (this == rhs)
			return 0;
		if (!(rhs instanceof PriorityNode<?>))
			throw new ClassCastException("Invalid Comparison");
		PriorityNode<V> prhs = (PriorityNode<V>) rhs;
		return myPriority - prhs.myPriority;
	}
	
	// Two nodes with the same priorities are equal
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		
		if (!(obj instanceof PriorityNode))
			return false;
		return this.compareTo((PriorityNode<V>)obj) == 0;
	}
}
