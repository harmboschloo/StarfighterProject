package com.harmboschloo.starfighter.app;

import com.harmboschloo.boxy.server.Analytics;

public class Stats {
	private static final String SF = "SF " + Starfighter.VERSION;

	public static boolean available() {
		return Analytics.isAvailable();
	};

	public static void track(final String action) {
		Analytics.trackEvent(SF, action);
	}

	public static void track(final String action, final String label) {
		Analytics.trackEvent(SF, action, label);
	}

	public static void track(final String action, final String label,
			final int value) {
		Analytics.trackEvent(SF, action, label, value);
	}
}
