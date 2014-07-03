package com.harmboschloo.boxy.util;

import java.util.List;

import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasEnabled;

public class KeyEnabledUpdateGroup extends UnknownUpdateGroup implements
		HasEnabled, HasUpdate {
	private final int toggleEnabledKeyCode;
	private final int updateOnceKeyCode;
	private boolean enabled = true;
	private boolean updateOnce = true;

	public KeyEnabledUpdateGroup(final int toggleEnabledKeyCode,
			final int updateOnceKeyCode,
			final List<? extends HasUpdate> elements) {
		super(elements);
		this.toggleEnabledKeyCode = toggleEnabledKeyCode;
		this.updateOnceKeyCode = updateOnceKeyCode;
	}

	public HandlerRegistration bindTo(final HasKeyPressHandlers hasHandlers) {
		return hasHandlers.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(final KeyPressEvent event) {
				doKeyPress(event);
			}
		});
	}

	public void doKeyPress(final KeyPressEvent event) {
		int keyCode = event.getNativeEvent().getKeyCode();
		if (keyCode == 0) {
			keyCode = Character.toUpperCase(event.getCharCode());
		}

		if (keyCode == toggleEnabledKeyCode) {
			enabled = !enabled;
			event.preventDefault();
		} else if (keyCode == updateOnceKeyCode) {
			updateOnce = true;
			event.preventDefault();
		}
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void update() {
		if (updateOnce) {
			super.update();
			updateOnce = false;
		} else if (enabled) {
			super.update();
		}
	}
}
