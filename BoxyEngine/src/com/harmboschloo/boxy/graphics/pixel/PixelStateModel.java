package com.harmboschloo.boxy.graphics.pixel;

import java.util.ArrayList;

import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.util.XYArrayI;

public class PixelStateModel {
	public static interface Tracker {
		void onAllPixelStatesSetTo(int newState, PixelStateModel model);

		void onPixelStateChange(int pixelIndex, int oldState, int newState,
				PixelStateModel model);
	}

	private final XYArrayI states;

	private final ArrayList<Tracker> trackers = new ArrayList<Tracker>();

	public PixelStateModel(final VectorI size) {
		states = new XYArrayI(size);
	}

	public void addTracker(final Tracker tracker) {
		trackers.add(tracker);
	}

	public boolean change(final int pixelIndex, final int newState) {
		final int oldState = states.get(pixelIndex);
		if (newState != oldState) {
			states.set(pixelIndex, newState);
			notifyTrackers(pixelIndex, oldState, newState);
			return true;
		}
		return false;
	}

	public boolean change(final int pixelIndex, final int oldState,
			final int newState) {
		if (newState != oldState && get(pixelIndex) == oldState) {
			states.set(pixelIndex, newState);
			notifyTrackers(pixelIndex, oldState, newState);
			return true;
		}
		return false;
	}

	public boolean change(final VectorI pixel, final int newState) {
		return change(states.getIndex(pixel), newState);
	}

	public boolean change(final VectorI pixel, final int oldState,
			final int newState) {
		return change(states.getIndex(pixel), oldState, newState);
	}

	public void changeAll(final int oldState, final int newState) {
		if (oldState == newState) {
			return;
		}

		final int n = states.getCount();
		for (int i = 0; i < n; ++i) {
			if (states.get(i) == oldState) {
				states.set(i, newState);
				notifyTrackers(i, oldState, newState);
			}
		}
	}

	public int changeAroundPoint(final VectorI point, final int state,
			final int max) {
		if (max == 0) {
			return 0;
		}

		final PointPixelArea area = new PointPixelArea(point, getSize());
		int count = 0;
		final VectorI pixel = new VectorI();
		for (pixel.x = area.min.x; pixel.x <= area.max.x; ++pixel.x) {
			for (pixel.y = area.min.y; pixel.y <= area.max.y; ++pixel.y) {
				if (change(pixel, state)) {
					++count;
					if (count >= max) {
						return count;
					}
				}
			}
		}

		return count;
	}

	public int changeAroundPoint(final VectorI point, final int oldState,
			final int newState, final int max) {
		if (max == 0 || newState == oldState) {
			return 0;
		}

		final PointPixelArea area = new PointPixelArea(point, getSize());
		int count = 0;
		final VectorI pixel = new VectorI();
		for (pixel.x = area.min.x; pixel.x <= area.max.x; ++pixel.x) {
			for (pixel.y = area.min.y; pixel.y <= area.max.y; ++pixel.y) {
				final int index = states.getIndex(pixel);
				if (states.get(index) == oldState) {
					states.set(index, newState);
					notifyTrackers(index, oldState, newState);
					++count;
					if (count >= max) {
						return count;
					}
				}
			}
		}

		return count;
	}

	public int get(final int pixelIndex) {
		return states.get(pixelIndex);
	}

	public int get(final VectorI pixel) {
		return states.get(pixel);
	}

	public int getCount() {
		return states.getCount();
	}

	public VectorI getSize() {
		return states.getSize();
	}

	private void notifyTrackers(final int newState) {
		for (final Tracker tracker : trackers) {
			tracker.onAllPixelStatesSetTo(newState, this);
		}
	}

	private void notifyTrackers(final int pixelIndex, final int oldState,
			final int newState) {
		for (final Tracker tracker : trackers) {
			tracker.onPixelStateChange(pixelIndex, oldState, newState, this);
		}
	}

	public void removeTracker(final Tracker tracker) {
		trackers.remove(tracker);
	}

	public void set(final int pixelIndex, final int newState) {
		final int oldState = states.get(pixelIndex);
		if (newState != oldState) {
			states.set(pixelIndex, newState);
			notifyTrackers(pixelIndex, oldState, newState);
		}
	}

	public void set(final int[] pixelIndices, final int newState) {
		for (final int i : pixelIndices) {
			set(i, newState);
		}
	}

	public void set(final VectorI pixel, final int newState) {
		final int oldState = states.get(pixel);
		if (newState != oldState) {
			states.set(pixel, newState);
			notifyTrackers(states.getIndex(pixel), oldState, newState);
		}
	}

	public void setAll(final int newState) {
		states.setAll(newState);
		notifyTrackers(newState);
	}
}
