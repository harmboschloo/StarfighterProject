package com.harmboschloo.boxy.util;

import com.harmboschloo.boxy.math.VectorI;

public class XYArrayI {
	private final VectorI size = new VectorI();
	private final int[] array;

	public XYArrayI(final VectorI size) {
		this.size.copy(size);
		array = new int[size.x * size.y];
	}

	public void add(final int index, final int value) {
		array[index] += value;
	}

	public void add(final int x, final int y, final int value) {
		assertXY(x, y);
		add(getIndex(x, y), value);
	}

	public void add(final VectorI position, final int value) {
		add(position.x, position.y, value);
	}

	private void assertXY(final int x, final int y) {
		assert (x >= 0 && x < size.x && y >= 0 && y < size.y) : "Out of bounds ("
				+ x + "," + y + "); " + size;
	}

	public int get(final int index) {
		return array[index];
	}

	public int get(final int x, final int y) {
		assertXY(x, y);
		return get(getIndex(x, y));
	}

	public int get(final VectorI position) {
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

	public void set(final int index, final int value) {
		array[index] = value;
	}

	public void set(final int x, final int y, final int value) {
		assertXY(x, y);
		set(getIndex(x, y), value);
	}

	public void set(final VectorI position, final int value) {
		set(position.x, position.y, value);
	}

	public void setAll(final int value) {
		final int n = array.length;
		for (int i = 0; i < n; ++i) {
			set(i, value);
		}
	}
}
