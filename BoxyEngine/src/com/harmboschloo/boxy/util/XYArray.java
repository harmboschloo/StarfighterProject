package com.harmboschloo.boxy.util;

import com.harmboschloo.boxy.math.VectorI;

public class XYArray<E> {
	private final VectorI size = new VectorI();
	private final E[] array;

	protected XYArray(final VectorI size, final E[] array) {
		this.size.copy(size);
		this.array = array;
		assert (array.length == size.x * size.y);
	}

	private void assertXY(final int x, final int y) {
		assert (x >= 0 && x < size.x && y >= 0 && y < size.y) : "Out of bounds ("
				+ x + "," + y + "); " + size;
	}

	public E get(final int index) {
		return array[index];
	}

	public E get(final int x, final int y) {
		assertXY(x, y);
		return get(getIndex(x, y));
	}

	public E get(final VectorI position) {
		return get(position.x, position.y);
	}

	public int getCount() {
		return array.length;
	}

	public int getIndex(final int x, final int y) {
		return (y * size.x + x);
	}

	public int getIndex(final VectorI position) {
		return getIndex(position.x, position.y);
	}

	public VectorI getPosition(final int index) {
		return new VectorI(getX(index), getY(index));
	}

	public VectorI getSize() {
		return size;
	}

	public int getX(final int index) {
		return (index % size.x);
	}

	public int getY(final int index) {
		return (index / size.x);
	}

	public void set(final int index, final E element) {
		array[index] = element;
	}

	public void set(final int x, final int y, final E element) {
		assertXY(x, y);
		set(getIndex(x, y), element);
	}

	public void set(final VectorI position, final E element) {
		set(position.x, position.y, element);
	}

	public void setAll(final E element) {
		final int n = getCount();
		for (int i = 0; i < n; ++i) {
			set(i, element);
		}
	}
}
