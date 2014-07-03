package com.harmboschloo.starfighter.game.object;

public class ShipStats {
	private final int maxDamage;
	private int totalDamage;

	public ShipStats(final int maxDamage) {
		this.maxDamage = maxDamage;
	}

	public void addDamage(final int damage) {
		totalDamage += damage;
	}

	public int getMaxDamage() {
		return maxDamage;
	}

	public int getTotalDamage() {
		return totalDamage;
	}

	public void reset() {
		totalDamage = 0;
	}

	public void resetDamage() {
		totalDamage = 0;
	}
}
