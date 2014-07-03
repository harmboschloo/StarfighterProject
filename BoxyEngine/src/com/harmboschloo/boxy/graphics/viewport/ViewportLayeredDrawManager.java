package com.harmboschloo.boxy.graphics.viewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.collision.BoxContactTester;
import com.harmboschloo.boxy.graphics.DrawBox;
import com.harmboschloo.boxy.graphics.DrawBoxManagerStats;
import com.harmboschloo.boxy.graphics.Drawer;
import com.harmboschloo.boxy.graphics.LayeredDrawManager;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;

public class ViewportLayeredDrawManager implements HasViewportUpdate,
		LayeredDrawManager, DrawBoxManagerStats {
	private class DrawData {
		int layer;
		Drawer drawer;
		VectorF position;
		float angle;

		DrawData(final Drawer drawer, final VectorF position,
				final float angle, final int layer) {
			this.layer = layer;
			this.drawer = drawer;
			this.position = position;
			this.angle = angle;
		}
	}

	private final Iterable<? extends DrawBox> drawBoxes;
	private final AreaF wrapArea;
	private final BoxContactTester tester = new BoxContactTester();
	private final ArrayList<DrawData> drawData = new ArrayList<DrawData>();
	private final Comparator<DrawData> comparator;
	private int totalBoxCount = 0;
	private int activeBoxCount = 0;
	private int visibleBoxCount = 0;
	private int drawCount = 0;
	private VectorF currentPosition = null;

	private float currentAngle = 0;

	public ViewportLayeredDrawManager(final AreaF wrapArea,
			final Iterable<? extends DrawBox> drawBoxes) {
		this.drawBoxes = drawBoxes;
		this.wrapArea = wrapArea;

		comparator = new Comparator<DrawData>() {
			@Override
			public int compare(final DrawData data1, final DrawData data2) {
				if (data1.layer == data2.layer) {
					return 0;
				} else if (data1.layer < data2.layer) {
					return -1;
				} else {
					return 1;
				}
			}
		};
	}

	private void clearDrawData() {
		drawData.clear();
	}

	@Override
	public void draw(final Drawer drawer) {
		draw(drawer, 0);
	}

	@Override
	public void draw(final Drawer drawer, final int layer) {
		drawData.add(new DrawData(drawer, currentPosition, currentAngle, layer));
	}

	@Override
	public void draw(final Iterable<? extends Drawer> drawers) {
		draw(drawers, 0);
	}

	@Override
	public void draw(final Iterable<? extends Drawer> drawers, final int layer) {
		for (final Drawer drawer : drawers) {
			draw(drawer, layer);
		}
	}

	private void drawDrawData(final Context2d context) {
		for (final DrawData data : drawData) {
			context.save();
			context.translate(data.position.x, data.position.y);
			context.rotate(data.angle);
			data.drawer.draw(context);
			context.restore();
		}
	}

	private void findDrawData(final Viewport viewport) {
		totalBoxCount = 0;
		activeBoxCount = 0;
		visibleBoxCount = 0;
		tester.setBox1(viewport);
		for (final DrawBox box : drawBoxes) {
			++totalBoxCount;
			if (box.isEnabled()) {
				++activeBoxCount;
				tester.setBox2(box);
				tester.updateRelativePositionAndRotation(wrapArea);
				if (tester.test()) {
					++visibleBoxCount;
					currentPosition = viewport.getCenter().plus(
							tester.getRelativePosition());
					currentAngle = box.getRotation().getAngle();
					box.draw(this);
				}
			}
		}
		drawCount = drawData.size();
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

	private void sortDrawData() {
		Collections.sort(drawData, comparator);
	}

	@Override
	public void update(final Viewport viewport) {
		findDrawData(viewport);
		sortDrawData();
		drawDrawData(viewport.getContext());
		clearDrawData();
	}
}
