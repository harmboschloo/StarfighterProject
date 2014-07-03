package com.harmboschloo.starfighter.game.ai;

import com.harmboschloo.starfighter.game.Game;

public class EvadeState implements State {
	@Override
	public State checkStateChange(final EvadeAI ai, final Game game) {
		if (!ai.getSensors().willCollide()
				&& ai.getSensors().getSafetyMargin() == 0) {
			return new ControlSpeedState();
		} else {
			return this;
		}
	}

	@Override
	public void enter() {
	}

	@Override
	public void exit() {
	}

	@Override
	public void update(final EvadeAI ai, final Game game) {
		final DroneSensors sensors = ai.getSensors();

		if (sensors.willCollide()) {
			final float[] constraints = sensors.getConstraints();
			assert (constraints.length == EvadeAI.STEER_DIRECTION_COUNT);

			float smallestConstraint = constraints[0];
			for (int i = 1; i < constraints.length; ++i) {
				if (constraints[i] < smallestConstraint) {
					smallestConstraint = constraints[i];
				}
			}

			for (int i = 0; i < EvadeAI.preferredSteering.length; ++i) {
				final int direction = EvadeAI.preferredSteering[i];
				if (constraints[direction] == smallestConstraint) {
					ai.steer(direction);
					break;
				}
			}

			// this will ensure that we are well clear of any possible
			// collisions before leaving the evade state
			sensors.setSafetyMarginByFraction(1);
		} else {
			sensors.decreaseSafetyMarginByFraction(0.01f);
		}
	}
}
