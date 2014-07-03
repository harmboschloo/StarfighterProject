package com.harmboschloo.boxy.ui;

import com.google.gwt.user.client.ui.Anchor;

public class EmptyAnchor extends Anchor {

	public EmptyAnchor() {
		Empty.widget(this);
	}

	public EmptyAnchor(final String href) {
		setHref(href);
		Empty.widget(this);
	}

	public EmptyAnchor(final String href, final String target) {
		setHref(href);
		setTarget(target);
		Empty.widget(this);
	}
}
