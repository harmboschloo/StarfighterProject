package com.harmboschloo.starfighter.game;

import com.harmboschloo.starfighter.game.object.Ship;

public interface Player extends Pilot {
	@Override
	public Ship getShip();

	@Override
	public void update(Game game);
}
