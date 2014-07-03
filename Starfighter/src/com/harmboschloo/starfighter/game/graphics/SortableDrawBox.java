package com.harmboschloo.starfighter.game.graphics;

import com.harmboschloo.boxy.collision.Box;
import com.harmboschloo.boxy.graphics.DrawBox;
import com.harmboschloo.boxy.graphics.DrawManager;
import com.harmboschloo.boxy.graphics.Drawer;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class SortableDrawBox implements DrawBox {
	private final Box box;
	private final Drawer drawer;
	private final int drawOrder;

	public SortableDrawBox(final Box box, final Drawer drawer,
			final int drawOrder) {
		this.box = box;
		this.drawer = drawer;
		this.drawOrder = drawOrder;
	}

	@Override
	public void draw(final DrawManager manager) {
		manager.draw(drawer);
	}

	public Box getBox() {
		return box;
	}

	@Override
	public VectorF getCenter() {
		return box.getCenter();
	}

	public int getDrawOrder() {
		return drawOrder;
	}

	@Override
	public VectorF getHalfSize() {
		return box.getHalfSize();
	}

	@Override
	public Rotation getRotation() {
		return box.getRotation();
	}

	@Override
	public boolean isEnabled() {
		return box.isEnabled();
	}
}
