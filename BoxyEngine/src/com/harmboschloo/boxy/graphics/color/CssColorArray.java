package com.harmboschloo.boxy.graphics.color;

import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.util.XYArray;

public class CssColorArray extends XYArray<CssColor> {
	public CssColorArray(final VectorI size) {
		super(size, new CssColor[size.x * size.y]);
	}
}
