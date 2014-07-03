package com.harmboschloo.boxy.graphics.viewport;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.math.VectorF;

public class ViewportFiller implements HasViewportUpdate {
	private final CssColor fillColor;

	public ViewportFiller(final CssColor fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void update(final Viewport viewport) {
		final VectorF center = viewport.getCenter();
		final VectorF halfSize = viewport.getHalfSize();
		final Context2d context = viewport.getContext();
		context.setFillStyle(fillColor);
		context.fillRect(center.x - halfSize.x, center.y - halfSize.y,
				2 * halfSize.x, 2 * halfSize.y);
	}
}
