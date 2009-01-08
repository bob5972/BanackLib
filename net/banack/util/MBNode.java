package net.banack.util;

/**
 * Abstract Node Class
 * 
 * @author Michael Banack
 */

public class MBNode<E> {
	private E myValue;

	public MBNode() {
		myValue = null;
	}

	public MBNode(E x) {
		myValue = x;
	}

	public final E value() {
		return getValue();
	}

	public E getValue() {
		return myValue;
	}

	public void setValue(E x) {
		myValue = x;
	}

	/**
	 * Determines if the node is a terminal node, whatever that means in
	 * context. Defaults to true.
	 * 
	 * @return Whether or not the node is a terminal node
	 */
	public boolean isTerminator() {
		return true;
	}
}