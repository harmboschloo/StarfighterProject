package com.harmboschloo.boxy.graphics.viewport;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.collision.BoxContactTester;
import com.harmboschloo.boxy.graphics.DrawBox;
import com.harmboschloo.boxy.graphics.DrawBoxManagerStats;
import com.harmboschloo.boxy.graphics.DrawManager;
import com.harmboschloo.boxy.graphics.Drawer;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;

public class ViewportDrawManager implements HasViewportUpdate, DrawManager,
		DrawBoxManagerStats {
	private final Iterable<? extends DrawBox> drawBoxes;
	private final AreaF wrapArea;
	private final BoxContactTester tester = new BoxContactTester();
	private int totalBoxCount = 0;
	private int activeBoxCount = 0;
	private int visibleBoxCount = 0;
	private int drawCount = 0;
	private Context2d context = null;

	public ViewportDrawManager(final AreaF wrapArea,
			final Iterable<? extends DrawBox> drawBoxes) {
		this.drawBoxes = drawBoxes;
		this.wrapArea = wrapArea;
	}

	private void beginBoxDraw(final Viewport viewport, final DrawBox box) {
		++visibleBoxCount;
		final VectorF center = viewport.getCenter().plus(
				tester.getRelativePosition());
		context.save();
		context.translate(center.x, center.y);
		context.rotate(box.getRotation().getAngle());
	}

	@Override
	public void draw(final Drawer drawer) {
		++drawCount;
		drawer.draw(context);
	}

	@Override
	public void draw(final Iterable<? extends Drawer> drawers) {
		for (final Drawer drawer : drawers) {
			draw(drawer);
		}
	}

	private void endBoxDraw() {
		context.restore();
	}

	@Override
	public int getActiveBoxCount() {
		return activeBoxCount;
	}

	@Override
	public int getDrawCount() {
		return drawCount;
	}

	@Override
	public int getTotalBoxCount() {
		return totalBoxCount;
	}

	@Override
	public int getVisibleBoxCount() {
		return visibleBoxCount;
	}

	@Override
	public void update(final Viewport viewport) {
		totalBoxCount = 0;
		activeBoxCount = 0;
		visibleBoxCount = 0;
		drawCount = 0;
		context = viewport.getContext();
		tester.setBox1(viewport);
		for (final DrawBox box : drawBoxes) {
			++totalBoxCount;
			if (box.isEnabled()) {
				++activeBoxCount;
				tester.setBox2(box);
				tester.updateRelativePositionAndRotation(wrapArea);
				if (tester.test()) {
					beginBoxDraw(viewport, box);
					box.draw(this);
					endBoxDraw();
				}
			}
		}
		context = null;
	}
}
