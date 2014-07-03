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
import com.harmboschloo.starfighter.game.object.Asteroid;
import com.harmboschloo.starfighter.game.object.Ship;

public class AsteroidShipCollider implements HasGameUpdate {
	protected final BoxContactTester boxTester = new BoxContactTester();
	protected final PixelPointContactTester pixelPointTester = new PixelPointContactTester();

	protected void handleBoxContact(final Asteroid asteroid, final Ship ship,
			final VectorF relativePosition) {
		// only check asteroid pixels against ship points (can miss corner
		// contacts)

		pixelPointTester.initTest(asteroid, ship, relativePosition);
		final PixelStatePointCountTracker tracker = ship.getUndamagedTracker();
		final VectorI point = pixelPointTester.getPoint();
		final VectorI points = pixelPointTester.getPoints();
		for (point.x = 0; point.x < points.x; ++point.x) {
			for (point.y = 0; point.y < points.y; ++point.y) {
				if (tracker.hasCountAroundPoint(point)) {
					if (pixelPointTester.test()) {
						handlePixelContact(asteroid,
								pixelPointTester.getPixel(), ship, point);
					}
				}
			}
		}
	}

	protected void handlePixelContact(final Asteroid asteroid,
			final VectorI pixel, final Ship ship, final VectorI point) {
		if (asteroid.damage(pixel)) {
			final int max = (int) (asteroid.getPixelOffsetSize().x
					/ ship.getPixelOffsetSize().x / 2 + 0.5f);
			ship.damageAroundPoint(point, max);
		}
	}

	@Override
	public void update(final Game game) {
		final AreaF wrapArea = game.getWrapArea();
		final List<Asteroid> asteroids = game.getAsteroids();
		final List<Ship> ships = game.getShips();
		for (final Asteroid asteroid : asteroids) {
			if (asteroid.isEnabled() && !asteroid.isDestroyed()) {
				boxTester.setBox1(asteroid);
				for (final Ship ship : ships) {
					if (ship.isEnabled() && !ship.isDestroyed()) {
						boxTester.setBox2(ship);
						boxTester.updateRelativePositionAndRotation(wrapArea);
						if (boxTester.test()) {
							handleBoxContact(asteroid, ship,
									boxTester.getRelativePosition());
						}
					}
				}
			}
		}
	}
}
