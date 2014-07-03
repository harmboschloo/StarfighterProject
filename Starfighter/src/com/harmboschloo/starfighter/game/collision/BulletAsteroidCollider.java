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
import com.harmboschloo.starfighter.game.object.Bullet;
import com.harmboschloo.starfighter.game.object.Ship;

public class BulletAsteroidCollider implements HasGameUpdate {
	private final BoxContactTester boxTester = new BoxContactTester();
	private final PixelPointContactTester pixelPointTester = new PixelPointContactTester();

	private void testBoxes(final Iterable<Bullet> bullets,
			final Asteroid asteroid, final AreaF wrapArea, final Game game) {
		boxTester.setBox1(asteroid);
		for (final Bullet bullet : bullets) {
			if (bullet.isActive()) {
				boxTester.setBox2(bullet);
				boxTester.updateRelativePositionAndRotation(wrapArea);
				if (boxTester.test()) {
					testPixels(asteroid, bullet,
							boxTester.getRelativePosition(), game);
				}
			}
		}
	}

	private void testPixels(final Asteroid asteroid, final Bullet bullet,
			final VectorF relativePosition, final Game game) {
		pixelPointTester.initTest(asteroid, bullet, relativePosition);
		final PixelStatePointCountTracker tracker = bullet
				.getUndamagedTracker();
		final VectorI point = pixelPointTester.getPoint();
		final VectorI points = pixelPointTester.getPoints();
		for (point.x = points.x - 1; point.x >= 0; --point.x) {
			for (point.y = points.y - 1; point.y >= 0; --point.y) {
				if (tracker.hasCountAroundPoint(point)) {
					if (pixelPointTester.test()) {
						if (asteroid.damage(pixelPointTester.getPixel())) {
							bullet.damageAroundPoint(point, 4);
							game.addExplosion(pixelPointTester
									.calculateAbsolutePointPosition(), bullet,
									asteroid);
							if (bullet.isDestroyed()) {
								return;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void update(final Game game) {
		final AreaF wrapArea = game.getWrapArea();
		final List<Asteroid> asteroids = game.getAsteroids();
		final List<Ship> ships = game.getShips();
		for (final Ship ship : ships) {
			for (final Asteroid asteroid : asteroids) {
				if (asteroid.isEnabled() && !asteroid.isDestroyed()) {
					testBoxes(ship.getBullets(), asteroid, wrapArea, game);
				}
			}
		}
	}
}
