package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public interface GameObject {
	public boolean damage(VectorI pixel);

	public float getAngularVelocity();

	public VectorF getCenter();

	public Color getColor();

	public VectorF getHalfSize();

	public VectorF getMassCenter();

	public Rotation getRotation();

	public VectorF getVelocity();

	public boolean isActive();

	public boolean isDestroyed();

	public boolean isEnabled();

	public void resetDamagedPixels();

	public void setAngularVelocity(float angularVelocity);

	public void setEnabled(boolean enabled);
}
