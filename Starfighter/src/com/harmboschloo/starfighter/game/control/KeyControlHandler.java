package com.harmboschloo.starfighter.game.control;

import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

/**
 * Player 1 controls:
 * <ul>
 * <li>left: Left-arrow key
 * <li>right: Right-arrow key
 * <li>up: Up-arrow key
 * <li>down: Down-arrow key
 * <li>action 1: Ctrl key
 * <li>action 2: Shift key
 * </ul>
 * Player 2 controls:
 * <ul>
 * <li>left: D key
 * <li>right: G key
 * <li>up: R key
 * <li>down: F key
 * <li>action 1: A key
 * <li>action 2: Z key
 * </ul>
 * 
 * @author Harm
 * 
 */
public class KeyControlHandler implements KeyDownHandler, KeyUpHandler,
		KeyPressHandler {
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	public static final int ACTION1 = 4;
	public static final int ACTION2 = 5;
	public static final int NUMBER_OF_KEY_CODES = 6;
	private final ControlInputHandler handler;
	private final int[] keyCodes;

	public KeyControlHandler(final ControlInputHandler controller,
			final int[] keyCodes) {
		handler = controller;
		this.keyCodes = keyCodes;
		assert (this.keyCodes.length == NUMBER_OF_KEY_CODES);
	}

	public void bindTo(final HasAllKeyHandlers hasKeyHandlers) {
		hasKeyHandlers.addKeyDownHandler(this);
		hasKeyHandlers.addKeyUpHandler(this);
		hasKeyHandlers.addKeyPressHandler(this);
	}

	private boolean handleKeyPress(final int keyCode) {
		for (final int code : keyCodes) {
			if (code == keyCode) {
				return true;
			}
		}
		return false;
	}

	private boolean handleKeyUpDown(final int keyCode, final boolean active) {
		boolean handled = false;

		if (keyCode == keyCodes[LEFT]) {
			handler.left(active);
			handled = true;
		}
		if (keyCode == keyCodes[RIGHT]) {
			handler.right(active);
			handled = true;
		}
		if (keyCode == keyCodes[UP]) {
			handler.up(active);
			handled = true;
		}
		if (keyCode == keyCodes[DOWN]) {
			handler.down(active);
			handled = true;
		}
		if (keyCode == keyCodes[ACTION1]) {
			handler.action1(active);
			handled = true;
		}
		if (keyCode == keyCodes[ACTION2]) {
			handler.action2(active);
			handled = true;
		}

		return handled;
	}

	@Override
	public void onKeyDown(final KeyDownEvent event) {
		if (handleKeyUpDown(event.getNativeKeyCode(), true)) {
			event.preventDefault();
		}
	}

	@Override
	public void onKeyPress(final KeyPressEvent event) {
		int keyCode = event.getNativeEvent().getKeyCode();
		if (keyCode == 0) {
			keyCode = Character.toUpperCase(event.getCharCode());
		}
		if (handleKeyPress(keyCode)) {
			event.preventDefault();
		}
	}

	@Override
	public void onKeyUp(final KeyUpEvent event) {
		if (handleKeyUpDown(event.getNativeKeyCode(), false)) {
			event.preventDefault();
		}
	}
}
