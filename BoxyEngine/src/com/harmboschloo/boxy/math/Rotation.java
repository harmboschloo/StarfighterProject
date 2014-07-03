package com.harmboschloo.boxy.math;

public class Rotation {
	private float angle;
	private final VectorF col1 = new VectorF();
	private final VectorF col2 = new VectorF();

	public Rotation() {
		reset();
	}

	public Rotation(final float angle) {
		setAngle(angle);
	}

	public Rotation(final Rotation other) {
		copy(other);
	}

	public void addAngle(final float angle) {
		setAngle(this.angle + angle);
	}

	public void copy(final Rotation other) {
		angle = other.angle;
		col1.copy(other.col1);
		col2.copy(other.col2);
	}

	public float getAngle() {
		return angle;
	}

	public void reset() {
		angle = 0;
		col1.set(1, 0);
		col2.set(0, 1);
	}

	// is transpose
	public void reverse() {
		angle = -angle;
		col1.y = -col1.y;
		col2.x = -col2.x;
	}

	public void rotate(final VectorF vector) {
		vector.set(vector.x * col1.x + vector.y * col2.x, vector.x * col1.y
				+ vector.y * col2.y);
	}

	public VectorF rotateNew(final VectorF vector) {
		return new VectorF(vector.x * col1.x + vector.y * col2.x, vector.x
				* col1.y + vector.y * col2.y);
	}

	public void rotateReverse(final VectorF vector) {
		vector.set(vector.x * col1.x + vector.y * -col2.x, vector.x * -col1.y
				+ vector.y * col2.y);
	}

	public VectorF rotateReverseNew(final VectorF vector) {
		return new VectorF(vector.x * col1.x + vector.y * -col2.x, vector.x
				* -col1.y + vector.y * col2.y);
	}

	public void setAngle(final float angle) {
		if (angle != 0) {
			this.angle = angle;
			col1.set((float) Math.cos(angle), (float) Math.sin(angle));
			col2.set(-col1.y, col1.x);
		} else {
			reset();
		}
	}
}
