package com.harmboschloo.starfighter.game.graphics;

import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.util.HasUpdate;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipViewportCenterController implements HasUpdate {
	private final Ship ship;
	private final Viewport viewport;
	private final AreaF wrapArea;

	public ShipViewportCenterController(final Ship ship,
			final Viewport viewport, final AreaF wrapArea) {
		this.ship = ship;
		this.viewport = viewport;
		this.wrapArea = wrapArea;
	}

	@Override
	public void update() {
		final float jumpProgress = ship.getJumpProgress();
		if (jumpProgress >= 0.5f) {
			final VectorF diff = ship.getMassCenter().minus(
					viewport.getCenter());
			wrapArea.wrapRelativePosition(diff);
			diff.multiplyBy(jumpProgress * 1.5f - 0.5f);
			viewport.getCenter().add(diff);
			wrapArea.wrap(viewport.getCenter());
		} else {
			viewport.getCenter().copy(ship.getMassCenter());
		}
	}
}
