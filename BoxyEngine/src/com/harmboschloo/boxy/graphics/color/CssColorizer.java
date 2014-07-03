package com.harmboschloo.boxy.graphics.color;

import com.google.gwt.canvas.dom.client.CssColor;

public class CssColorizer extends Colorizer {
	public static CssColor colorizeCss(final int grayValue, final Color color) {
		return CssColor.make(clamp(color.r * (grayValue / 255.f)),
				clamp(color.g * (grayValue / 255.f)), clamp(color.b
						* (grayValue / 255.f)));
	}

	private final Color color = new Color();

	public CssColorizer() {
	}

	public CssColorizer(final Color color) {
		super(color);
	}

	public CssColor colorizeCss(final int grayValue) {
		return colorizeCss(grayValue, color);
	}
}
