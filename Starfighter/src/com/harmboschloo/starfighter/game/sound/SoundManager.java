package com.harmboschloo.starfighter.game.sound;

import com.harmboschloo.boxy.sound.MultiChannelSound;
import com.harmboschloo.boxy.sound.RepeatingSound;
import com.harmboschloo.starfighter.game.Config;

public class SoundManager {
	public static SoundManager instance = null;

	public static SoundManager get() {
		if (instance == null) {
			instance = new SoundManager();
		}
		return instance;
	}

	private final MultiChannelSound laser;
	private final MultiChannelSound jump;
	private final RepeatingSound[] thruster = new RepeatingSound[Config.MAX_NUMBER_OF_PLAYERS];
	private final MultiChannelSound shipHit;
	private boolean mutedAll = false;
	private double volumeAll = 1;

	private SoundManager() {
		laser = new MultiChannelSound(Sounds.laser().getSafeUri().asString(), 5);
		laser.load();
		shipHit = new MultiChannelSound(Sounds.shipHit().getSafeUri()
				.asString(), 5);
		shipHit.load();
		jump = new MultiChannelSound(Sounds.jump().getSafeUri().asString(),
				Config.MAX_NUMBER_OF_PLAYERS);
		jump.load();
		for (int i = 0; i < thruster.length; ++i) {
			thruster[i] = new RepeatingSound(Sounds.thruster().getSafeUri()
					.asString(), 0.75);
			thruster[i].load();
		}
		setVolumeAll(Config.SOUND_VOLUME);
	}

	public MultiChannelSound getJumpSound() {
		return jump;
	}

	public MultiChannelSound getLaserSound() {
		return laser;
	}

	public MultiChannelSound getShipHitSound() {
		return shipHit;
	}

	public RepeatingSound getThrusterSound(final int playerIndex) {
		return thruster[playerIndex];
	}

	public double getVolumeAll() {
		return volumeAll;
	}

	public boolean isMutedAll() {
		return mutedAll;
	}

	public void setMutedAll(final boolean muted) {
		mutedAll = muted;
		laser.setMuted(muted);
		jump.setMuted(muted);
		shipHit.setMuted(muted);
		for (int i = 0; i < thruster.length; ++i) {
			thruster[i].setMuted(muted);
		}
	}

	public void setVolumeAll(final double volume) {
		volumeAll = volume;
		laser.setVolume(volume);
		jump.setVolume(volume);
		shipHit.setVolume(volume);
		for (int i = 0; i < thruster.length; ++i) {
			thruster[i].setVolume(volume);
		}
	}

	public void stopAll() {
		laser.stopAll();
		jump.stopAll();
		shipHit.stopAll();
		stopAllRepeating();
	}

	public void stopAllRepeating() {
		for (int i = 0; i < thruster.length; ++i) {
			thruster[i].stop();
		}
	}

	public void toggleMute() {
		if (mutedAll) {
			setMutedAll(false);
		} else {
			stopAll();
			setMutedAll(true);
		}
	}
}
