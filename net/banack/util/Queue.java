package net.banack.util;

/**
 * Queue Class with linked List implementation.
 * 
 * @author Michael Banack
 */

public class Queue {
	// Instance Variables
	ListNode myTop;

	ListNode myBottom;

	int mySize;

	public Queue() {
		myTop = null;
		myBottom = null;
		mySize = 0;
	}

	public Object front() {
		if (mySize <= 0)
			throw new EmptyQueueException("front on Empty Queue");
		return myTop.value();
	}

	public boolean isEmpty() {
		return mySize == 0;
	}

	public int size() {
		return mySize;
	}

	public void enqueue(Object elem) {
		if (mySize == 0) {
			myBottom = new ListNode(elem, null);
			myTop = myBottom;
		} else {
			myBottom.setNext(new ListNode(elem, null));
			myBottom = myBottom.getNext();
		}

		mySize++;
	}

	public Object dequeue() {
		if (mySize <= 0)
			throw new EmptyQueueException("dequeue on Empty Stack");
		Object myReturn = myTop.value();
		myTop = myTop.getNext();
		mySize--;
		return myReturn;
	}

	public void makeEmpty() {
		myTop = null;
		mySize = 0;
		myBottom = null;
	}

	public String toString() {
		if (mySize == 0)
			return new String("Queue: Empty");
		String str = new String("Queue: Size="+mySize+" Elements=");
		ListNode current = myTop;
		while (current != myBottom) {
			str += current.value() + ",";
			current = current.getNext();
		}
		str += current.value();
		return str;
	}

	public Object clone() {
		Queue oup = new Queue();
		for (int x = 0; x < mySize; x++) {
			oup.enqueue(front());
			enqueue(dequeue());
		}
		return oup;
	}
}
