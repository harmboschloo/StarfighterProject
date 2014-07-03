package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.starfighter.game.sound.ShipSounds;

public class DroneShip extends Ship {
	static class DronePixelData implements ShipPixelData {
		@Override
		public int[] getColors() {
			return colors;
		}

		@Override
		public VectorI getPixelCount() {
			return pixelCount;
		}

		@Override
		public VectorF getPixelOffsetSize() {
			return pixelOffsetSize;
		}

		@Override
		public int[] getPixels() {
			return pixels;
		}

		@Override
		public int[] getRotateLeftPixels() {
			return rotateLeftPixels;
		}

		@Override
		public int[] getRotateRightPixels() {
			return rotateRightPixels;
		}

		@Override
		public int[] getThrustBackPixels() {
			return thrustBackPixels;
		}

		@Override
		public int[] getThrustForwardPixels() {
			return thrustForwardPixels;
		}

		@Override
		public int[] getThrustPixels() {
			return thrustPixels;
		}
	}

	private static final VectorI pixelCount = new VectorI(4, 5);
	private static final VectorF pixelOffsetSize = new VectorF(2.f);
	private static final int[] pixels = { 1, 2, 4, 5, 6, 7, 8, 9, 10, 12, 13,
			14, 15, 17, 18 };
	private static final int[] colors = { 179, 179, 154, 143, 143, 143, 154,
			143, 143, 154, 143, 143, 143, 179, 179 };
	private static final int[] thrustPixels = { 0, 3, 16, 19 };
	private static final int[] rotateLeftPixels = { 3, 16 };
	private static final int[] rotateRightPixels = { 0, 19 };
	private static final int[] thrustForwardPixels = { 0, 16 };
	private static final int[] thrustBackPixels = { 3, 19 };

	private static final ShipPixelData pixelData = new DronePixelData();

	public DroneShip(final Color color, final ShipSounds sounds) {
		super(color, pixelData, sounds);
		setMaxSpeed(200);
		setMaxAngularVelocity(8);
		setThrustAcceleration(300);
		setRotateAcceleration(60);
		setBulletFireDelay(0.2f);
		setBulletSpeedIncrease(400);
		setBulletMaxLifetime(0.5f);
		getBulletPixelCount().set(4, 1);
		getBulletPixelOffsetSize().set(1.5f);
		setBoxOffset(0f);
		setPerformanceDamageGain(0.5f);

	}
}
