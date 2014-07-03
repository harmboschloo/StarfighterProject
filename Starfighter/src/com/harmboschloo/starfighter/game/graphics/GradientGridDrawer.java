package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class GradientGridDrawer implements HasViewportUpdate {
	private final VectorI gridSize = new VectorI();
	private final VectorF cellSize = new VectorF();
	private final CssColor[] colorsX;
	private final CssColor[] colorsY;

	public GradientGridDrawer(final VectorI gridSize, final Color[] baseColors,
			final AreaF wrapArea) {
		this.gridSize.copy(gridSize);
		cellSize.set(wrapArea.getWidth() / gridSize.x, wrapArea.getHeight()
				/ gridSize.y);
		colorsX = Color.interpolateCssColors(baseColors, gridSize.x);
		colorsY = Color.interpolateCssColors(baseColors, gridSize.y);
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

		int colorMax = colorsX.length;
		for (int x = min.x; x < max.x; ++x) {
			context.beginPath();
			context.setStrokeStyle(colorsX[Limit.wrap(x, 0, colorMax)]);
			context.moveTo(x * cellSize.x, min.y * cellSize.y);
			context.lineTo(x * cellSize.x, max.y * cellSize.y);
			context.stroke();
		}
		colorMax = colorsY.length;
		for (int y = min.y; y < max.y; ++y) {
			context.beginPath();
			context.setStrokeStyle(colorsY[Limit.wrap(y, 0, colorMax)]);
			context.moveTo(min.x * cellSize.x, y * cellSize.y);
			context.lineTo(max.x * cellSize.x, y * cellSize.y);
			context.stroke();
		}

		// context.setStrokeStyle("#dd0000");
		// context.beginPath();
		// context.moveTo(0, topLeft.y);
		// context.lineTo(0, bottomRight.y);
		// context.moveTo(gridSize.x * cellSize.x, topLeft.y);
		// context.lineTo(gridSize.x * cellSize.x, bottomRight.y);
		// context.moveTo(topLeft.x, 0);
		// context.lineTo(bottomRight.x, 0);
		// context.moveTo(topLeft.x, gridSize.y * cellSize.y);
		// context.lineTo(bottomRight.x, gridSize.y * cellSize.y);
		// context.stroke();

		context.restore();
	}
}
