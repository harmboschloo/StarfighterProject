package com.harmboschloo.boxy.math;

public class AreaF {
	public final VectorF min = new VectorF();
	public final VectorF max = new VectorF();

	public AreaF() {
	}

	public AreaF(final AreaF area) {
		copy(area);
	}

	public AreaF(final float x, final float y, final float width,
			final float height) {
		min.set(x, y);
		max.set(x + width, y + height);
	}

	public AreaF(final VectorF min, final VectorF max) {
		this.min.copy(min);
		this.max.copy(max);
	}

	public void copy(final AreaF area) {
		min.copy(area.min);
		max.copy(area.max);
	}

	public VectorF getCenter() {
		return new VectorF(min.x + getWidth() / 2, min.y + getHeight() / 2);
	}

	public float getHeight() {
		return (max.y - min.y);
	}

	public VectorF getSize() {
		return new VectorF(getWidth(), getHeight());
	}

	public float getWidth() {
		return (max.x - min.x);
	}

	public void includePoint(final VectorF point) {
		if (min.x > point.x) {
			min.x = point.x;
		}
		if (min.y > point.y) {
			min.y = point.y;
		}
		if (max.x < point.x) {
			max.x = point.x;
		}
		if (max.y < point.y) {
			max.y = point.y;
		}
	}

	public boolean includesPoint(final VectorF point) {
		return (min.x <= point.x && min.y <= point.y && max.x >= point.x && max.y >= point.y);
	}

	public void setHeight(final float height) {
		max.y = min.y + height;
	}

	public void setSize(final VectorF size) {
		max.copy(min);
		max.add(size);
	}

	public void setWidth(final float width) {
		max.x = min.x + width;
	}

	public void wrap(final VectorF vector) {
		Limit.wrap(vector, min, max);
	}

	// wrap position difference (distance) within +/- half size
	public void wrapRelativePosition(final VectorF relativePosition) {
		final VectorF max = new VectorF(getWidth() / 2, getHeight() / 2);
		final VectorF min = max.times(-1);
		Limit.wrap(relativePosition, min, max);
	}
}
