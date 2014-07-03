package com.harmboschloo.starfighter.game.graphics;

import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.object.Asteroid;

public class AsteroidDrawBox extends SortableDrawBox {
	public AsteroidDrawBox(final Asteroid asteroid, final int drawOrder) {
		super(asteroid, new PixelStateDrawer(asteroid.getPixels(),
				asteroid.getStates(), Config.PIXEL_PADDING_FRACTION, 0.15f),
				drawOrder);
	}
}
