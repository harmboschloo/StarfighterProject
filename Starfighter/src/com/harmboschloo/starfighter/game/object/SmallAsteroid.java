package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class SmallAsteroid extends Asteroid {
	public SmallAsteroid(final Color color) {
		super(color, new VectorI(5, 4), new VectorF(5));
		setLevel(3);
	}
}
