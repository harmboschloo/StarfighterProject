package com.harmboschloo.boxy.util;

import java.util.List;

public class UnknownGroup<E> {
	private List<? extends E> elements;

	public UnknownGroup(final List<? extends E> elements) {
		setElements(elements);
	}

	public List<? extends E> getElements() {
		return elements;
	}

	public void setElements(final List<? extends E> elements) {
		this.elements = elements;
	}
}
