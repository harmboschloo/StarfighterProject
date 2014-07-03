package com.harmboschloo.starfighter.game.ai;

import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.Random;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.object.Ship;

public class ControlSpeedState implements State {
	private int slowSteer = -1;

	@Override
	public State checkStateChange(final EvadeAI ai, final Game game) {
		if (ai.getSensors().willCollide()) {
			return new EvadeState();
		} else {
			return this;
		}
	}

	@Override
	public void enter() {
		slowSteer = -1;
	}

	@Override
	public void exit() {
		slowSteer = -1;
	}

	@Override
	public void update(final EvadeAI ai, final Game game) {
		final Ship ship = ai.getShip();
		final float speed = ship.getVelocity().getLength();
		final float maxSpeed = ship.getMaxSpeed();
		if (speed < 0.1f * maxSpeed) {
			if (slowSteer == -1) {
				switch (Random.range(0, 3)) {
				case 0:
					slowSteer = EvadeAI.STEER_FRONT_RIGHT;
					break;
				case 1:
					slowSteer = EvadeAI.STEER_FRONT_LEFT;
					break;
				case 2:
					slowSteer = EvadeAI.STEER_BACK_RIGHT;
					break;
				default:
					slowSteer = EvadeAI.STEER_BACK_LEFT;
					break;
				}
			}

			ai.steer(slowSteer);
		} else {
			slowSteer = -1;

			if (speed > 0.8f * maxSpeed) {
				// reduces speed
				ai.steer(Limit.wrap((int) (4 + 8
						* (ship.getVelocity().getAngle() - ship.getRotation()
								.getAngle()) / (2 * (float) Math.PI)), 0, 8));
			}
		}
	}
}
