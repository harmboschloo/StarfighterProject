package com.harmboschloo.boxy.graphics;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

public class AxisDrawer implements Drawer {
	private final double size;
	private final CssColor xColor;
	private final CssColor yColor;

	public AxisDrawer(final double size) {
		this(size, CssColor.make(255, 0, 0), CssColor.make(0, 255, 0));
	}

	public AxisDrawer(final double size, final CssColor xColor,
			final CssColor yColor) {
		this.size = size;
		this.xColor = xColor;
		this.yColor = yColor;
	}

	@Override
	public void draw(final Context2d context) {
		context.setStrokeStyle(xColor);
		context.beginPath();
		context.moveTo(0, 0);
		context.lineTo(size, 0);
		context.stroke();
		context.setStrokeStyle(yColor);
		context.beginPath();
		context.moveTo(0, 0);
		context.lineTo(0, size);
		context.stroke();
	}
}
