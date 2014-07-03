package com.harmboschloo.boxy.util;

import com.harmboschloo.boxy.math.VectorF;

public class Parse {
	public static boolean parseBoolean(final String value,
			final boolean defaultValue) {
		try {
			return Boolean.parseBoolean(value);
		} catch (final Throwable error) {
			return defaultValue;
		}
	}

	public static float parseFloat(final String value, final float defaultValue) {
		try {
			return Float.parseFloat(value);
		} catch (final Throwable error) {
			return defaultValue;
		}
	}

	public static int parseInt(final String value, final int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (final Throwable error) {
			return defaultValue;
		}
	}

	public static int parseInt(final String value, final int min,
			final int max, final int defaultValue) {
		try {
			final int intValue = Integer.parseInt(value);
			if (intValue >= min && intValue <= max) {
				return intValue;
			}
		} catch (final Throwable error) {
		}
		return defaultValue;
	}

	public static boolean parseIntBoolean(final String value,
			final boolean defaultValue) {
		try {
			final int intValue = Integer.parseInt(value);
			if (intValue == 0) {
				return false;
			} else if (intValue == 1) {
				return true;
			}
		} catch (final Throwable error) {
		}
		return defaultValue;
	}

	public static VectorF parseVectorF(final String value,
			final VectorF defaultValue) {
		try {
			return VectorF.parseVector(value);
		} catch (final Throwable error) {
			return defaultValue;
		}
	}
}
