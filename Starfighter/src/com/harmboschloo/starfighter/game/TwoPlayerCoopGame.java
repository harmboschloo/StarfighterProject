package com.harmboschloo.starfighter.game;

import com.harmboschloo.boxy.ui.CanvasAppPanel;

public class TwoPlayerCoopGame extends AgainstAiGame {
	public TwoPlayerCoopGame(final CanvasAppPanel gamePanel) {
		super(gamePanel);

		final Team team = addTeam(true);
		team.add(addHumanPlayer());
		team.add(addHumanPlayer());

		addAsteroids(9);
		addDrones(8, 200);
	}
}
