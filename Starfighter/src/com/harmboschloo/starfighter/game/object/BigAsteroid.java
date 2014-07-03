package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class BigAsteroid extends Asteroid {
	public BigAsteroid(final Color color) {
		super(color, new VectorI(10, 8), new VectorF(15));
		setLevel(1);
	}
}
