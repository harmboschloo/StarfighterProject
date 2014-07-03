package com.harmboschloo.boxy.util;

public class UpdateCounter {
	private int maxCount = 60;
	private int count = 0;
	private long countStart = 0;
	private double updateFrequency = 0;

	public UpdateCounter() {

	}

	public UpdateCounter(final int maxCount) {
		this.maxCount = maxCount;
	}

	public double getUpdateFrequency() {
		return updateFrequency;
	}

	public void reset() {
		count = 0;
		countStart = System.currentTimeMillis();
		updateFrequency = 0;
	}

	public void reset(final int maxCount) {
		reset();
		this.maxCount = maxCount;
	}

	public boolean update() {
		++count;
		if (count == maxCount) {
			final long countEnd = System.currentTimeMillis();
			updateFrequency = 1000.0 * count / (countEnd - countStart);
			count = 0;
			countStart = countEnd;
			return true;
		}
		return false;
	}
}
