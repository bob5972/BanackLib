/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <bob5972@banack.net>
 * 
 * BanackLib is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * BanackLib is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with BanackLib.  If not, see <http://www.gnu.org/licenses/>.
 */

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
