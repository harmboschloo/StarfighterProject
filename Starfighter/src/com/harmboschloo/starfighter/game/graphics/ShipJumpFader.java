package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipJumpFader implements HasViewportUpdate {
	private final Ship ship;

	public ShipJumpFader(final Ship ship) {
		this.ship = ship;
	}

	@Override
	public void update(final Viewport viewport) {
		final float progress = ship.getJumpProgress();
		if (progress > 0) {
			final float max = 0.2f;
			final float alpha = 1 - Limit.clamp(
					Math.abs(progress - 0.5f) / max, 0, 1);
			if (alpha > 0) {
				final VectorF center = viewport.getCenter();
				final VectorF halfSize = viewport.getHalfSize();
				final Context2d context = viewport.getContext();
				context.setGlobalAlpha(alpha);
				context.setFillStyle("#000000");
				context.fillRect(center.x - halfSize.x, center.y - halfSize.y,
						halfSize.x * 2, halfSize.y * 2);
				context.setGlobalAlpha(1);
			}
		}
	}
}
