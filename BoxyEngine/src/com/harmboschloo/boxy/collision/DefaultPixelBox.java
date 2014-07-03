package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class DefaultPixelBox extends DefaultBox implements PixelBox {
	private final VectorI pixelCount = new VectorI();
	private final VectorF pixelOffsetSize = new VectorF();

	public DefaultPixelBox() {
	}

	public DefaultPixelBox(final VectorI pixelCount,
			final VectorF pixelOffsetSize) {
		this.pixelCount.copy(pixelCount);
		this.pixelOffsetSize.copy(pixelOffsetSize);
	}

	@Override
	public VectorI getPixelCount() {
		return pixelCount;
	}

	@Override
	public VectorF getPixelOffsetSize() {
		return pixelOffsetSize;
	}
}
