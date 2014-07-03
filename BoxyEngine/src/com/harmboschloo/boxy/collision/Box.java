package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public interface Box {
	public VectorF getCenter();

	public VectorF getHalfSize();

	public Rotation getRotation();

	public boolean isEnabled();
}
