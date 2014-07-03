package com.harmboschloo.boxy.graphics.color;

import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.math.VectorI;

public class CssColorModel extends CssColorArray {
	private final Color baseColor;

	public CssColorModel(final VectorI size, final Color baseColor) {
		super(size);
		this.baseColor = baseColor;
	}

	public CssColor createColor(final int grayValue) {
		return CssColorizer.colorizeCss(grayValue, baseColor);
	}

	public Color getBaseColor() {
		return baseColor;
	}

	public void set(final VectorI position, final int grayValue) {
		set(position, createColor(grayValue));
	}

	public void setAll(final int grayValue) {
		setAll(createColor(grayValue));
	}
}
