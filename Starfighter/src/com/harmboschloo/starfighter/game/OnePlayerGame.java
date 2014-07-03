package com.harmboschloo.starfighter.game;

import com.harmboschloo.boxy.ui.CanvasAppPanel;

public class OnePlayerGame extends AgainstAiGame {
	public OnePlayerGame(final CanvasAppPanel gamePanel) {
		super(gamePanel);

		final Team team = addTeam(true);
		team.add(addHumanPlayer());

		addAsteroids(9);
		addDrones(5, 200);
	}
}
