package com.harmboschloo.starfighter.game.graphics;

import com.harmboschloo.starfighter.game.object.Explosion;

public class ExplosionDrawBox extends SortableDrawBox {

	public ExplosionDrawBox(final Explosion explosion, final int drawOrder) {
		super(explosion, new ExplosionDrawer(explosion), drawOrder);
	}
}
