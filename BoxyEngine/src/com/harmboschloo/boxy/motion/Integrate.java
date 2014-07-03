package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class Integrate {
	public static void step(final DynamicBody body, final float stepSize) {
		step(body.getVelocity(), body.getAcceleration(), stepSize);
		body.getAcceleration().set(0, 0);
		body.setAngularVelocity(step(body.getAngularVelocity(),
				body.getAngularAcceleration(), stepSize));
		body.setAngularAcceleration(0);
		step((MovingBody) body, stepSize);
	}

	public static float step(final float value, final float derivative,
			final float stepSize) {
		return (value + derivative * stepSize);
	}

	public static void step(final MovingBody body, final float stepSize) {
		step(body.getMassCenter(), body.getVelocity(), stepSize);
		step(body.getRotation(), body.getAngularVelocity(), stepSize);
	}

	public static void step(final Rotation rotation,
			final float angularVelocity, final float stepSize) {
		rotation.setAngle(Limit.wrapAngle(Integrate.step(rotation.getAngle(),
				angularVelocity, stepSize)));
	}

	public static void step(final VectorF value, final VectorF derivative,
			final float stepSize) {
		value.x += derivative.x * stepSize;
		value.y += derivative.y * stepSize;
	}
}
