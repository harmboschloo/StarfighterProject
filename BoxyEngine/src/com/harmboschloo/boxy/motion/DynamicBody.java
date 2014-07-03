package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public interface DynamicBody extends MovingBody {
	public VectorF getAcceleration();

	public float getAngularAcceleration();

	@Override
	public float getAngularVelocity();

	@Override
	public VectorF getMassCenter();

	@Override
	public Rotation getRotation();

	@Override
	public VectorF getVelocity();

	public void setAngularAcceleration(float angularAcceleration);

	@Override
	public void setAngularVelocity(float angularVelocity);
}
