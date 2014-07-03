package com.harmboschloo.boxy.util;

import java.util.List;

public class UnknownUpdateGroup extends UnknownGroup<HasUpdate> implements
		HasUpdate {
	public UnknownUpdateGroup(final List<? extends HasUpdate> elements) {
		super(elements);
	}

	@Override
	public void update() {
		for (final HasUpdate element : getElements()) {
			element.update();
		}
	}
}
