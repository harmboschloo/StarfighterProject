package com.harmboschloo.boxy.graphics.pixel;

import com.harmboschloo.boxy.math.VectorI;

public class PixelStateCountTracker implements PixelStateModel.Tracker {
	private final int state;
	private int count;

	public PixelStateCountTracker(final int state) {
		this.state = state;
		count = 0;
	}

	private void countAll(final PixelStateModel model) {
		final VectorI pixel = new VectorI();
		final VectorI size = model.getSize();
		count = 0;
		for (pixel.x = 0; pixel.x < size.x; ++pixel.x) {
			for (pixel.y = 0; pixel.y < size.y; ++pixel.y) {
				if (model.get(pixel) == state) {
					++count;
				}
			}
		}
	}

	public int getState() {
		return state;
	}

	public int getStateCount() {
		return count;
	}

	@Override
	public void onAllPixelStatesSetTo(final int newState,
			final PixelStateModel model) {
		if (newState != state) {
			count = 0;
		} else {
			count = model.getSize().getArea();
		}
	}

	@Override
	public void onPixelStateChange(final int pixelIndex, final int oldState,
			final int newState, final PixelStateModel model) {
		assert (oldState != newState);
		if (oldState == state) {
			--count;
		} else if (newState == state) {
			++count;
		}
		assert (count >= 0);
		assert (count <= model.getSize().getArea());
	}

	public void track(final PixelStateModel model) {
		model.addTracker(this);
		countAll(model);
	}
}
