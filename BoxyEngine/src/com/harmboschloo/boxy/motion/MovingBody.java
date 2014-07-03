package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public interface MovingBody extends Body {
	public float getAngularVelocity();

	@Override
	public VectorF getMassCenter();

	@Override
	public Rotation getRotation();

	public VectorF getVelocity();

	public void setAngularVelocity(float angularVelocity);
}
