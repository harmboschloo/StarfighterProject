package com.harmboschloo.boxy.graphics.pixel.text;

import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.math.VectorI;

public class CssColorArrayPixelHandler implements PixelHandler {
	private final VectorI offset;
	private final CssColor color;
	private final CssColorArray colors;

	public CssColorArrayPixelHandler(final CssColor color,
			final CssColorArray colors) {
		this(new VectorI(), color, colors);
	}

	public CssColorArrayPixelHandler(final VectorI offset,
			final CssColor color, final CssColorArray colors) {
		this.offset = offset;
		this.color = color;
		this.colors = colors;
	}

	@Override
	public void onPixel(final VectorI pixel) {
		final VectorI position = offset.plus(pixel);
		if (validPosition(position)) {
			colors.set(position, color);
		}
	}

	public boolean validPosition(final VectorI position) {
		return (position.x >= 0 && position.x < colors.getSize().x
				&& position.y >= 0 && position.y < colors.getSize().y);
	}
}
