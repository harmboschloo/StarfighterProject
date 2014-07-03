package com.harmboschloo.boxy.graphics;

public interface LayeredDrawManager extends DrawManager {
	@Override
	public void draw(Drawer drawer);

	public void draw(Drawer drawer, int layer);

	@Override
	public void draw(Iterable<? extends Drawer> drawers);

	public void draw(Iterable<? extends Drawer> drawers, int layer);
}
