package com.harmboschloo.boxy.math;

public class Random {
	// [min, max)
	public static double range(final double min, final double max) {
		return (Math.random() * (max - min) + min);
	}

	// [min, max)
	public static float range(final float min, final float max) {
		return (float) (Math.random() * (max - min) + min);
	}

	// [min, max]
	public static int range(final int min, final int max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}
}
