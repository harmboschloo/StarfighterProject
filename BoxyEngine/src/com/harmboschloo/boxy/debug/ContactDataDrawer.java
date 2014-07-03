package com.harmboschloo.boxy.debug;

import java.util.List;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.viewport.HasViewportUpdate;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.util.Group;

public class ContactDataDrawer extends Group<ContactData> implements
		HasViewportUpdate {
	private final CssColor color;

	public ContactDataDrawer(final CssColor color,
			final List<ContactData> contactData) {
		super(contactData);
		this.color = color;
	}

	@Override
	public void update(final Viewport viewport) {
		// TODO: make sure positions are within viewport box
		final Context2d context = viewport.getContext();
		context.setStrokeStyle(color);
		context.setGlobalAlpha(0.5);
		context.beginPath();
		for (final ContactData data : getElements()) {
			context.moveTo(data.position1.x, data.position1.y);
			context.lineTo(data.position2.x, data.position2.y);
		}
		context.stroke();
		context.setGlobalAlpha(1);

	}
}
