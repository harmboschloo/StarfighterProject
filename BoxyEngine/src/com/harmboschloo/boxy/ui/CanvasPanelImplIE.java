package com.harmboschloo.boxy.ui;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;

class CanvasPanelImplIE extends CanvasPanelImpl {
	private final FocusPanel panel = new FocusPanel();

	CanvasPanelImplIE() {
		panel.add(getCanvas());
	}

	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return panel.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return panel.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyDownHandler(final KeyDownHandler handler) {
		return panel.addKeyDownHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(final KeyPressHandler handler) {
		return panel.addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyUpHandler(final KeyUpHandler handler) {
		return panel.addKeyUpHandler(handler);
	}

	@Override
	public int getTabIndex() {
		return panel.getTabIndex();
	}

	@Override
	public Widget getWidget() {
		return panel;
	}

	@Override
	public void setAccessKey(final char key) {
		panel.setAccessKey(key);
	}

	@Override
	public void setFocus(final boolean focused) {
		panel.setFocus(focused);
	}

	@Override
	public void setTabIndex(final int index) {
		panel.setTabIndex(index);
	}
}
