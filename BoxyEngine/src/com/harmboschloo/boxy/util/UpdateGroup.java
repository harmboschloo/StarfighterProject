package com.harmboschloo.boxy.util;

import java.util.List;

public class UpdateGroup extends Group<HasUpdate> implements HasUpdate {
	public UpdateGroup() {
	}

	public UpdateGroup(final List<HasUpdate> elements) {
		super(elements);
	}

	@Override
	public void update() {
		for (final HasUpdate element : getElements()) {
			element.update();
		}
	}
}
