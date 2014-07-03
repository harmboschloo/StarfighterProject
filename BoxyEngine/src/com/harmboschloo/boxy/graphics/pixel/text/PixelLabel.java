package com.harmboschloo.boxy.graphics.pixel.text;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.Drawer;
import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModel;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModelDrawer;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class PixelLabel implements Drawer {
	private final ColorPixelModelDrawer drawer;
	private double verticalAlignment = 0.5;
	private double horizontalAlignment = 0.5;

	public PixelLabel(final String text, final PixelFont font,
			final CssColor color, final VectorF pixelOffsetSize,
			final float paddingPercentage) {
		final VectorI pixelCount = new VectorI(font.getPixelWidth(text),
				font.getPixelHeight());
		final ColorPixelModel pixels = new ColorPixelModel(pixelCount,
				pixelOffsetSize);
		final CssColorArray colors = pixels.getColors();

		final PixelCharacterIterator iterator = new PixelCharacterIterator(
				text, font);

		while (iterator.hasNext()) {
			final PixelCharacter character = iterator.next();
			if (character != null) {
				final int n = character.getNumberOfPixels();
				for (int i = 0; i < n; ++i) {
					final VectorI pixel = character.getPixelAt(i);
					pixel.x += iterator.getPixelPosition();
					colors.set(pixel, color);
				}
			}
		}

		drawer = new ColorPixelModelDrawer(pixels, paddingPercentage);
	}

	@Override
	public void draw(final Context2d context) {
		final VectorF halfSize = drawer.getPixels().getHalfSize();
		context.save();
		context.translate((1 - 2 * horizontalAlignment) * halfSize.x,
				(1 - 2 * verticalAlignment) * halfSize.y);
		drawer.draw(context);
		context.restore();
	}

	public CssColorArray getColors() {
		return getPixels().getColors();
	}

	public VectorF getHalfSize() {
		return getPixels().getHalfSize();
	}

	public double getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public VectorI getPixelCount() {
		return getPixels().getPixelCount();
	}

	public VectorF getPixelOffsetSize() {
		return getPixels().getPixelOffsetSize();
	}

	public ColorPixelModel getPixels() {
		return drawer.getPixels();
	}

	public double getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setAlignment(final double verticalAlignment,
			final double horizontalAlignment) {
		this.verticalAlignment = verticalAlignment;
		this.horizontalAlignment = horizontalAlignment;
	}

	public void setHorizontalAlignment(final double horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public void setVerticalAlignment(final double verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}
}
