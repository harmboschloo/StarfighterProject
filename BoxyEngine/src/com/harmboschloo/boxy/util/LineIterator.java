package com.harmboschloo.boxy.util;

import com.harmboschloo.boxy.math.VectorI;

/**
 * Based on Bresenham's_line_algorithm
 * (http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm). Modified to
 * preserve correct direction.
 */
public class LineIterator {
	private final VectorI current = new VectorI();
	private final VectorI dAbs = new VectorI();
	private final VectorI stepChange = new VectorI();
	private boolean steep;
	private int error;
	private int steps;
	private int step;

	public LineIterator(final VectorI start, final VectorI end) {
		reset(start, end);
	}

	public VectorI current() {
		return current;
	}

	boolean hasNext() {
		return (step < steps);
	}

	public VectorI next() {
		if (hasNext()) {
			if (steep) {
				stepY();
			} else {
				stepX();
			}
			++step;
			return current;
		}
		return null;
	}

	public void reset(final VectorI start, final VectorI end) {
		current.copy(start);
		dAbs.x = Math.abs(end.x - start.x);
		dAbs.y = Math.abs(end.y - start.y);
		stepChange.x = (start.x < end.x ? 1 : -1);
		stepChange.y = (start.y < end.y ? 1 : -1);
		steep = (dAbs.y > dAbs.x);
		steps = (steep ? dAbs.y : dAbs.x);
		error = steps / 2;
		step = 1;
	}

	private void stepX() {
		current.x += stepChange.x;
		error -= dAbs.y;
		if (error < 0) {
			current.y += stepChange.y;
			error += dAbs.x;
		}
	}

	private void stepY() {
		current.y += stepChange.y;
		error -= dAbs.x;
		if (error < 0) {
			current.x += stepChange.x;
			error += dAbs.y;
		}
	}
}
