package com.harmboschloo.boxy.math;

public class Limit {
	// clamp [min,max]
	public static float clamp(float value, final float min, final float max) {
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		return value;
	}

	// clamp [min,max]
	public static int clamp(int value, final int min, final int max) {
		if (value < min) {
			value = min;
		} else if (value > max) {
			value = max;
		}
		return value;
	}

	// clamp [min,max]
	public static void clamp(final VectorF vector, final float min,
			final float max) {
		vector.x = clamp(vector.x, min, max);
		vector.y = clamp(vector.y, min, max);
	}

	// clamp [min,max]
	public static void clamp(final VectorF vector, final VectorF min,
			final VectorF max) {
		vector.x = clamp(vector.x, min.x, max.x);
		vector.y = clamp(vector.y, min.y, max.y);
	}

	public static float max(final float a, final float b, final float c,
			final float d) {
		return Math.max(Math.max(Math.max(a, b), c), d);
	}

	public static float min(final float a, final float b, final float c,
			final float d) {
		return Math.min(Math.min(Math.min(a, b), c), d);
	}

	// wrap [min,max)
	public static float wrap(float value, final float min, final float max) {
		if (value < min) {
			value = max - (min - value) % (max - min);
		}
		// value can still be max is some cases (-10,0,10)
		if (value >= max) {
			value = min + (value - max) % (max - min);
		}
		assert (value >= min && value < max) : "wrap failed: " + value + " ["
				+ min + "," + max + ")";
		return value;
	}

	// wrap [min,max)
	public static int wrap(int value, final int min, final int max) {
		if (value < min) {
			value = max - (min - value) % (max - min);
		}
		// value can still be max is some cases (-10,0,10)
		if (value >= max) {
			value = min + (value - max) % (max - min);
		}
		assert (value >= min && value < max) : "wrap failed: " + value + " ["
				+ min + "," + max + ")";
		return value;
	}

	public static void wrap(final VectorF vector, final VectorF min,
			final VectorF max) {
		vector.x = wrap(vector.x, min.x, max.x);
		vector.y = wrap(vector.y, min.y, max.y);
	}

	public static void wrap(final VectorI vector, final VectorI min,
			final VectorI max) {
		vector.x = wrap(vector.x, min.x, max.x);
		vector.y = wrap(vector.y, min.y, max.y);
	}

	public static float wrapAngle(final float angle) {
		return wrap(angle, (float) -Math.PI, (float) Math.PI);
	}
}
