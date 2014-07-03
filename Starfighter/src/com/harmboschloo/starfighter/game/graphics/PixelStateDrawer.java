package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModel;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModelDrawer;
import com.harmboschloo.boxy.graphics.pixel.PixelStateModel;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.starfighter.game.PixelState;

public class PixelStateDrawer extends ColorPixelModelDrawer {
	private final PixelStateModel states;
	private final float lineWidthPercentage;
	private float lineWidth;

	public PixelStateDrawer(final ColorPixelModel pixels,
			final PixelStateModel states, final float paddingPercentage,
			final float lineWidthPercentage) {
		super(pixels, paddingPercentage);
		this.states = states;
		this.lineWidthPercentage = lineWidthPercentage;
	}

	@Override
	public void draw(final Context2d context) {
		final VectorF pixelOffsetSize = getPixels().getPixelOffsetSize();
		lineWidth = lineWidthPercentage
				* (pixelOffsetSize.x + pixelOffsetSize.y) / 2;
		super.draw(context);
	}

	public void drawDamaged(final Context2d context, final CssColor color,
			final double x, final double y, final double width,
			final double height) {
		context.setStrokeStyle(color);
		context.setLineWidth(lineWidth);
		context.strokeRect(x + lineWidth / 2, y + lineWidth / 2, width
				- lineWidth, height - lineWidth);
	}

	@Override
	public void drawPixel(final Context2d context, final CssColor color,
			final double x, final double y, final double width,
			final double height, final VectorI pixel) {
		switch (states.get(pixel)) {
		case PixelState.UNDAMAGED:
			drawUndamaged(context, color, x, y, width, height);
			break;
		case PixelState.DAMAGED:
			drawDamaged(context, color, x, y, width, height);
			break;
		case PixelState.THRUSTER:
			drawThruster(context, color, x, y, width, height);
			break;
		default:
			break;
		}
	}

	public void drawThruster(final Context2d context, final CssColor color,
			final double x, final double y, final double width,
			final double height) {
		context.setFillStyle(color);
		context.setGlobalAlpha(0.8);
		context.fillRect(x, y, width, height);
		context.setGlobalAlpha(1);
	}

	public void drawUndamaged(final Context2d context, final CssColor color,
			final double x, final double y, final double width,
			final double height) {
		context.setFillStyle(color);
		context.fillRect(x, y, width, height);
	}
}
