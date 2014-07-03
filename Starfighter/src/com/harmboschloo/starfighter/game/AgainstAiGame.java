package com.harmboschloo.starfighter.game;

import com.harmboschloo.boxy.ui.CanvasAppPanel;

public class AgainstAiGame extends Game {
	private Team droneTeam = null;

	public AgainstAiGame(final CanvasAppPanel gamePanel) {
		super(gamePanel);

		getWrapArea().min.set(0, 0);
		getWrapArea().max.set(1000, 1000);
		getViewportBaseSize().set(500, 500);

		setPVP(false);

		droneTeam = addTeam(false);
	}

	protected void addAsteroid() {
		switch (getAsteroids().size() % 9) {
		case 0:
		case 4:
			addBigAsteroids(1);
			break;
		case 1:
		case 5:
		case 8:
			addMediumAsteroids(1);
			break;
		case 2:
		case 3:
		case 6:
		case 7:
			addSmallAsteroids(1);
			break;
		}
	}

	protected void addAsteroids(final int n) {
		for (int i = 0; i < n; ++i) {
			addAsteroid();
		}
		positionAsteroids();
		animateAsteroids();
	}

	protected void addDrone(final float sensorRange) {
		droneTeam.add(addAIDrone(sensorRange));
	}

	protected void addDrones(final int n, final float sensorRange) {
		for (int i = 0; i < n; ++i) {
			addDrone(sensorRange);
		}
		positionShips();
	}
}
