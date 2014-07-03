package com.harmboschloo.boxy.graphics.viewport;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.DrawBoxManagerStats;
import com.harmboschloo.boxy.math.VectorF;

public class DrawBoxManagerStatsDrawer implements HasViewportUpdate {
	private final double x;
	private final double y;
	private final CssColor color;
	private final DrawBoxManagerStats stats;

	public DrawBoxManagerStatsDrawer(final double x, final double y,
			final CssColor color, final DrawBoxManagerStats stats) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.stats = stats;
	}

	@Override
	public void update(final Viewport viewport) {
		final Context2d context = viewport.getContext();
		final VectorF upperLeft = viewport.getCenter().minus(
				viewport.getHalfSize());
		context.setFillStyle(color);
		context.fillText(
				"drawn: " + stats.getDrawCount() + " ("
						+ stats.getVisibleBoxCount() + "/"
						+ stats.getActiveBoxCount() + "/"
						+ stats.getTotalBoxCount() + ")", upperLeft.x + x,
				upperLeft.y + y);
	}
}
