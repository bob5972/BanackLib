package net.banack.util;

public class PriorityNode extends MBNode {

	private int myPriority;
	
	public PriorityNode(Object x, int priority) {
		super(x);
		myPriority=priority;
	}
	
	public final int priority()
	{
		return getPriority();
	}
	
	public int getPriority()
	{
		return myPriority;
	}
	
	//Compares the priorities
	//Two nodes with the same priorities are equal
	public int compareTo(Object rhs)
	{
		//quick test for equality
		if(this==rhs)
			return 0;
		if(! (rhs instanceof PriorityNode))
			throw new ClassCastException("Invalid Comparison");
		PriorityNode prhs = (PriorityNode)rhs;
		return myPriority-prhs.myPriority;
	}
	
	//Two nodes with the same priorities are equal
	public boolean equals(Object obj)
	{
		if(this==obj)
			return true;
		
		if(! (obj instanceof PriorityNode))
			return false;
		return this.compareTo(obj)==0;
	}
}
