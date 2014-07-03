package com.harmboschloo.starfighter.game;

import com.harmboschloo.boxy.ui.CanvasAppPanel;
import com.harmboschloo.starfighter.game.object.Asteroid;

public class AITestGame extends Game {

	public AITestGame(final CanvasAppPanel gamePanel) {
		super(gamePanel);

		getWrapArea().min.set(0, 0);
		getWrapArea().max.set(1000, 1000);
		getViewportBaseSize().set(500, 500);

		final Player ai = addAIPlayer(200);
		addHumanPlayer();
		positionShips();

		Asteroid asteroid = addBigAsteroid(nextAsteroidColor());
		asteroid.getCenter().set(ai.getShip().getCenter().x + 30,
				ai.getShip().getCenter().y - 150);
		asteroid.getVelocity().set(-5, 50);

		asteroid = addMediumAsteroid(nextAsteroidColor());
		asteroid.getCenter().set(ai.getShip().getCenter().x + 150,
				ai.getShip().getCenter().y + 250);
		asteroid.getVelocity().set(-30, 0);
	}
}
