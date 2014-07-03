package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class MediumAsteroid extends Asteroid {
	public MediumAsteroid(final Color color) {
		super(color, new VectorI(7, 5), new VectorF(10));
		setLevel(2);
	}
}
