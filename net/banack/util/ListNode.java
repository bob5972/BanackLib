package net.banack.util;

/**
 * Generic node in a linked list.
 * 
 * @author Michael Banack
 */

public class ListNode<V> extends MBNode<V> {
	private ListNode<V> myNext;

	public ListNode() {
		super();
		myNext = null;
	}

	public ListNode(V x) {
		super(x);
		myNext = null;
	}

	public ListNode(V x, ListNode<V> n) {
		super(x);
		myNext = n;
	}

	public ListNode(ListNode<V> n) {
		super();
		myNext = n;
	}

	public final ListNode<V> next() {
		return getNext();
	}
	
	public ListNode<V> getNext() {
		return myNext;
	}

	public void setNext(ListNode<V> x) {
		myNext = x;
	}

	public boolean isTail() {
		return (next() == null);
	}

	public boolean isTerminator() {
		return isTail();
	}
}
