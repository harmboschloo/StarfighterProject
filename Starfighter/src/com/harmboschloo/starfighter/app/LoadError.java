package com.harmboschloo.starfighter.app;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class LoadError extends Composite {
	interface Binder extends UiBinder<HTML, LoadError> {
	}

	private static final Binder _binder = GWT.create(Binder.class);

	public LoadError() {
		initWidget(_binder.createAndBindUi(this));
	}
}
