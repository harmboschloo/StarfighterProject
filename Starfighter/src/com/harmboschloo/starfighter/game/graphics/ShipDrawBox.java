package com.harmboschloo.starfighter.game.graphics;

import com.harmboschloo.boxy.graphics.DrawManager;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.object.Ship;

public class ShipDrawBox extends SortableDrawBox {
	public ShipDrawBox(final Ship ship, final int drawOrder) {
		super(ship, new PixelStateDrawer(ship.getPixels(), ship.getStates(),
				Config.PIXEL_PADDING_FRACTION, 0.25f), drawOrder);
	}

	@Override
	public void draw(final DrawManager manager) {
		((Ship) getBox()).updateThrustPixels();
		super.draw(manager);
	}
}
