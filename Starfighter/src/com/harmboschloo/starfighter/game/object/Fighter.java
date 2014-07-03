package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.sound.ShipSounds;

public class Fighter extends Ship {
	static class FighterPixelData implements ShipPixelData {
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

	private static final VectorI pixelCount = new VectorI(8, 5);
	private static final VectorF pixelOffsetSize = new VectorF(2.5f);
	private static final int[] pixels = { 1, 2, 9, 10, 11, 12, 13, 17, 18, 19,
			20, 21, 22, 23, 25, 26, 27, 28, 29, 33, 34 };
	private static final int[] colors = { 179, 179, 154, 154, 154, 143, 143,
			128, 128, 128, 113, 113, 128, 128, 154, 154, 154, 143, 143, 179,
			179 };
	private static final int[] thrustPixels = { 0, 3, 32, 35 };
	private static final int[] rotateLeftPixels = { 3, 32 };
	private static final int[] rotateRightPixels = { 0, 35 };
	private static final int[] thrustForwardPixels = { 0, 32 };
	private static final int[] thrustBackPixels = { 3, 35 };

	private static final ShipPixelData pixelData = new FighterPixelData();

	public Fighter(final Color color, final ShipSounds sounds) {
		super(color, pixelData, sounds);
		setMaxSpeed(175);
		setMaxAngularVelocity(6);
		setThrustAcceleration(250);
		setRotateAcceleration(40);
		setBulletFireDelay(0.2f);
		setBulletSpeedIncrease(400);
		setBulletMaxLifetime(0.5f);
		getBulletPixelCount().set(6, 1);
		getBulletPixelOffsetSize().set(2f);
		setBoxOffset(1.5f);
		setJumpSequenceTime(Config.JUMP_SEQUENCE_TIME);
		setJumpRange(450);
		setPerformanceDamageGain(0.2f);
	}
}
