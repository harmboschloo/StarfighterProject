package com.harmboschloo.starfighter.game;

import java.util.List;

import com.harmboschloo.boxy.util.UnknownGroup;

public class UnknownGameUpdateGroup extends UnknownGroup<HasGameUpdate>
		implements HasGameUpdate {

	public UnknownGameUpdateGroup(final List<? extends HasGameUpdate> elements) {
		super(elements);
	}

	@Override
	public void update(final Game game) {
		for (final HasGameUpdate hasGameUpdate : getElements()) {
			hasGameUpdate.update(game);
		}
	}
}
