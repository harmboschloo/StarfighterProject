package com.harmboschloo.boxy.graphics.viewport;

import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.util.HasUpdate;

public class ViewportCenterController implements HasUpdate {
	private final Viewport viewport;
	private final VectorF follow;

	public ViewportCenterController(final Viewport viewport,
			final VectorF follow) {
		this.viewport = viewport;
		this.follow = follow;
	}

	@Override
	public void update() {
		viewport.getCenter().copy(follow);
	}
}
