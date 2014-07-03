package com.harmboschloo.starfighter.game;

import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.graphics.pixel.text.PixelFont;
import com.harmboschloo.boxy.graphics.pixel.text.PixelFont5x5Default;
import com.harmboschloo.boxy.graphics.pixel.text.PixelFont5x5Monospace;
import com.harmboschloo.boxy.util.Parse;

public class Config {
	public static final String VERSION = "0.0";
	public static final boolean SQUARE_VIEWPORTS = false;
	// TODO: public static final boolean THRUST_DAMAGE_ENABLED = true;
	public static final int MAX_NUMBER_OF_PLAYERS = 2;
	public static final Color[] PLAYER_COLORS = { new Color(255, 0, 0),
			new Color(0, 255, 0) };
	public static final Color[] DRONE_COLORS = { new Color(150, 150, 200),
			new Color(00, 150, 255), new Color(0, 255, 255),
			new Color(255, 0, 255), new Color(255, 255, 0) };
	public static final Color[] ASTEROID_COLORS = { new Color(90, 60, 30),
			new Color(90, 70, 50), new Color(90, 80, 70) };
	public static final Color[] BACKGROUND_GRID_COLORS = {
			new Color(75, 150, 225), new Color(38, 75, 113),
			new Color(25, 50, 75), new Color(38, 75, 113),
			new Color(75, 150, 225) };
	// public static final Color[] BACKGROUND_GRID_COLORS = {
	// new Color(50, 100, 150), new Color(25, 50, 75) };
	public static final CssColor THRUST_COLOR = CssColor.make(255, 255, 0);
	public static final CssColor SCREEN_TEXT_COLOR = CssColor.make(100, 150,
			255);
	public static final PixelFont FONT = new PixelFont5x5Default();
	public static final PixelFont FONT_MONOSPACE = new PixelFont5x5Monospace();
	// public static final CssColor LOSE_TEXT_COLOR = CssColor.make(255, 0, 0);
	// public static final CssColor WIN_TEXT_COLOR = CssColor.make(0, 255, 0);
	public static final float PIXEL_PADDING_FRACTION = 0.03f;
	public static final int NUMBER_OF_VISIBLE_GRID_LINES = 5;
	public static final float JUMP_SEQUENCE_TIME = 0.6f;
	public static final double SOUND_VOLUME = 0.5;
	public static final boolean PARAMETERS_ENABLED = false;
	public static final boolean DEBUG_ENABLED = false;

	private static final float TIME_STEP = 1 / 60f;
	private static float timeStep = TIME_STEP;
	private static boolean drawBoxes = false;
	private static boolean drawAxis = false;
	private static boolean drawContacts = false;

	@SuppressWarnings("unused")
	public static boolean drawAxis() {
		return (PARAMETERS_ENABLED && drawAxis);
	}

	@SuppressWarnings("unused")
	public static boolean drawBoxes() {
		return (PARAMETERS_ENABLED && drawBoxes);
	}

	@SuppressWarnings("unused")
	public static boolean drawContacts() {
		return (PARAMETERS_ENABLED && drawContacts);
	}

	public static float getTimeStep() {
		return (PARAMETERS_ENABLED ? timeStep : TIME_STEP);
	}

	@SuppressWarnings("unused")
	public static boolean parseToken(final String token) {
		if (!PARAMETERS_ENABLED || token == null) {
			return false;
		}

		final String[] keyValue = token.split(":");
		if (keyValue == null || keyValue.length < 2) {
			return false;
		}

		final String key = keyValue[0];
		final String value = keyValue[1];

		if (key.equals("dt")) {
			timeStep = Parse.parseFloat(value, timeStep);
		} else if (key.equals("db")) {
			drawBoxes = Parse.parseIntBoolean(value, drawBoxes);
		} else if (key.equals("da")) {
			drawAxis = Parse.parseIntBoolean(value, drawAxis);
		} else if (key.equals("dc")) {
			drawContacts = Parse.parseIntBoolean(value, drawContacts);
		} else {
			return false;
		}

		return true;
	}

	@SuppressWarnings("unused")
	public static void parseTokens(final String tokens) {
		if (!PARAMETERS_ENABLED || tokens == null) {
			return;
		}

		// TODO: check Locale influence
		tokens.toLowerCase();

		final String[] tokenArray = tokens.split("/");

		for (final String token : tokenArray) {
			parseToken(token);
		}
	}

	public static void setDrawAxis(final boolean drawAxis) {
		if (PARAMETERS_ENABLED) {
			Config.drawAxis = drawAxis;
		}
	}

	public static void setDrawBoxes(final boolean drawBoxes) {
		if (PARAMETERS_ENABLED) {
			Config.drawBoxes = drawBoxes;
		}
	}

	public static void setDrawContacts(final boolean drawContacts) {
		if (PARAMETERS_ENABLED) {
			Config.drawContacts = drawContacts;
		}
	}

	public static void setTimeStep(final float timeStep) {
		if (PARAMETERS_ENABLED) {
			Config.timeStep = timeStep;
		}
	}
}
