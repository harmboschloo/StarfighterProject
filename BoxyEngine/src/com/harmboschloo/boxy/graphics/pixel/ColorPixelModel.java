package com.harmboschloo.boxy.graphics.pixel;

import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class ColorPixelModel {
	private final CssColorArray colors;
	private final VectorF halfSize = new VectorF();
	private final VectorF pixelOffsetSize = new VectorF();

	public ColorPixelModel(final CssColorArray colors,
			final VectorF pixelOffsetSize) {
		this.colors = colors;
		updatePixelOffsetSize(pixelOffsetSize);
	}

	public ColorPixelModel(final VectorI pixelCount,
			final VectorF pixelOffsetSize) {
		this(new CssColorArray(pixelCount), pixelOffsetSize);
	}

	public CssColorArray getColors() {
		return colors;
	}

	public VectorF getHalfSize() {
		return halfSize;
	}

	public VectorI getPixelCount() {
		return colors.getSize();
	}

	public VectorF getPixelOffsetSize() {
		return pixelOffsetSize;
	}

	public void updatePixelOffsetSize(final VectorF pixelOffsetSize) {
		this.pixelOffsetSize.copy(pixelOffsetSize);
		halfSize.copy(getPixelCount());
		halfSize.multiplyBy(pixelOffsetSize);
		halfSize.multiplyBy(0.5f);
	}
}
