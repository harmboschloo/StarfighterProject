package com.harmboschloo.boxy.util;

/**
 * Based on Bresenham's_line_algorithm
 * (http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm). Modified to
 * preserve correct direction.
 */
public class LineIteratorXY {
	private final boolean steep;
	private final int x1;
	private final int dxAbs;
	private final int dyAbs;
	private final int xStep;
	private final int yStep;
	private int x;
	private int y;
	private int error;

	public LineIteratorXY(int x0, int y0, int x1, int y1) {
		steep = (Math.abs(y1 - y0) > Math.abs(x1 - x0));
		if (steep) {
			int tmp = x0;
			x0 = y0;
			y0 = tmp;
			tmp = x1;
			x1 = y1;
			y1 = tmp;
		}
		x = x0;
		y = y0;
		this.x1 = x1;
		dxAbs = Math.abs(x1 - x0);
		dyAbs = Math.abs(y1 - y0);
		xStep = (x0 < x1 ? 1 : -1);
		yStep = (y0 < y1 ? 1 : -1);
		error = dxAbs / 2;
	}

	public int getX() {
		return (steep ? y : x);
	}

	public int getY() {
		return (steep ? x : y);
	}

	boolean hasNext() {
		return (x != x1);
	}

	public boolean next() {
		if (hasNext()) {
			x += xStep;
			error -= dyAbs;
			if (error < 0) {
				y += yStep;
				error += dxAbs;
			}
			return true;
		}
		return false;
	}
}
