package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.VectorF;

public class DefaultDynamicBody extends DefaultMovingBody implements
		DynamicBody {
	private final VectorF acceleration = new VectorF();
	private float angularAcceleration = 0;

	public void addAngularAcceleration(final float angularAcceleration) {
		this.angularAcceleration += angularAcceleration;
	}

	public void addLocalAcceleration(final VectorF acceleration) {
		this.acceleration.add(getRotation().rotateNew(acceleration));
	}

	@Override
	public VectorF getAcceleration() {
		return acceleration;
	}

	@Override
	public float getAngularAcceleration() {
		return angularAcceleration;
	}

	@Override
	public void integrateStep(final float stepSize) {
		Integrate.step(this, stepSize);
	}

	@Override
	public void setAngularAcceleration(final float angularAcceleration) {
		this.angularAcceleration = angularAcceleration;
	}
}
