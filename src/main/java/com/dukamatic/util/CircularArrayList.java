package com.dukamatic.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

// TODO: Auto-generated Javadoc
/**
 * If you use this code, please retain this comment block.
 *
 * @param <E> the element type
 * @author Isak du Preez isak at du-preez dot com www.du-preez.com
 */
public class CircularArrayList<E> extends AbstractList<E> implements RandomAccess {

	/** The n. */
	private final int n; // buffer length
	
	/** The buf. */
	private final List<E> buf; // a List implementing RandomAccess
	
	/** The head. */
	private int head = 0;
	
	/** The tail. */
	private int tail = 0;

	/**
	 * Instantiates a new circular array list.
	 *
	 * @param capacity the capacity
	 */
	public CircularArrayList(int capacity) {
		n = capacity + 1;
		buf = new ArrayList<E>(Collections.nCopies(n, (E) null));
	}

	/**
	 * Capacity.
	 *
	 * @return the int
	 */
	public int capacity() {
		return n - 1;
	}

	/**
	 * Wrap index.
	 *
	 * @param i the i
	 * @return the int
	 */
	private int wrapIndex(int i) {
		int m = i % n;
		if (m < 0) { // java modulus can be negative
			m += n;
		}
		return m;
	}

	// This method is O(n) but will never be called if the
	// CircularArrayList is used in its typical/intended role.
	/**
	 * Shift block.
	 *
	 * @param startIndex the start index
	 * @param endIndex the end index
	 */
	private void shiftBlock(int startIndex, int endIndex) {
		assert (endIndex > startIndex);
		for (int i = endIndex - 1; i >= startIndex; i--) {
			set(i + 1, get(i));
		}
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractCollection#size()
	 */
	@Override
	public int size() {
		return tail - head + (tail < head ? n : 0);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractList#get(int)
	 */
	@Override
	public E get(int i) {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return buf.get(wrapIndex(head + i));
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractList#set(int, java.lang.Object)
	 */
	@Override
	public E set(int i, E e) {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return buf.set(wrapIndex(head + i), e);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractList#add(int, java.lang.Object)
	 */
	@Override
	public void add(int i, E e) {
		int s = size();
		if (s == n - 1) {
			throw new IllegalStateException("Cannot add element." + " CircularArrayList is filled to capacity.");
		}
		if (i < 0 || i > s) {
			throw new IndexOutOfBoundsException();
		}
		tail = wrapIndex(tail + 1);
		if (i < s) {
			shiftBlock(i, s);
		}
		set(i, e);
	}

	/* (non-Javadoc)
	 * @see java.util.AbstractList#remove(int)
	 */
	@Override
	public E remove(int i) {
		int s = size();
		if (i < 0 || i >= s) {
			throw new IndexOutOfBoundsException();
		}
		E e = get(i);
		if (i > 0) {
			shiftBlock(0, i);
		}
		head = wrapIndex(head + 1);
		return e;
	}
}