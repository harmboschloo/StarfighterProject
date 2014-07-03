package com.harmboschloo.boxy.server;

public class Analytics {
	private static native void _trackEvent(String category, String action) /*-{
																			$wnd._gaq.push([ '_trackEvent', category, action ]);
																			}-*/;

	private static native void _trackEvent(String category, String action,
			String label) /*-{
							$wnd._gaq.push([ '_trackEvent', category, action, label ]);
							}-*/;

	private static native void _trackEvent(String category, String action,
			String label, int value) /*-{
										$wnd._gaq.push([ '_trackEvent', category, action, label, value ]);
										}-*/;

	public static boolean isAvailable() {
		return isDefined();
	};

	private static native boolean isDefined() /*-{
												return (typeof $wnd._gaq != "undefined");
												}-*/;

	public static void trackEvent(final String category, final String action) {
		if (isAvailable()) {
			_trackEvent(category, action);
		}
	}

	public static void trackEvent(final String category, final String action,
			final String label) {
		if (isAvailable()) {
			_trackEvent(category, action, label);
		}
	}

	public static void trackEvent(final String category, final String action,
			final String label, final int value) {
		if (isAvailable()) {
			_trackEvent(category, action, label, value);
		}
	}
}
