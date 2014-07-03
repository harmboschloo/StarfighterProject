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
import com.harmboschloo.starfighter.game.object.Bullet;
import com.harmboschloo.starfighter.game.object.Ship;

public class BulletBulletCollider implements HasGameUpdate {
	private final BoxContactTester boxTester = new BoxContactTester();
	private final PixelPointContactTester pixelPointTester = new PixelPointContactTester();

	private void test(final Iterable<Bullet> bullets1,
			final Iterable<Bullet> bullets2, final AreaF wrapArea,
			final Game game) {
		for (final Bullet bullet1 : bullets1) {
			if (bullet1.isActive()) {
				boxTester.setBox1(bullet1);
				for (final Bullet bullet2 : bullets2) {
					if (bullet2.isActive()) {
						boxTester.setBox2(bullet2);
						boxTester.updateRelativePositionAndRotation(wrapArea);
						if (boxTester.test()) {
							testPixels(bullet1, bullet2,
									boxTester.getRelativePosition(), game);
						}
					}
				}
			}
		}
	}

	private void testPixels(final Bullet bullet1, final Bullet bullet2,
			final VectorF relativePosition, final Game game) {
		pixelPointTester.initTest(bullet1, bullet2, relativePosition);
		final PixelStatePointCountTracker tracker = bullet1
				.getUndamagedTracker();
		final VectorI point = pixelPointTester.getPoint();
		final VectorI points = pixelPointTester.getPoints();
		for (point.x = points.x - 1; point.x >= 0; --point.x) {
			for (point.y = points.y - 1; point.y >= 0; --point.y) {
				if (tracker.hasCountAroundPoint(point)) {
					if (pixelPointTester.test()) {
						if (bullet1.damage(pixelPointTester.getPixel())) {
							bullet2.damageAroundPoint(point, 1);
							game.addExplosion(pixelPointTester
									.calculateAbsolutePointPosition(), bullet1,
									bullet2);
							if (bullet1.isDestroyed() || bullet2.isDestroyed()) {
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
		final List<Ship> ships = game.getShips();
		int i1 = 0;
		for (final Ship ship1 : ships) {
			for (int i2 = (i1 + 1); i2 < ships.size(); ++i2) {
				test(ship1.getBullets(), ships.get(i2).getBullets(), wrapArea,
						game);
			}
			++i1;
		}
	}
}
