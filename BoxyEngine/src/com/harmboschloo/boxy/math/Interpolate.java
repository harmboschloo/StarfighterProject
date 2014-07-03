package com.harmboschloo.boxy.math;

public class Interpolate {
	public static float linear(final float value1, final float value2,
			final float fraction) {
		return (value1 + fraction * (value2 - value1));
	}

	public static int linear(final int value1, final int value2,
			final float fraction) {
		return (int) (value1 + fraction * (value2 - value1));
	}
}
