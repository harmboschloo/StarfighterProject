package com.harmboschloo.boxy.graphics;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.util.HasUpdate;

public class CanvasFiller implements HasUpdate {
	private final Canvas canvas;
	private final CssColor fillColor;

	public CanvasFiller(final Canvas canvas, final CssColor fillColor) {
		this.canvas = canvas;
		this.fillColor = fillColor;
	}

	@Override
	public void update() {
		canvas.getContext2d().setFillStyle(fillColor);
		canvas.getContext2d().fillRect(0, 0, canvas.getCoordinateSpaceWidth(),
				canvas.getCoordinateSpaceHeight());
	}

}
