package com.harmboschloo.boxy.graphics.pixel.text;

public class PixelFont5x5Monospace extends PixelFont5x5Default {
	public static final int[] PIXELS_I = { 5, 1, 2, 3, 7, 12, 17, 21, 22, 23 };
	public static final int[] PIXELS_PERIOD = { 5, 22 };
	public static final int[] PIXELS_COLON = { 5, 2, 22 };
	public static final int[] PIXELS_SINGLE_QUOTE = { 5, 2, 7 };
	public static final int[] PIXELS_BRACKET_LEFT = { 5, 3, 4, 12, 17, 23 };
	public static final int[] PIXELS_BRACKET_RIGHT = { 5, 2, 8, 13, 18, 22 };
	public static final int[] PIXELS_DASH = { 5, 11, 12, 13 };
	public static final int[] PIXELS_VERTICAL_LINE = { 5, 2, 7, 12, 17, 22 };
	public static final int[] PIXELS_SPACE = { 5 };

	@Override
	public int[] getPixels(final char character) {
		switch (character) {
		case 'I':
			return PIXELS_I;
		case '.':
			return PIXELS_PERIOD;
		case ':':
			return PIXELS_COLON;
		case '\'':
			return PIXELS_SINGLE_QUOTE;
		case '(':
			return PIXELS_BRACKET_LEFT;
		case ')':
			return PIXELS_BRACKET_RIGHT;
		case '-':
			return PIXELS_DASH;
		case '|':
			return PIXELS_VERTICAL_LINE;
		case ' ':
			return PIXELS_SPACE;
		default:
			return super.getPixels(character);
		}
	}
}
