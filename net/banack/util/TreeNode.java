package net.banack.util;

/**
 * Generic Node from a Tree.
 * 
 * @author Michael Banack
 */

public class TreeNode extends MBNode {
	private TreeNode myLeft, myRight;

	public TreeNode() {
		super();
		myLeft = null;
		myRight = null;
	}

	public TreeNode(Object x) {
		super(x);
		myLeft = null;
		myRight = null;
	}

	public TreeNode(Object x, TreeNode left, TreeNode right) {
		super(x);
		myLeft = left;
		myRight = right;
	}

	public TreeNode(TreeNode left, TreeNode right) {
		super();
		myLeft = left;
		myRight = right;
	}
	
	public MBNode leftChild() {
		return myLeft;
	}

	public MBNode rightChild() {
		return myRight;
	}

	public void setLeft(TreeNode x) {
		myLeft = x;
	}

	public void setRight(TreeNode x) {
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
