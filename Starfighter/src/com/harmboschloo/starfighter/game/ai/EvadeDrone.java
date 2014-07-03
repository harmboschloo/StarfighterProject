package com.harmboschloo.starfighter.game.ai;

import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.Pilot;
import com.harmboschloo.starfighter.game.object.Ship;

public class EvadeDrone implements Pilot {
	private final EvadeAI ai;

	public EvadeDrone(final Ship ship, final float sensorRange) {
		ai = new EvadeAI(ship, sensorRange);
	}

	public EvadeAI getAI() {
		return ai;
	}

	@Override
	public Ship getShip() {
		return ai.getShip();
	}

	@Override
	public boolean isActive() {
		return getShip().isActive();
	}

	@Override
	public void update(final Game game) {
		ai.update(game);
		ai.getShip().update(game);
	}

}
