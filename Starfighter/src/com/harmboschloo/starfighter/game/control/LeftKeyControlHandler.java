package com.harmboschloo.starfighter.game.control;

/**
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
public class LeftKeyControlHandler extends KeyControlHandler {
	public static final int[] KEY_CODES = { 'D', 'G', 'R', 'F', 'X', 'Z' };

	public LeftKeyControlHandler(final ControlInputHandler controller) {
		super(controller, KEY_CODES);
	}
}
