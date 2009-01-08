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
