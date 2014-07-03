package com.harmboschloo.boxy.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CompositeGroup<E> extends Group<Iterable<E>> implements
		Iterable<E> {
	class CompositeIterator implements Iterator<E> {
		private final Iterator<Iterable<E>> groupIterator;
		private Iterator<E> elementIterator = null;

		CompositeIterator() {
			groupIterator = getElements().iterator();
			findNext();
		}

		private void findNext() {
			while (groupIterator.hasNext()) {
				elementIterator = groupIterator.next().iterator();
				if (elementIterator.hasNext()) {
					break;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return (elementIterator != null ? elementIterator.hasNext() : false);
		}

		@Override
		public E next() {
			if (elementIterator == null) {
				throw new NoSuchElementException();
			}

			final E element = elementIterator.next();

			if (!elementIterator.hasNext()) {
				findNext();
			}

			return element;
		}

		@Override
		public void remove() {
			if (elementIterator == null) {
				throw new IllegalStateException();
			}
			elementIterator.remove();
		}
	}

	@Override
	public Iterator<E> iterator() {
		return new CompositeIterator();
	}
}
