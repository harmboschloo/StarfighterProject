package com.harmboschloo.starfighter.game.ai;

import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;
import com.harmboschloo.starfighter.game.object.Ship;
import com.harmboschloo.starfighter.game.object.ShipControls;

public class EvadeAI implements HasGameUpdate {
	public static final int STEER_FRONT = 0;
	public static final int STEER_FRONT_RIGHT = 1;
	public static final int STEER_RIGHT = 2;
	public static final int STEER_BACK_RIGHT = 3;
	public static final int STEER_BACK = 4;
	public static final int STEER_BACK_LEFT = 5;
	public static final int STEER_LEFT = 6;
	public static final int STEER_FRONT_LEFT = 7;
	public static final int STEER_DIRECTION_COUNT = 8;
	public static final int[] preferredSteering = { EvadeAI.STEER_FRONT,
			EvadeAI.STEER_BACK, EvadeAI.STEER_FRONT_RIGHT,
			EvadeAI.STEER_FRONT_LEFT, EvadeAI.STEER_BACK_RIGHT,
			EvadeAI.STEER_BACK_LEFT, EvadeAI.STEER_RIGHT, EvadeAI.STEER_LEFT };

	private final Ship ship;
	private final DroneSensors sensors;
	private State state;

	public EvadeAI(final Ship ship, final float sensorRange) {
		this.ship = ship;
		sensors = (Config.DEBUG_ENABLED ? new DroneDebugSensors(sensorRange,
				ship) : new DroneSensors(sensorRange, ship));
		state = new ControlSpeedState();
		state.enter();
	}

	public DroneSensors getSensors() {
		return sensors;
	}

	public Ship getShip() {
		return ship;
	}

	public void steer(final int direction) {
		final ShipControls controls = ship.getControls();
		switch (direction) {
		case STEER_FRONT:
			controls.up(true);
			break;
		case STEER_FRONT_RIGHT:
			controls.up(true);
			controls.right(true);
			break;
		case STEER_RIGHT:
			controls.right(true);
			break;
		case STEER_BACK_RIGHT:
			controls.down(true);
			controls.left(true);
			break;
		case STEER_BACK:
			controls.down(true);
			break;
		case STEER_BACK_LEFT:
			controls.down(true);
			controls.right(true);
			break;
		case STEER_LEFT:
			controls.left(true);
			break;
		case STEER_FRONT_LEFT:
			controls.up(true);
			controls.left(true);
			break;
		default:
			break;
		}
	}

	@Override
	public void update(final Game game) {
		if (!ship.isEnabled() || ship.isDestroyed()) {
			return;
		}

		ship.getControls().resetAll();
		sensors.update(game);
		final State newState = state.checkStateChange(this, game);
		if (newState != state) {
			state.exit();
			state = newState;
			state.enter();
		}
		state.update(this, game);
	}
}
