package net.banack.util;

import java.util.Collection;
import java.util.Comparator;

public interface SortedCollection extends Collection
{
	//Returns the comparator associated with this sorted set, or null if it uses its elements' natural ordering.
	Comparator comparator();
	
	//Returns the first (lowest) element currently in this sorted set.
	Object first();
	
	//Returns a view of the portion of this sorted set whose elements are strictly less than toElement.
	SortedCollection headCollection(Object toElement);
	
    //Returns the last (highest) element currently in this sorted set.
	Object last();
	
	//Returns a view of the portion of this sorted set whose elements range from fromElement, inclusive, to toElement, exclusive.
	SortedCollection subCollection(Object fromElement, Object toElement);
	
	//Returns a view of the portion of this sorted set whose elements are greater than or equal to fromElement.
	SortedCollection tailCollection(Object fromElement);
	
	//Returns the object that comes after e in the collection (or would come after, if e were in the collection) 
	// returns null if e is the maximum or greater than the maximum
	Object successor(Object e);
	
	//eturns the object that comes before e in the collection (or would come before, if e were in the collection) 
	// returns null if e is the minimum or greater than the minimum
	Object predecessor(Object e);
}
