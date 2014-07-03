package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class DefaultBody implements Body {
	private final VectorF massCenter = new VectorF();
	private final Rotation rotation = new Rotation();

	@Override
	public VectorF getMassCenter() {
		return massCenter;
	}

	@Override
	public Rotation getRotation() {
		return rotation;
	}
}
