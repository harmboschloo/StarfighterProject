package com.harmboschloo.starfighter.game;

import com.harmboschloo.starfighter.game.object.Ship;

public class HumanPlayer implements Player {
	private final Ship ship;

	public HumanPlayer(final Ship ship) {
		this.ship = ship;
	}

	@Override
	public Ship getShip() {
		return ship;
	}

	@Override
	public boolean isActive() {
		return ship.isActive();
	}

	@Override
	public void update(final Game game) {
		ship.update(game);
	}
}
