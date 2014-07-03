package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class GridDrawer implements HasViewportUpdate {
	private final VectorI gridSize = new VectorI();
	private final VectorF cellSize = new VectorF();
	private final CssColor color;

	public GridDrawer(final VectorI gridSize, final CssColor color,
			final AreaF wrapArea) {
		this.gridSize.copy(gridSize);
		cellSize.set(wrapArea.getWidth() / gridSize.x, wrapArea.getHeight()
				/ gridSize.y);
		this.color = color;
	}

	@Override
	public void update(final Viewport viewport) {
		final Context2d context = viewport.getContext();

		final VectorF topLeft = viewport.getCenter().minus(
				viewport.getHalfSize());
		final VectorF bottomRight = viewport.getCenter().plus(
				viewport.getHalfSize());

		final VectorI min = new VectorI(
				(int) Math.floor(topLeft.x / cellSize.x),
				(int) Math.floor(topLeft.y / cellSize.y));
		final VectorI max = new VectorI((int) Math.ceil(bottomRight.x
				/ cellSize.x), (int) Math.ceil(bottomRight.y / cellSize.y));

		context.save();
		context.setLineWidth(2);
		context.setStrokeStyle(color);

		context.beginPath();
		for (int x = min.x; x < max.x; ++x) {
			context.moveTo(x * cellSize.x, min.y * cellSize.y);
			context.lineTo(x * cellSize.x, max.y * cellSize.y);
		}
		for (int y = min.y; y < max.y; ++y) {
			context.moveTo(min.x * cellSize.x, y * cellSize.y);
			context.lineTo(max.x * cellSize.x, y * cellSize.y);
		}
		context.stroke();

		context.restore();
	}
}
