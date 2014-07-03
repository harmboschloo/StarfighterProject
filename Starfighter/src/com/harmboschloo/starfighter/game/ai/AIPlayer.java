package com.harmboschloo.starfighter.game.ai;

import com.harmboschloo.starfighter.game.Player;
import com.harmboschloo.starfighter.game.object.Ship;

public class AIPlayer extends EvadeDrone implements Player {
	public AIPlayer(final Ship ship, final float sensorRange) {
		super(ship, sensorRange);
	}
}
