package com.harmboschloo.boxy.util;

public class TimeSpec {
	private float dt;
	private double time;
	private long startTime;

	public TimeSpec() {
		reset(0);
	}

	public TimeSpec(final float dt) {
		reset(dt);
	}

	public double getRealRunTime() {
		return 0.001 * getRealRunTimeMillies();
	}

	public long getRealRunTimeMillies() {
		return (System.currentTimeMillis() - startTime);
	}

	public double getRunTime() {
		return time;
	}

	public float getTimeStep() {
		return dt;
	}

	public void reset() {
		time = 0;
		startTime = System.currentTimeMillis();
	}

	public void reset(final float dt) {
		reset();
		this.dt = dt;
	}

	public void resetRealRunTimeToRunTime() {
		startTime = System.currentTimeMillis() - (long) (getRunTime() * 1000);
	}

	public void setTimeStep(final float dt) {
		this.dt = dt;
	}

	public void step() {
		time += dt;
	}
}
