package com.harmboschloo.starfighter.game;

import java.util.List;

import com.harmboschloo.boxy.util.Group;

public class GameUpdateGroup extends Group<HasGameUpdate> implements
		HasGameUpdate {

	public GameUpdateGroup() {
	}

	public GameUpdateGroup(final List<HasGameUpdate> elements) {
		super(elements);
	}

	@Override
	public void update(final Game game) {
		for (final HasGameUpdate component : getElements()) {
			component.update(game);
		}
	}
}
