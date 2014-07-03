package com.harmboschloo.starfighter.game;

import com.harmboschloo.starfighter.game.object.Ship;

public interface Pilot extends HasGameUpdate {
	public Ship getShip();

	public boolean isActive();

	@Override
	public void update(Game game);
}
