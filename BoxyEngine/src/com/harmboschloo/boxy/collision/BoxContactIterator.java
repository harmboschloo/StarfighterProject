package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public interface BoxContactIterator<TBox1 extends Box, TBox2 extends Box> {
	public TBox1 getBox1();

	public TBox2 getBox2();

	public VectorF getRelativePosition();

	public Rotation getRelativeRotation();

	public boolean nextContact();

	public void reset();
}
