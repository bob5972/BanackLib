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
		if (!(rhs instanceof PriorityNode))
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
