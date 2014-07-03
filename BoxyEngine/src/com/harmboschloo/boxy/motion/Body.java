package com.harmboschloo.boxy.motion;

import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public interface Body {
	public VectorF getMassCenter();

	public Rotation getRotation();
}
