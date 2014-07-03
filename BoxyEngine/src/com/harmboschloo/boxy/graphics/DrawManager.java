package com.harmboschloo.boxy.graphics;

public interface DrawManager {
	public void draw(Drawer drawer);

	public void draw(Iterable<? extends Drawer> drawers);
}
