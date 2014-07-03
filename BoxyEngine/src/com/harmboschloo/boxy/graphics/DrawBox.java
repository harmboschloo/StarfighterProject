package com.harmboschloo.boxy.graphics;

import com.harmboschloo.boxy.collision.Box;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public interface DrawBox extends Box {
	public void draw(DrawManager manager);

	@Override
	public VectorF getCenter();

	@Override
	public VectorF getHalfSize();

	@Override
	public Rotation getRotation();
}
