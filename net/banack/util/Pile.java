package net.banack.util;

public interface Pile<V>
{
	public V peek();
	
	public V next();
	
	public void add(V v);
	
	public void makeEmpty();
	
	public boolean isEmpty();
	
	public int size();
}
