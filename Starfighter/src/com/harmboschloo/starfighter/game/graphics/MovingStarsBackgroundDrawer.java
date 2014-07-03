package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Random;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.ui.CanvasNotSupportedException;

// TODO: as DrawBox? or duplicate...
public class MovingStarsBackgroundDrawer implements HasViewportUpdate {
	private final AreaF wrapArea;
	private final Canvas background = Canvas.createIfSupported();

	public MovingStarsBackgroundDrawer(final AreaF wrapArea) {
		this.wrapArea = wrapArea;

		if (background == null) {
			throw new CanvasNotSupportedException();
		}

		initBackground();
	}

	private void initBackground() {
		background.setCoordinateSpaceWidth((int) (wrapArea.getWidth()) + 1);
		background.setCoordinateSpaceHeight((int) (wrapArea.getHeight()) + 1);

		final float size = 5;
		background.getContext2d().setFillStyle(CssColor.make(250, 150, 250));
		final int n = (int) (wrapArea.getWidth() * wrapArea.getHeight() / 40000);
		for (int i = 0; i < n; ++i) {
			final float x = Random.range(wrapArea.min.x, wrapArea.max.x - size);
			final float y = Random.range(wrapArea.min.y, wrapArea.max.y - size);
			background.getContext2d().fillRect(x, y, size, size);
		}
	}

	@Override
	public void update(final Viewport viewport) {
		final Context2d context = viewport.getContext();
		final float viewBoxWidth = viewport.getHalfSize().x * 2;
		final float viewBoxHeight = viewport.getHalfSize().y * 2;
		final float width = wrapArea.getWidth();
		final float height = wrapArea.getHeight();

		final VectorF topLeft = viewport.getCenter().minus(
				viewport.getHalfSize());

		if (topLeft.x < wrapArea.min.x) {
			if (topLeft.y < wrapArea.min.y) {
				// top left
				context.drawImage(background.getCanvasElement(), wrapArea.min.x
						- width, wrapArea.min.y - height);
				context.drawImage(background.getCanvasElement(), wrapArea.min.x
						- width, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y - height);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y);
			} else if ((topLeft.y + viewBoxHeight) >= wrapArea.max.y) {
				// bottom left
				context.drawImage(background.getCanvasElement(), wrapArea.min.x
						- width, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(), wrapArea.min.x
						- width, wrapArea.max.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.max.y);
			} else {
				// left
				context.drawImage(background.getCanvasElement(), wrapArea.min.x
						- width, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y);
			}
		} else if ((topLeft.x + viewBoxWidth) > wrapArea.max.x) {
			if (topLeft.y < wrapArea.min.y) {
				// top right
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y - height);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.max.x, wrapArea.min.y - height);
				context.drawImage(background.getCanvasElement(),
						wrapArea.max.x, wrapArea.min.y);
			} else if ((topLeft.y + viewBoxHeight) >= wrapArea.max.y) {
				// bottom right
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.max.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.max.x, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.max.x, wrapArea.max.y);

			} else {
				// right
				context.drawImage(background.getCanvasElement(),
						wrapArea.min.x, wrapArea.min.y);
				context.drawImage(background.getCanvasElement(),
						wrapArea.max.x, wrapArea.min.y);
			}
		} else if (topLeft.y < wrapArea.min.y) {
			// top
			context.drawImage(background.getCanvasElement(), wrapArea.min.x,
					wrapArea.min.y - height);
			context.drawImage(background.getCanvasElement(), wrapArea.min.x,
					wrapArea.min.y);

		} else if ((topLeft.y + viewBoxHeight) >= wrapArea.max.y) {
			// bottom
			context.drawImage(background.getCanvasElement(), wrapArea.min.x,
					wrapArea.min.y);
			context.drawImage(background.getCanvasElement(), wrapArea.min.x,
					wrapArea.max.y);

		} else {
			// center
			context.drawImage(background.getCanvasElement(), wrapArea.min.x,
					wrapArea.min.y);
		}
	}
}
