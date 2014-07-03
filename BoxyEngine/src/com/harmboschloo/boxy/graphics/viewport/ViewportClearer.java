package com.harmboschloo.boxy.graphics.viewport;

import com.harmboschloo.boxy.math.VectorF;

public class ViewportClearer implements HasViewportUpdate {
	@Override
	public void update(final Viewport viewport) {
		final VectorF center = viewport.getCenter();
		final VectorF halfSize = viewport.getHalfSize();
		viewport.getContext().clearRect(center.x - halfSize.x,
				center.y - halfSize.y, 2 * halfSize.x, 2 * halfSize.y);
	}
}
