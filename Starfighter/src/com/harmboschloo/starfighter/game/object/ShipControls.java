package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.starfighter.game.control.ControlInputHandler;

public class ShipControls implements ControlInputHandler {
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private float thrust = 0;
	private float rotate = 0;
	private boolean fire = false;
	private boolean jump = false;

	@Override
	public void action1(final boolean active) {
		fire = active;
	}

	@Override
	public void action2(final boolean active) {
		jump = active;
	}

	@Override
	public void down(final boolean active) {
		down = active;
		updateThrust();
	}

	public boolean fire() {
		return fire;
	}

	public boolean jump() {
		return jump;
	}

	@Override
	public void left(final boolean active) {
		left = active;
		updateRotate();
	}

	public void resetAll() {
		up = false;
		down = false;
		right = false;
		left = false;
		thrust = 0;
		rotate = 0;
		fire = false;
		jump = false;
	}

	@Override
	public void right(final boolean active) {
		right = active;
		updateRotate();
	}

	public float rotate() {
		return rotate;
	}

	public float thrust() {
		return thrust;
	}

	@Override
	public void up(final boolean active) {
		up = active;
		updateThrust();
	}

	private void updateRotate() {
		if (left && right) {
			rotate = 0;
		} else if (left) {
			rotate = -1;
		} else if (right) {
			rotate = 1;
		} else {
			rotate = 0;
		}
	}

	private void updateThrust() {
		if (down) {
			thrust = -1;
		} else if (up) {
			thrust = 1;
		} else {
			thrust = 0;
		}
	}
}
