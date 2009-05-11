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

//designed to turn any set into a map
public class KeyNode<K extends Comparable<K> ,V> extends MBNode<V> implements Comparable<KeyNode<K,V> >
{

	private K myKey;
	
	public KeyNode() {
		super();
		myKey=null;
	}

	public KeyNode(K key,V value)
	{
		super(value);
		myKey=key;
	}
	
	public final K key()
	{
		return getKey();
	}
	
	public K getKey()
	{
		return myKey;
	}
	
	//Compares the keys
	//Two KeyNodes with the same keys are equal
	public int compareTo(KeyNode<K,V> rhs)
	{
		//quick test
		if(this==rhs)
			return 0;
		
//		if(! (rhs instanceof KeyNode))
//			throw new ClassCastException("Invalid Comparison");
		
		return myKey.compareTo(rhs.myKey);
	}
	
	//Two KeyNodes with the same keys are equal
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj)
	{
		//quick test for equality
		if(this==obj)
			return true;
		
		if(! (obj instanceof KeyNode))
			return false;
		
		return this.compareTo((KeyNode<K,V>)obj)==0;
	}

}
