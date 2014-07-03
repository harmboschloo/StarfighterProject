package com.harmboschloo.boxy.math;

public class AreaI {
	public final VectorI min = new VectorI();
	public final VectorI max = new VectorI();

	public AreaI() {
	}

	public AreaI(final AreaI area) {
		min.copy(area.min);
		max.copy(area.max);
	}

	public AreaI(final int x, final int y, final int width, final int height) {
		min.set(x, y);
		max.set(x + width, y + height);
	}

	public AreaI(final VectorI min, final VectorI max) {
		this.min.copy(min);
		this.max.copy(max);
	}

	public VectorI getCenter() {
		return new VectorI((max.x - min.x) / 2, (max.y - min.y) / 2);
	}

	public int getHeight() {
		return (max.y - min.y);
	}

	public VectorI getSize() {
		return new VectorI(getWidth(), getHeight());
	}

	public int getWidth() {
		return (max.x - min.x);
	}

	public void includePoint(final VectorI point) {
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

	public void wrap(final VectorI vector) {
		Limit.wrap(vector, min, max);
	}
}
