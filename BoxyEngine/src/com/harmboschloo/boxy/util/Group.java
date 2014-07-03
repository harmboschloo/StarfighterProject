package com.harmboschloo.boxy.util;

import java.util.ArrayList;
import java.util.List;

public class Group<E> {
	private List<E> elements;

	public Group() {
		setElements(new ArrayList<E>());
	}

	public Group(final List<E> elements) {
		setElements(elements);
	}

	public void add(final E element) {
		elements.add(element);
	}

	public boolean addAll(final List<? extends E> c) {
		return elements.addAll(c);
	}

	public void clear() {
		elements.clear();
	}

	public List<E> getElements() {
		return elements;
	}

	public boolean remove(final E element) {
		return elements.remove(element);
	}

	public void setElements(final List<E> elements) {
		this.elements = elements;
	}
}
