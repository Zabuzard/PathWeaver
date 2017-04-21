package de.zabuza.pathweaver.util;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * An iterator which traverses the elements in reversed order.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 * @param <E>
 *            The class of the objects the iterator contains
 */
public final class ReverseIterator<E> implements Iterator<E> {
	/**
	 * The internal list iterator.
	 */
	private final ListIterator<E> mListIter;

	/**
	 * Creates a new iterator which traverses the elements of the given list in
	 * reversed order.
	 * 
	 * @param list
	 *            The list to traverse
	 */
	public ReverseIterator(final List<E> list) {
		this.mListIter = list.listIterator(list.size());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.mListIter.hasPrevious();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Iterator#next()
	 */
	@Override
	public E next() {
		return this.mListIter.previous();
	}

}
