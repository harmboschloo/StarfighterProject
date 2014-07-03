package com.harmboschloo.starfighter.game.collision;

import java.util.List;

import com.harmboschloo.boxy.collision.BoxContactTester;
import com.harmboschloo.boxy.collision.PixelPointContactTester;
import com.harmboschloo.boxy.graphics.pixel.PixelStatePointCountTracker;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipShipCollider implements HasGameUpdate {
	private final BoxContactTester boxTester = new BoxContactTester();
	private final PixelPointContactTester pixelPointTester = new PixelPointContactTester();

	private void handleBoxContact(final Ship ship1, final Ship ship2,
			final VectorF relativePosition) {
		// test pixels/points both ways
		testPixels(ship1, ship2, boxTester.getRelativePosition());
		testPixels(ship2, ship1, relativePosition.times(-1));
	}

	private void handlePixelContact(final Ship ship1, final VectorI pixel,
			final Ship ship2, final VectorI point) {
		// damage one for one
		if (ship1.damage(pixel)) {
			ship2.damageAroundPoint(point, 1);
		}
	}

	private void testPixels(final Ship ship1, final Ship ship2,
			final VectorF relativePosition) {
		pixelPointTester.initTest(ship1, ship2, relativePosition);
		final PixelStatePointCountTracker tracker = ship2.getUndamagedTracker();
		final VectorI point = pixelPointTester.getPoint();
		final VectorI points = pixelPointTester.getPoints();
		for (point.x = 0; point.x < points.x; ++point.x) {
			for (point.y = 0; point.y < points.y; ++point.y) {
				if (tracker.hasCountAroundPoint(point)) {
					if (pixelPointTester.test()) {
						handlePixelContact(ship1, pixelPointTester.getPixel(),
								ship2, point);
					}
				}
			}
		}
	}

	@Override
	public void update(final Game game) {
		final AreaF wrapArea = game.getWrapArea();
		final List<Ship> ships = game.getShips();
		int i1 = 0;
		final int n = ships.size();
		for (final Ship ship1 : ships) {
			if (ship1.isEnabled() && !ship1.isDestroyed()) {
				boxTester.setBox1(ship1);
				for (int i2 = (i1 + 1); i2 < n; ++i2) {
					final Ship ship2 = ships.get(i2);
					if (ship2.isEnabled() && !ship2.isDestroyed()) {
						boxTester.setBox2(ship2);
						boxTester.updateRelativePositionAndRotation(wrapArea);
						if (boxTester.test()) {
							handleBoxContact(ship1, ship2,
									boxTester.getRelativePosition());
						}
					}
				}
			}
			++i1;
		}
	}
}
