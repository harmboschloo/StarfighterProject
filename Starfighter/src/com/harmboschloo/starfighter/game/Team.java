package com.harmboschloo.starfighter.game;

import com.harmboschloo.boxy.util.Group;

public class Team extends Group<Pilot> {
	private boolean playerTeam = false;
	private boolean active = false;

	public Team() {
	}

	public Team(final boolean playerTeam) {
		this.playerTeam = playerTeam;
	}

	public boolean checkActive() {
		active = false;
		for (final Pilot pilot : getElements()) {
			if (pilot.isActive()) {
				active = true;
				break;
			}
		}
		return active;
	}

	public boolean isActive() {
		return active;
	}

	public boolean isPlayerTeam() {
		return playerTeam;
	}

	public void setPlayerTeam(final boolean playerTeam) {
		this.playerTeam = playerTeam;
	}
}
