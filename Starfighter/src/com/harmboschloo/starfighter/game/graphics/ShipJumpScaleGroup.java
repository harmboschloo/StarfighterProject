package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.graphics.viewport.ViewportUpdateGroup;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipJumpScaleGroup extends ViewportUpdateGroup {
	private final Ship ship;

	public ShipJumpScaleGroup(final Ship ship) {
		this.ship = ship;
	}

	@Override
	public void update(final Viewport viewport) {
		if (ship.getJumpProgress() == 0) {
			super.update(viewport);
			return;
		}

		final VectorF jumpScale = ship.getJumpScale();
		final VectorF scale = new VectorF(1, 1);
		scale.divideBy(jumpScale);
		final VectorF center = viewport.getCenter();
		final Context2d context = viewport.getContext();

		context.save();

		context.translate(center.x, center.y);
		context.scale(scale.x, scale.y);
		context.translate(-center.x, -center.y);

		super.update(viewport);

		context.restore();
	}
}
