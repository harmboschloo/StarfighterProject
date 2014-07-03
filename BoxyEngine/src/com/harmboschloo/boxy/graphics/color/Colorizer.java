package com.harmboschloo.boxy.graphics.color;

public class Colorizer {
	protected static int clamp(final float value) {
		return ((int) value);
	}

	protected static int clamp(int value) {
		if (value < 0) {
			value = 0;
		} else if (value > 255) {
			value = 255;
		}
		return value;
	}

	public static Color colorize(final int grayValue, final Color color) {
		final Color newColor = new Color();
		newColor.r = clamp(color.r * (grayValue / 255.f));
		newColor.g = clamp(color.g * (grayValue / 255.f));
		newColor.b = clamp(color.b * (grayValue / 255.f));
		newColor.a = color.a;
		return newColor;
	}

	protected Color color = new Color();

	public Colorizer() {
	}

	public Colorizer(final Color color) {
		this.color.copy(color);
	}

	public Color colorize(final int grayValue) {
		return colorize(grayValue, color);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(final Color color) {
		this.color.copy(color);
	}
}
