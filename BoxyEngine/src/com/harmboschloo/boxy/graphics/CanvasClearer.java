package com.harmboschloo.boxy.graphics;

import com.google.gwt.canvas.client.Canvas;
import com.harmboschloo.boxy.util.HasUpdate;

public class CanvasClearer implements HasUpdate {
	private final Canvas canvas;

	public CanvasClearer(final Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void update() {
		canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(),
				canvas.getCoordinateSpaceHeight());
	}

}
