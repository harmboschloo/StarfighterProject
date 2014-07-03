package com.harmboschloo.starfighter.game.control;

import com.google.gwt.event.dom.client.KeyCodes;

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
 * 
 * @author Harm
 * 
 */
public class RightKeyControlHandler extends KeyControlHandler {
	public static final int[] KEY_CODES = { KeyCodes.KEY_LEFT,
			KeyCodes.KEY_RIGHT, KeyCodes.KEY_UP, KeyCodes.KEY_DOWN, 'M', 'N' };

	public RightKeyControlHandler(final ControlInputHandler controller) {
		super(controller, KEY_CODES);
	}
}
