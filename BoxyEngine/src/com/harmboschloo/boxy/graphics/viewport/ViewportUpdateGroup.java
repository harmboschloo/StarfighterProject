package com.harmboschloo.boxy.graphics.viewport;

import java.util.List;

import com.harmboschloo.boxy.util.Group;

public class ViewportUpdateGroup extends Group<HasViewportUpdate> implements
		HasViewportUpdate {
	public ViewportUpdateGroup() {
	}

	public ViewportUpdateGroup(final List<HasViewportUpdate> elements) {
		super(elements);
	}

	@Override
	public void update(final Viewport viewport) {
		for (final HasViewportUpdate element : getElements()) {
			element.update(viewport);
		}
	}
}
