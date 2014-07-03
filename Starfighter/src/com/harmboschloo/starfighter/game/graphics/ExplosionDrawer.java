package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.graphics.Drawer;
import com.harmboschloo.starfighter.game.object.Explosion;

public class ExplosionDrawer implements Drawer {
	private final Explosion explosion;

	public ExplosionDrawer(final Explosion explosion) {
		this.explosion = explosion;
	}

	@Override
	public void draw(final Context2d context) {
		final float radius = explosion.getRadius();
		final float fraction = radius / explosion.getMaxRadius();
		context.setGlobalAlpha(1 - fraction * fraction);
		context.setFillStyle(explosion.getColor());
		context.beginPath();
		context.arc(0, 0, radius, 0, 2 * Math.PI);
		context.fill();
	}
}
