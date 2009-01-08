package net.banack.util;

/**
 * Generic Node from a Tree.
 * 
 * @author Michael Banack
 */

public class TreeNode<V> extends MBNode<V> {
	private TreeNode<V> myLeft, myRight;

	public TreeNode() {
		super();
		myLeft = null;
		myRight = null;
	}

	public TreeNode(V x) {
		super(x);
		myLeft = null;
		myRight = null;
	}

	public TreeNode(V x, TreeNode<V> left, TreeNode<V> right) {
		super(x);
		myLeft = left;
		myRight = right;
	}

	public TreeNode(TreeNode<V> left, TreeNode<V> right) {
		super();
		myLeft = left;
		myRight = right;
	}
	
	public MBNode<V> leftChild() {
		return myLeft;
	}

	public MBNode<V> rightChild() {
		return myRight;
	}

	public void setLeft(TreeNode<V> x) {
		myLeft = x;
	}

	public void setRight(TreeNode<V> x) {
		myRight = x;
	}

	public boolean isLeaf() {
		return (leftChild() == null && rightChild() == null);
	}

	public boolean isTerminator() {
		return isLeaf();
	}

//	public boolean isFull() {
//		return (leftChild() != null && rightChild() != null);
//	}

//	public void setNext(TreeNode n) {
//		if (isFull())
//			throw new IllegalStateException(
//			        "Unable to setNext() - TreeNode is Full");
//		if (myLeft == null) {
//			myLeft = n;
//			return;
//		} else if (myRight == null) {
//			myRight = n;
//			return;
//		}
//	}
}
