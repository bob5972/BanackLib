package net.banack.util;

/**
 * Generic node in a linked list.
 * 
 * @author Michael Banack
 */

public class ListNode extends MBNode {
	private ListNode myNext;

	public ListNode() {
		super();
		myNext = null;
	}

	public ListNode(Object x) {
		super(x);
		myNext = null;
	}

	public ListNode(Object x, ListNode n) {
		super(x);
		myNext = n;
	}

	public ListNode(ListNode n) {
		super();
		myNext = n;
	}

	public final ListNode next() {
		return getNext();
	}
	
	public ListNode getNext() {
		return myNext;
	}

	public void setNext(ListNode x) {
		myNext = x;
	}

	public boolean isTail() {
		return (next() == null);
	}

	public boolean isTerminator() {
		return isTail();
	}
}
