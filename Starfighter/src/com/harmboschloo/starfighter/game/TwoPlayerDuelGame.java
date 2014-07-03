package com.harmboschloo.starfighter.game;

import com.harmboschloo.boxy.ui.CanvasAppPanel;

public class TwoPlayerDuelGame extends Game {

	public TwoPlayerDuelGame(final CanvasAppPanel gamePanel) {
		super(gamePanel);

		getWrapArea().min.set(0, 0);
		getWrapArea().max.set(1000, 1000);
		getViewportBaseSize().set(500, 500);

		addBigAsteroids(2);
		addMediumAsteroids(3);
		addSmallAsteroids(4);
		positionAsteroids();
		animateAsteroids();

		setPVP(true);

		Team team = addTeam(true);
		team.add(addHumanPlayer());

		team = addTeam(true);
		team.add(addHumanPlayer());

		positionShips();
	}
}
