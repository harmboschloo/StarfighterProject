package com.harmboschloo.boxy.math;

public class VectorI {
	public static VectorI parseVector(final String value) {
		if (value != null) {
			final String[] xy = value.split("x");
			if (xy != null && xy.length == 2) {
				final VectorI vector = new VectorI();
				vector.x = Integer.parseInt(xy[0]);
				vector.y = Integer.parseInt(xy[1]);
				return vector;
			}
		}

		throw new NumberFormatException();
	}

	public int x;

	public int y;

	public VectorI() {
		x = 0;
		y = 0;
	}

	public VectorI(final int xy) {
		set(xy);
	}

	public VectorI(final int x, final int y) {
		set(x, y);
	}

	public VectorI(final VectorI other) {
		copy(other);
	}

	public void add(final int scalar) {
		x += scalar;
		y += scalar;
	}

	public void add(final VectorI other) {
		x += other.x;
		y += other.y;
	}

	public void copy(final VectorI other) {
		x = other.x;
		y = other.y;
	}

	public VectorI divide(final int scalar) {
		return new VectorI(x / scalar, y / scalar);
	}

	public VectorI divide(final VectorI other) {
		return new VectorI(x / other.x, y / other.y);
	}

	public void divideBy(final int scalar) {
		x /= scalar;
		y /= scalar;
	}

	public void divideBy(final VectorI other) {
		x /= other.x;
		y /= other.y;
	}

	public boolean equals(final int x, final int y) {
		return (this.x == x && this.y == y);
	}

	public boolean equals(final VectorI other) {
		return (x == other.x && y == other.y);
	}

	public int getArea() {
		return (x * y);
	}

	public VectorI minus(final int scalar) {
		return new VectorI(x - scalar, y - scalar);
	}

	public VectorI minus(final VectorI other) {
		return new VectorI(x - other.x, y - other.y);
	}

	public void multiplyBy(final int scalar) {
		x *= scalar;
		y *= scalar;
	}

	public void multiplyBy(final VectorI other) {
		x *= other.x;
		y *= other.y;
	}

	public VectorI opposite() {
		return new VectorI(-x, -y);
	}

	public VectorI plus(final int scalar) {
		return new VectorI(x + scalar, y + scalar);
	}

	public VectorI plus(final VectorI other) {
		return new VectorI(x + other.x, y + other.y);
	}

	public void set(final int xy) {
		x = xy;
		y = xy;
	}

	public void set(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public void substract(final int scalar) {
		x -= scalar;
		y -= scalar;
	}

	public void substract(final VectorI other) {
		x -= other.x;
		y -= other.y;
	}

	public VectorI times(final int scalar) {
		return new VectorI(x * scalar, y * scalar);
	}

	public VectorI times(final VectorI other) {
		return new VectorI(x * other.x, y * other.y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
