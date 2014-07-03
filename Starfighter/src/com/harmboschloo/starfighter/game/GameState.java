package com.harmboschloo.starfighter.game;

public class GameState {
	private boolean hasFocus = false;
	private boolean gameOver = false;

	public boolean gameOver() {
		return gameOver;
	}

	public boolean hasFocus() {
		return hasFocus;
	}

	public void setFocus(final boolean hasFocus) {
		this.hasFocus = hasFocus;
	}

	public void setGameOver(final boolean gameOver) {
		this.gameOver = gameOver;
	}
}
