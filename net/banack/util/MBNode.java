package net.banack.util;

/**
 * Abstract Node Class
 * 
 * @author Michael Banack
 */

public class MBNode {
	private Object myValue;

	public MBNode() {
		myValue = null;
	}

	public MBNode(Object x) {
		myValue = x;
	}

	public final Object value() {
		return getValue();
	}

	public Object getValue() {
		return myValue;
	}

	public void setValue(Object x) {
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