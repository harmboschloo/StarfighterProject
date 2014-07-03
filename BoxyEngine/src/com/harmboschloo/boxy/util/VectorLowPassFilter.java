package com.harmboschloo.boxy.util;

import com.harmboschloo.boxy.math.VectorF;

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
public class VectorLowPassFilter {
	private final VectorF value = new VectorF();
	private float alpha = 1;

	public VectorLowPassFilter() {
	}

	public VectorLowPassFilter(final float alpha) {
		setAlpha(alpha);
	}

	public VectorLowPassFilter(final float dt, final float rc) {
		setAlpha(dt, rc);
	}

	public VectorLowPassFilter(final float dt, final float rc,
			final VectorF value) {
		setAlpha(dt, rc);
		this.value.copy(value);
	}

	public VectorLowPassFilter(final float alpha, final VectorF value) {
		setAlpha(alpha);
		this.value.copy(value);
	}

	public VectorLowPassFilter(final VectorF value) {
		this.value.copy(value);
	}

	public float getAlpha() {
		return alpha;
	}

	public VectorF getValue() {
		return value;
	}

	public void setAlpha(final float alpha) {
		this.alpha = alpha;
	}

	public void setAlpha(final float dt, final float rc) {
		alpha = dt / (rc + dt);
	}

	public VectorF update(final VectorF inValue) {
		final VectorF diff = inValue.minus(value);
		diff.multiplyBy(alpha);
		value.add(diff);
		return value;
	}
}
