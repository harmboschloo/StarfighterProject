package com.harmboschloo.boxy.graphics.pixel;

import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.util.XYArrayI;

public class PixelStatePointCountTracker implements PixelStateModel.Tracker {
	private final int state;
	private XYArrayI stateCounts = null;

	public PixelStatePointCountTracker(final int state) {
		this.state = state;
	}

	private void addCount(final int pixelIndex, final int addValue) {
		final int width = stateCounts.getSize().x - 1;
		final int x = pixelIndex % width;
		final int y = pixelIndex / width;
		stateCounts.add(x, y, addValue);
		stateCounts.add(x + 1, y, addValue);
		stateCounts.add(x, y + 1, addValue);
		stateCounts.add(x + 1, y + 1, addValue);
	}

	private int countAroundPoint(final VectorI point,
			final PixelStateModel model) {
		final PointPixelArea area = new PointPixelArea(point, model.getSize());
		int count = 0;
		final VectorI pixel = new VectorI();
		for (pixel.x = area.min.x; pixel.x <= area.max.x; ++pixel.x) {
			for (pixel.y = area.min.y; pixel.y <= area.max.y; ++pixel.y) {
				if (model.get(pixel) == state) {
					++count;
				}
			}
		}
		return count;
	}

	public int getCountAroundPoint(final VectorI point) {
		return stateCounts.get(point);
	}

	public boolean hasCountAroundPoint(final VectorI point) {
		return (getCountAroundPoint(point) > 0);
	}

	@Override
	public void onAllPixelStatesSetTo(final int newState,
			final PixelStateModel model) {
		if (newState != state) {
			stateCounts.setAll(0);
		} else {
			stateCounts.setAll(4);
			stateCounts.set(0, 0, 1);
			stateCounts.set(0, stateCounts.getSize().y - 1, 1);
			stateCounts.set(stateCounts.getSize().x - 1, 0, 1);
			stateCounts.set(stateCounts.getSize().x - 1,
					stateCounts.getSize().y - 1, 1);

			for (int i = 1; i < (stateCounts.getSize().x - 1); ++i) {
				stateCounts.set(i, 0, 2);
				stateCounts.set(i, stateCounts.getSize().y - 1, 2);
			}

			for (int i = 1; i < (stateCounts.getSize().y - 1); ++i) {
				stateCounts.set(0, i, 2);
				stateCounts.set(stateCounts.getSize().x - 1, i, 2);
			}
		}
	}

	@Override
	public void onPixelStateChange(final int pixelIndex, final int oldState,
			final int newState, final PixelStateModel model) {
		assert (oldState != newState);
		if (oldState == state) {
			addCount(pixelIndex, -1);
		} else if (newState == state) {
			addCount(pixelIndex, 1);
		}
	}

	public void track(final PixelStateModel model) {
		stateCounts = new XYArrayI(model.getSize().plus(1));
		model.addTracker(this);
		updateAllCounts(model);
	}

	private void updateAllCounts(final PixelStateModel model) {
		final VectorI point = new VectorI();
		for (point.x = 0; point.x < stateCounts.getSize().x; ++point.x) {
			for (point.y = 0; point.y < stateCounts.getSize().y; ++point.y) {
				updateCountsAroundPoint(point, model);
			}
		}
	}

	private void updateCountsAroundPoint(final VectorI point,
			final PixelStateModel model) {
		stateCounts.set(point, countAroundPoint(point, model));
	}
}
