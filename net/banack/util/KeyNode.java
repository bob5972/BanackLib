package net.banack.util;

//designed to turn any set into a map
public class KeyNode extends MBNode implements Comparable {

	private Object myKey;
	
	public KeyNode() {
		super();
		myKey=null;
	}

	public KeyNode(Object key,Object value)
	{
		super(value);
		myKey=key;
	}
	
	public final Object key()
	{
		return getKey();
	}
	
	public Object getKey()
	{
		return myKey;
	}
	
	//Compares the keys
	//Two KeyNodes with the same keys are equal
	public int compareTo(Object rhs)
	{
		//quick test
		if(this==rhs)
			return 0;
		
		if(! (rhs instanceof KeyNode))
			throw new ClassCastException("Invalid Comparison");
		Comparable c1 = (Comparable)myKey;
		KeyNode krhs = (KeyNode)rhs;
		Comparable c2 = (Comparable) krhs.myKey;
		return c1.compareTo(c2);
	}
	
	//Two KeyNodes with the same keys are equal
	public boolean equals(Object obj)
	{
		//quick test for equality
		if(this==obj)
			return true;
		
		if(! (obj instanceof KeyNode))
			return false;
		return this.compareTo(obj)==0;
	}

}
