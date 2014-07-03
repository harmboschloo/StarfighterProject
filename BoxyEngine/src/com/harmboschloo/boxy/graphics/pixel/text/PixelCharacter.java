package com.harmboschloo.boxy.graphics.pixel.text;

import com.harmboschloo.boxy.math.VectorI;

public class PixelCharacter {
	private final int[] pixels;
	private final int pixelWidth;

	public PixelCharacter(final int[] pixels) {
		this.pixels = pixels;
		pixelWidth = pixels[0];
	}

	public int getNumberOfPixels() {
		return (pixels.length - 1);
	}

	private VectorI getPixel(final int index) {
		return new VectorI(index % pixelWidth, index / pixelWidth);
	}

	public VectorI getPixelAt(final int pixelIndex) {
		return getPixel(pixels[pixelIndex + 1]);
	}

	public int getPixelWidth() {
		return pixelWidth;
	}
}
