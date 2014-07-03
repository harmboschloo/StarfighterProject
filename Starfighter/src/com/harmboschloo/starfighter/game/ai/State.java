package com.harmboschloo.starfighter.game.ai;

import com.harmboschloo.starfighter.game.Game;

public interface State {
	public State checkStateChange(EvadeAI ai, Game game);

	public void enter();

	public void exit();

	public void update(EvadeAI ai, Game game);
}
