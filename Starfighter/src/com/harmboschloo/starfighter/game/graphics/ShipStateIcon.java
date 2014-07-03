package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.PixelState;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipStateIcon implements HasViewportUpdate {
	private final Ship ship;

	public ShipStateIcon(final Ship ship) {
		this.ship = ship;
	}

	public void drawPixel(final Context2d context, final CssColor color,
			final double x, final double y, final double width,
			final double height, final double lineWidth, final VectorI pixel) {
		switch (ship.getStates().get(pixel)) {
		case PixelState.UNDAMAGED:
			context.setFillStyle(color);
			context.fillRect(x, y, width, height);
			break;
		case PixelState.DAMAGED:
			context.setStrokeStyle(color);
			context.setLineWidth(lineWidth);
			context.strokeRect(x + lineWidth / 2, y + lineWidth / 2, width
					- lineWidth, height - lineWidth);
			break;
		default:
			break;
		}
	}

	@Override
	public void update(final Viewport viewport) {

		final VectorI pixelCount = ship.getPixels().getPixelCount();
		final VectorF pixelOffsetSize = ship.getPixelData()
				.getPixelOffsetSize();
		final VectorF pixelPadding = pixelOffsetSize
				.times(Config.PIXEL_PADDING_FRACTION);
		final VectorF pixelSize = new VectorF(pixelPadding);
		pixelSize.multiplyBy(-2);
		pixelSize.add(pixelOffsetSize);
		final VectorF halfSize = new VectorF(pixelCount);
		halfSize.multiplyBy(pixelOffsetSize);
		halfSize.divideBy(2);
		final CssColorArray colors = ship.getPixels().getColors();
		final double lineWidth = ship.getPixelData().getPixelOffsetSize().x / 4;

		final VectorF vpHalfSize = viewport.getHalfSize();
		final VectorF position = new VectorF(viewport.getCenter());
		final float size = Math.min(vpHalfSize.x, vpHalfSize.y);
		final float margin = size / 50;
		final float scale = size / 90;
		position.x += vpHalfSize.x - (halfSize.y + margin) * scale;
		position.y -= vpHalfSize.y - (halfSize.x + margin) * scale;

		final Context2d context = viewport.getContext();
		context.save();
		context.translate(position.x, position.y);
		context.rotate(-Math.PI / 2);
		context.scale(scale, scale);

		context.translate(-halfSize.x, -halfSize.y);

		final VectorI pixel = new VectorI();
		for (pixel.x = 0; pixel.x < pixelCount.x; ++pixel.x) {
			for (pixel.y = 0; pixel.y < pixelCount.y; ++pixel.y) {
				final CssColor color = colors.get(pixel);
				if (color != null) {
					drawPixel(context, color, pixel.x * pixelOffsetSize.x
							+ pixelPadding.x, pixel.y * pixelOffsetSize.y
							+ pixelPadding.y, pixelSize.x, pixelSize.y,
							lineWidth, pixel);
				}
			}
		}

		context.restore();
	}
}
