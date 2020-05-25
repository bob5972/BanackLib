/*
 * This file is part of BanackLib.
 * Copyright (c)2009 Michael Banack <github@banack.net>
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