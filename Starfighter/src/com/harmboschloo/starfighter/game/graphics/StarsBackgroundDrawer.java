package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.Random;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.ui.CanvasNotSupportedException;

// Test draw list instead of image
public class StarsBackgroundDrawer implements HasViewportUpdate {
	private final Canvas stars = Canvas.createIfSupported();

	public StarsBackgroundDrawer(final VectorF size) {
		if (stars == null) {
			throw new CanvasNotSupportedException();
		}

		initStars(size);
	}

	private void initStars(final VectorF size) {
		stars.setCoordinateSpaceWidth((int) (size.x));
		stars.setCoordinateSpaceHeight((int) (size.y));

		final int starSizeMax = 3;
		final int starSizeMin = 1;

		stars.getContext2d().setFillStyle(CssColor.make(100, 50, 100));
		final int n = (int) (size.x * size.y / 500);
		for (int i = 0; i < n; ++i) {
			final float x = Random.range(starSizeMax, size.x - starSizeMax);
			final float y = Random.range(starSizeMax, size.y - starSizeMax);
			final int starSize = Random.range(starSizeMin, starSizeMax);
			stars.getContext2d().fillRect(x, y, starSize, starSize);
		}
	}

	@Override
	public void update(final Viewport viewport) {
		final VectorF center = viewport.getCenter();
		viewport.getContext().drawImage(stars.getCanvasElement(),
				center.x - stars.getCoordinateSpaceWidth() / 2,
				center.y - stars.getCoordinateSpaceHeight() / 2);
	}
}
