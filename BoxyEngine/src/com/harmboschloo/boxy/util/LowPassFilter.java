package com.harmboschloo.boxy.util;

/**
 * from http://en.wikipedia.org/wiki/Low-pass_filter: Return RC low-pass filter
 * output samples, given input samples, time interval dt, and time constant RC
 * <code>
 * function lowpass(real[0..n] x, real dt, real RC) 
 *     var real[0..n] y
 *     var real α := dt / (RC + dt)
 *     y[0] := x[0] 
 *     for i from 1 to n 
 *         y[i] := α * x[i] + (1-α) * y[i-1] 
 *     return y
 * </code>
 */
public class LowPassFilter {
	private float value;

	public LowPassFilter() {
		this(0);
	}

	public LowPassFilter(final float value) {
		this.value = value;
	}

	public float getValue() {
		return value;
	}

	public void reset() {
		reset(0);
	}

	public void reset(final float value) {
		this.value = value;
	}

	public float update(final float inValue, final float alpha) {
		value = value + alpha * (inValue - value);
		return value;
	}

	public float update(final float inValue, final float dt, final float rc) {
		return update(inValue, dt / (rc + dt));
	}
}
