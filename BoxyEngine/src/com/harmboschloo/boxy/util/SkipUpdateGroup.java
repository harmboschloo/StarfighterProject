package com.harmboschloo.boxy.util;

import java.util.List;

public class SkipUpdateGroup extends UpdateGroup {
	private int skipUpdates = 0;
	private int updateCountdown = 0;

	public SkipUpdateGroup(final int skipUpdates) {
		this.skipUpdates = skipUpdates;
	}

	public SkipUpdateGroup(final int skipUpdates, final int offset) {
		this.skipUpdates = skipUpdates;
	}

	public SkipUpdateGroup(final int skipUpdates, final int offset,
			final List<HasUpdate> elements) {
		super(elements);
		this.skipUpdates = skipUpdates;
		updateCountdown = offset;
	}

	public void setSkipOffset(final int offset) {
		updateCountdown = offset;
	}

	public void setSkipUpdates(final int skipUpdates) {
		this.skipUpdates = skipUpdates;
	}

	@Override
	public void update() {
		if (updateCountdown <= 0) {
			super.update();
			updateCountdown = skipUpdates;
		} else {
			--updateCountdown;
		}
	}
}
