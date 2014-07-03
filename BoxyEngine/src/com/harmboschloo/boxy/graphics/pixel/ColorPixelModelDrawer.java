package com.harmboschloo.boxy.graphics.pixel;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.Drawer;
import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class ColorPixelModelDrawer implements Drawer {
	private final ColorPixelModel pixels;
	private final float paddingPercentage;

	public ColorPixelModelDrawer(final ColorPixelModel pixels) {
		this(pixels, 0);
	}

	public ColorPixelModelDrawer(final ColorPixelModel pixels,
			final float paddingPercentage) {
		this.pixels = pixels;
		this.paddingPercentage = paddingPercentage;
	}

	@Override
	public void draw(final Context2d context) {
		final VectorI pixelCount = pixels.getPixelCount();
		final VectorF pixelOffsetSize = pixels.getPixelOffsetSize();
		final VectorF pixelPadding = pixelOffsetSize.times(paddingPercentage);
		final VectorF pixelSize = new VectorF(pixelPadding);
		pixelSize.multiplyBy(-2);
		pixelSize.add(pixelOffsetSize);
		final VectorF halfSize = pixels.getHalfSize();
		final CssColorArray colors = pixels.getColors();

		context.translate(-halfSize.x, -halfSize.y);

		final VectorI pixel = new VectorI();
		for (pixel.x = 0; pixel.x < pixelCount.x; ++pixel.x) {
			for (pixel.y = 0; pixel.y < pixelCount.y; ++pixel.y) {
				final CssColor color = colors.get(pixel);
				if (color != null) {
					drawPixel(context, color, pixel.x * pixelOffsetSize.x
							+ pixelPadding.x, pixel.y * pixelOffsetSize.y
							+ pixelPadding.y, pixelSize.x, pixelSize.y, pixel);
				}
			}
		}

		context.translate(halfSize.x, halfSize.y);
	}

	public void drawPixel(final Context2d context, final CssColor color,
			final double x, final double y, final double width,
			final double height, final VectorI pixel) {
		context.setFillStyle(color);
		context.fillRect(x, y, width, height);
	}

	public ColorPixelModel getPixels() {
		return pixels;
	}
}
