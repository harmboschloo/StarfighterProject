package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public interface ShipPixelData {
	public int[] getColors();

	public VectorI getPixelCount();

	public VectorF getPixelOffsetSize();

	public int[] getPixels();

	public int[] getRotateLeftPixels();

	public int[] getRotateRightPixels();

	public int[] getThrustBackPixels();

	public int[] getThrustForwardPixels();

	public int[] getThrustPixels();
}
