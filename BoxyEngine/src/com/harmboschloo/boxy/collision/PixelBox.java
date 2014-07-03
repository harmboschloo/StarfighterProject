package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public interface PixelBox extends Box {
	@Override
	public VectorF getCenter();

	@Override
	public VectorF getHalfSize();

	public VectorI getPixelCount();

	public VectorF getPixelOffsetSize();

	@Override
	public Rotation getRotation();

	@Override
	public boolean isEnabled();
}
