package com.harmboschloo.boxy.math;

public class VectorF {
	public static VectorF parseVector(final String value) {
		if (value != null) {
			final String[] xy = value.split("x");
			if (xy != null && xy.length == 2) {
				final VectorF vector = new VectorF();
				vector.x = Float.parseFloat(xy[0]);
				vector.y = Float.parseFloat(xy[1]);
				return vector;
			}
		}

		throw new NumberFormatException();
	}

	public float x;

	public float y;

	public VectorF() {
		x = 0;
		y = 0;
	}

	public VectorF(final float xy) {
		set(xy);
	}

	public VectorF(final float x, final float y) {
		set(x, y);
	}

	public VectorF(final VectorF other) {
		copy(other);
	}

	public VectorF(final VectorI other) {
		x = other.x;
		y = other.y;
	}

	public void add(final float scalar) {
		x += scalar;
		y += scalar;
	}

	public void add(final VectorF other) {
		x += other.x;
		y += other.y;
	}

	public void copy(final VectorF other) {
		x = other.x;
		y = other.y;
	}

	public void copy(final VectorI other) {
		x = other.x;
		y = other.y;
	}

	public VectorF divide(final float scalar) {
		return new VectorF(x / scalar, y / scalar);
	}

	public VectorF divide(final VectorF other) {
		return new VectorF(x / other.x, y / other.y);
	}

	public void divideBy(final float scalar) {
		x /= scalar;
		y /= scalar;
	}

	public void divideBy(final VectorF other) {
		x /= other.x;
		y /= other.y;
	}

	public float dot(final VectorF other) {
		return (x * other.x + y * other.y);
	}

	public boolean equals(final float x, final float y) {
		return (this.x == x && this.y == y);
	}

	public boolean equals(final VectorF other) {
		return (x == other.x && y == other.y);
	}

	public float getAngle() {
		return (float) Math.atan2(y, x);
	}

	public float getArea() {
		return (x * y);
	}

	public float getDistance(final VectorF other) {
		return (float) Math.sqrt(getDistanceSquared(other));
	}

	public float getDistanceSquared(final VectorF other) {
		return other.minus(this).getLengthSquared();
	}

	public float getLength() {
		return (float) Math.sqrt(getLengthSquared());
	}

	public float getLengthSquared() {
		return (x * x + y * y);
	}

	public VectorF getNormalized() {
		final VectorF vector = new VectorF(this);
		vector.normalize();
		return vector;
	}

	public VectorF getPerpendicular() {
		return new VectorF(y, -x);
	}

	public VectorF minus(final float scalar) {
		return new VectorF(x - scalar, y - scalar);
	}

	public VectorF minus(final VectorF other) {
		return new VectorF(x - other.x, y - other.y);
	}

	public void multiplyBy(final float scalar) {
		x *= scalar;
		y *= scalar;
	}

	public void multiplyBy(final VectorF other) {
		x *= other.x;
		y *= other.y;
	}

	// TODO: add tolerance?
	public void normalize() {
		final float length = getLength();
		if (length > 0) {
			x /= length;
			y /= length;
		}
	}

	public VectorF opposite() {
		return new VectorF(-x, -y);
	}

	public VectorF plus(final float scalar) {
		return new VectorF(x + scalar, y + scalar);
	}

	public VectorF plus(final VectorF other) {
		return new VectorF(x + other.x, y + other.y);
	}

	public void set(final float xy) {
		x = xy;
		y = xy;
	}

	public void set(final float x, final float y) {
		this.x = x;
		this.y = y;
	}

	public void setPerpendicular() {
		set(y, -x);
	}

	public void substract(final float scalar) {
		x -= scalar;
		y -= scalar;
	}

	public void substract(final VectorF other) {
		x -= other.x;
		y -= other.y;
	}

	public VectorF times(final float scalar) {
		return new VectorF(x * scalar, y * scalar);
	}

	public VectorF times(final VectorF other) {
		return new VectorF(x * other.x, y * other.y);
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
}
