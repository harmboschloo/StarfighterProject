package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class DefaultBox implements Box {
	private final VectorF center = new VectorF();
	private final Rotation rotation = new Rotation();
	private final VectorF halfSize = new VectorF();

	@Override
	public VectorF getCenter() {
		return center;
	}

	@Override
	public VectorF getHalfSize() {
		return halfSize;
	}

	@Override
	public Rotation getRotation() {
		return rotation;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
