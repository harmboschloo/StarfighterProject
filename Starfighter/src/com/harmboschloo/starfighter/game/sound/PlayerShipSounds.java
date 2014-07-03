package com.harmboschloo.starfighter.game.sound;

import com.harmboschloo.boxy.sound.MultiChannelSound;
import com.harmboschloo.boxy.sound.RepeatingSound;

public class PlayerShipSounds implements ShipSounds {
	private final MultiChannelSound laser;
	private final MultiChannelSound hit;
	private final RepeatingSound thruster;
	private final MultiChannelSound jump;

	public PlayerShipSounds(final int playerIndex) {
		final SoundManager manager = SoundManager.get();
		laser = manager.getLaserSound();
		hit = manager.getShipHitSound();
		thruster = manager.getThrusterSound(playerIndex);
		jump = manager.getJumpSound();
	}

	@Override
	public void playHit() {
		hit.play();
	}

	@Override
	public void playJump() {
		jump.play();
	}

	@Override
	public void playLaser() {
		laser.play();
	}

	@Override
	public void playThruster(final boolean play) {
		if (play) {
			if (!thruster.isStarted()) {
				thruster.start();
			} else {
				thruster.update();
			}
		} else {
			thruster.stop();
		}
	}
}
