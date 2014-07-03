package com.harmboschloo.boxy.graphics.viewport;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.i18n.client.NumberFormat;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.util.UpdateCounter;

public class FPSDrawer implements HasViewportUpdate {
	private final UpdateCounter counter = new UpdateCounter(30);
	private final double x;
	private final double y;
	private final CssColor color;

	public FPSDrawer(final double x, final double y, final CssColor color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	@Override
	public void update(final Viewport viewport) {
		final Context2d context = viewport.getContext();
		final VectorF upperLeft = viewport.getCenter().minus(
				viewport.getHalfSize());
		counter.update();
		context.setFillStyle(color);
		context.fillText(
				"fps: "
						+ NumberFormat.getFormat("###0.00").format(
								counter.getUpdateFrequency()), upperLeft.x + x,
				upperLeft.y + y);
	}
}
