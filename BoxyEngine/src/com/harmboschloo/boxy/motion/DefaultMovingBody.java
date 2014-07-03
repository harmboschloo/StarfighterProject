package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.VectorF;

public class DefaultMovingBody extends DefaultBody implements MovingBody {
	private final VectorF velocity = new VectorF();
	private float angularVelocity = 0;

	public void addAngularVelocity(final float angularVelocity) {
		this.angularVelocity += angularVelocity;
	}

	public void addLocalVelocity(final VectorF velocity) {
		this.velocity.add(getRotation().rotateNew(velocity));
	}

	@Override
	public float getAngularVelocity() {
		return angularVelocity;
	}

	@Override
	public VectorF getVelocity() {
		return velocity;
	}

	public void integrateStep(final float stepSize) {
		Integrate.step(this, stepSize);
	}

	@Override
	public void setAngularVelocity(final float angularVelocity) {
		this.angularVelocity = angularVelocity;
	}
}
