package com.harmboschloo.boxy.sound;

import java.util.ArrayList;

import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;

//TODO: fix preloading (IE gives nasty JS errors)
public class MultiChannelSound {
	private final String source;
	private final int maxChannels;
	private final ArrayList<Audio> sounds = new ArrayList<Audio>();
	private boolean alwaysPlay = true;
	private boolean muted = false;
	private double volume = 1;

	public MultiChannelSound(final String source, final int maxChannels) {
		this.source = source;
		this.maxChannels = (Audio.isSupported() ? maxChannels : 0);
	}

	public boolean alwaysPlay() {
		return alwaysPlay;
	}

	public int getMaxChannels() {
		return maxChannels;
	}

	public String getSource() {
		return source;
	}

	public double getVolume() {
		return volume;
	}

	public boolean isMuted() {
		return muted;
	}

	private boolean isReady(final Audio audio) {
		return (audio.getReadyState() != MediaElement.HAVE_NOTHING && audio
				.getNetworkState() != MediaElement.NETWORK_LOADING);
	}

	public void load() {
		load(maxChannels);
	}

	public void load(int channels) {
		if (channels > maxChannels) {
			channels = maxChannels;
		}
		while (sounds.size() < channels) {
			sounds.add(loadNewSound());
		}
	}

	public void loadAll() {
		load(maxChannels);
	}

	private Audio loadNewSound() {
		final Audio sound = Audio.createIfSupported();
		assert (sound != null);
		sound.setSrc(source);
		sound.setPreload(MediaElement.PRELOAD_AUTO);
		sound.setMuted(muted);
		sound.setVolume(volume);
		sound.load();
		return sound;
	}

	public Audio play() {
		if (muted) {
			return null;
		}

		// GWT.log("play");
		for (final Audio sound : sounds) {
			if (sound.hasEnded() || sound.isPaused()) {
				// GWT.log("reuse " + sound);
				sound.play();
				return sound;
			}
		}

		if (sounds.size() < maxChannels) {
			// GWT.log("new");
			final Audio sound = loadNewSound();
			// GWT.log("new " + sound);
			sounds.add(sound);
			sound.play();
			return sound;
		}

		if (alwaysPlay) {
			// GWT.log("alwaysPlay");
			Audio furthest = null;
			for (final Audio audio : sounds) {
				if (furthest == null) {
					furthest = audio;
				} else if (audio.getCurrentTime() > furthest.getCurrentTime()) {
					furthest = audio;
				}
			}

			if (furthest != null && isReady(furthest)) {
				// GWT.log("reset " + furthest);
				// GWT.log("ready state: " + furthest.getReadyState());
				furthest.setCurrentTime(0);
				furthest.play();
				return furthest;
			}
		}

		return null;
	}

	public void setAlwaysPlay(final boolean alwaysPlay) {
		this.alwaysPlay = alwaysPlay;
	}

	public void setMuted(final boolean muted) {
		this.muted = muted;
		for (final Audio sound : sounds) {
			sound.setMuted(muted);
		}
	}

	public void setVolume(final double volume) {
		this.volume = volume;
		for (final Audio sound : sounds) {
			sound.setVolume(volume);
		}
	}

	public void stopAll() {
		for (final Audio audio : sounds) {
			audio.pause();
			if (isReady(audio)) {
				audio.setCurrentTime(0);
			}
		}
	}
}
