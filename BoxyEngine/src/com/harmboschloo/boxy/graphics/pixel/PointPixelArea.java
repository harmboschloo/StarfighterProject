package com.harmboschloo.boxy.graphics.pixel;

import com.harmboschloo.boxy.math.AreaI;
import com.harmboschloo.boxy.math.VectorI;

class PointPixelArea extends AreaI {
	public PointPixelArea(final VectorI point, final VectorI pixelSize) {
		super(point, point);
		if (min.x > 0) {
			min.x -= 1;
		}
		if (min.y > 0) {
			min.y -= 1;
		}

		if (max.x >= pixelSize.x) {
			max.x = pixelSize.x - 1;
		}
		if (max.y >= pixelSize.y) {
			max.y = pixelSize.y - 1;
		}
	}
}
