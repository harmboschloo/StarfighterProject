package com.harmboschloo.starfighter.game.graphics;

import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.object.Bullet;

public class BulletDrawBox extends SortableDrawBox {
	public BulletDrawBox(final Bullet bullet, final int drawOrder) {
		super(bullet, new PixelStateDrawer(bullet.getPixels(),
				bullet.getStates(), Config.PIXEL_PADDING_FRACTION, 0.1f),
				drawOrder);
	}
}
