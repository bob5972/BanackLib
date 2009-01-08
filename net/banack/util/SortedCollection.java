package net.banack.util;

import java.util.Collection;
import java.util.Comparator;

public interface SortedCollection<E> extends Collection<E>
{
	//Returns the comparator associated with this sorted set, or null if it uses its elements' natural ordering.
	Comparator<E> comparator();
	
	//Returns the first (lowest) element currently in this sorted set.
	E first();
	
	//Returns a view of the portion of this sorted set whose elements are strictly less than toElement.
	SortedCollection<E> headCollection(E toElement);
	
    //Returns the last (highest) element currently in this sorted set.
	E last();
	
	//Returns a view of the portion of this sorted set whose elements range from fromElement, inclusive, to toElement, exclusive.
	SortedCollection<E> subCollection(E fromElement, E toElement);
	
	//Returns a view of the portion of this sorted set whose elements are greater than or equal to fromElement.
	SortedCollection<E> tailCollection(E fromElement);
	
	//Returns the object that comes after e in the collection (or would come after, if e were in the collection) 
	// returns null if e is the maximum or greater than the maximum
	E successor(E e);
	
	//returns the object that comes before e in the collection (or would come before, if e were in the collection) 
	// returns null if e is the minimum or greater than the minimum
	E predecessor(E e);
}
