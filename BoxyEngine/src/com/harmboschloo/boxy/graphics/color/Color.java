package com.harmboschloo.boxy.graphics.color;

import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.math.Interpolate;
import com.harmboschloo.boxy.math.Limit;

public class Color {
	public static Color interpolate(final Color color1, final Color color2,
			final float fraction) {
		final Color color = new Color();
		color.r = Interpolate.linear(color1.r, color2.r, fraction);
		color.g = Interpolate.linear(color1.g, color2.g, fraction);
		color.b = Interpolate.linear(color1.b, color2.b, fraction);
		color.a = Interpolate.linear(color1.a, color2.a, fraction);
		return color;
	}

	public static Color interpolateColor(final float fraction,
			final Color[] baseColors) {
		final int max = baseColors.length - 1;
		final float baseFraction = fraction * max;
		final int i0 = Limit.clamp((int) baseFraction, 0, max);
		final int i1 = Limit.clamp(i0 + 1, 0, max);
		final float interpolateFraction = baseFraction - i0;
		return interpolate(baseColors[i0], baseColors[i1], interpolateFraction);
	}

	public static Color[] interpolateColors(final Color[] baseColors,
			final int n) {
		final Color[] colors = new Color[n];
		final float max = n - 1;
		for (int i = 0; i < n; ++i) {
			colors[i] = interpolateColor(i / max, baseColors);
		}
		return colors;
	}

	public static CssColor[] interpolateCssColors(final Color[] baseColors,
			final int n) {
		final CssColor[] colors = new CssColor[n];
		final float max = n - 1;
		for (int i = 0; i < n; ++i) {
			colors[i] = interpolateColor(i / max, baseColors).makeCssColor();
		}
		return colors;
	}

	public int r;

	public int g;

	public int b;

	public int a;

	public Color() {
		this(0, 0, 0, 0);
	}

	public Color(final Color color) {
		copy(color);
	}

	public Color(final int r, final int g, final int b) {
		this(r, g, b, 255);
	}

	public Color(final int r, final int g, final int b, final int a) {
		set(r, g, b, a);
	}

	public void copy(final Color color) {
		r = color.r;
		g = color.g;
		b = color.b;
		a = color.a;
	}

	public CssColor makeCssColor() {
		return CssColor.make(r, g, b);
	}

	public void set(final int r, final int g, final int b, final int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	@Override
	public String toString() {
		if (a == 255) {
			return "rgb(" + r + "," + g + "," + b + ")";
		} else {
			return "rgba(" + r + "," + g + "," + b + "," + a + ")";
		}
	}
}
