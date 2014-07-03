package com.harmboschloo.boxy.sound;

import com.google.gwt.media.client.Audio;

public class RepeatingSound {
	private final MultiChannelSound sound;
	private final double changeOver;
	private Audio current = null;

	public RepeatingSound(final String source) {
		this(source, 0.75);
	}

	public RepeatingSound(final String source, final double changeOver) {
		sound = new MultiChannelSound(source, (int) Math.ceil(1 / changeOver));
		this.changeOver = changeOver;
	}

	public double getVolume() {
		return sound.getVolume();
	}

	public boolean isMuted() {
		return sound.isMuted();
	}

	public boolean isStarted() {
		return (current != null);
	}

	public void load() {
		sound.load();
	}

	public void setMuted(final boolean muted) {
		sound.setMuted(muted);
	}

	public void setVolume(final double volume) {
		sound.setVolume(volume);
	}

	public void start() {
		if (!isStarted()) {
			current = sound.play();
		}
	}

	public void stop() {
		if (isStarted()) {
			sound.stopAll();
			current = null;
		}
	}

	public void update() {
		if (isStarted()) {
			if (current.getCurrentTime() / current.getDuration() > changeOver) {
				current = sound.play();
			}
		}
	}
}
