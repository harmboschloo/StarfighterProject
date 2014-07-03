package com.harmboschloo.boxy.graphics.pixel;

import com.harmboschloo.boxy.graphics.DrawBox;
import com.harmboschloo.boxy.graphics.DrawManager;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class ColorPixelModelDrawBox implements DrawBox {
	private final VectorF center = new VectorF();
	private final Rotation rotation = new Rotation();
	private final ColorPixelModelDrawer drawer;
	private boolean active = true;

	public ColorPixelModelDrawBox(final ColorPixelModel pixels,
			final float paddingPercentage, final int layer) {
		drawer = new ColorPixelModelDrawer(pixels, paddingPercentage);
	}

	@Override
	public void draw(final DrawManager manager) {
		manager.draw(drawer);
	}

	@Override
	public VectorF getCenter() {
		return center;
	}

	@Override
	public VectorF getHalfSize() {
		return drawer.getPixels().getHalfSize();
	}

	@Override
	public Rotation getRotation() {
		return rotation;
	}

	@Override
	public boolean isEnabled() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
	}
}
