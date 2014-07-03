package com.harmboschloo.boxy.graphics.pixel.text;

public class PixelFont3x5Default implements PixelFont {
	public static final int[] PIXELS_UNKNOWN = { 3, 0, 1, 2, 3, 4, 5, 6, 7, 8,
			9, 10, 11, 12, 13, 14 };
	public static final int[] PIXELS_A = { 3, 1, 3, 5, 6, 7, 8, 9, 11, 12, 14 };
	public static final int[] PIXELS_B = { 3, 0, 1, 3, 5, 6, 7, 9, 11, 12, 13 };
	public static final int[] PIXELS_C = { 3, 1, 3, 5, 6, 9, 11, 13 };
	public static final int[] PIXELS_D = { 3, 0, 1, 3, 5, 6, 8, 9, 11, 12, 13 };
	public static final int[] PIXELS_E = { 3, 0, 1, 2, 3, 6, 7, 9, 12, 13, 14 };
	public static final int[] PIXELS_F = { 3, 0, 1, 2, 3, 6, 7, 9, 12 };
	public static final int[] PIXELS_G = { 3, 1, 3, 5, 6, 9, 11, 13, 14 };
	public static final int[] PIXELS_H = { 3, 0, 2, 3, 5, 6, 7, 8, 9, 11, 12,
			14 };
	public static final int[] PIXELS_I = { 1, 0, 1, 2, 3, 4 };
	public static final int[] PIXELS_J = { 3, 2, 5, 8, 9, 11, 13 };
	public static final int[] PIXELS_K = { 3, 0, 3, 6, 8, 9, 10, 12, 14 };
	public static final int[] PIXELS_L = { 3, 0, 3, 6, 9, 12, 13, 14 };
	public static final int[] PIXELS_M = { 5, 0, 1, 3, 5, 7, 9, 10, 12, 14, 15,
			17, 19, 20, 22, 24 };
	public static final int[] PIXELS_N = { 3, 0, 1, 3, 5, 6, 8, 9, 11, 12, 14 };
	public static final int[] PIXELS_O = { 3, 1, 3, 5, 6, 8, 9, 11, 13 };
	public static final int[] PIXELS_P = { 3, 0, 1, 3, 5, 6, 7, 9, 12 };
	public static final int[] PIXELS_Q = { 3, 1, 3, 5, 6, 8, 9, 11, 13, 14 };
	public static final int[] PIXELS_R = { 3, 0, 1, 3, 5, 6, 7, 9, 11, 12, 14 };
	public static final int[] PIXELS_S = { 3, 1, 2, 3, 6, 7, 8, 11, 12, 13 };
	public static final int[] PIXELS_T = { 3, 0, 1, 2, 4, 7, 10, 13 };
	public static final int[] PIXELS_U = { 3, 0, 2, 3, 5, 6, 8, 9, 11, 13, 14 };
	public static final int[] PIXELS_V = { 3, 0, 2, 3, 5, 6, 8, 9, 11, 13 };
	public static final int[] PIXELS_W = { 5, 0, 4, 5, 9, 10, 12, 14, 15, 17,
			19, 21, 23 };
	public static final int[] PIXELS_X = { 3, 0, 2, 3, 5, 7, 9, 11, 12, 14 };
	public static final int[] PIXELS_Y = { 3, 0, 2, 3, 5, 7, 8, 11, 12, 13, 14 };
	public static final int[] PIXELS_Z = { 3, 0, 1, 2, 5, 7, 9, 12, 13, 14 };
	public static final int[] PIXELS_0 = { 3, 0, 1, 2, 3, 5, 6, 8, 9, 11, 12,
			13, 14 };
	public static final int[] PIXELS_1 = { 3, 0, 1, 4, 7, 10, 12, 13, 14 };
	public static final int[] PIXELS_2 = { 3, 0, 1, 2, 5, 6, 7, 8, 9, 12, 13,
			14 };
	public static final int[] PIXELS_3 = { 3, 0, 1, 2, 5, 6, 7, 8, 11, 12, 13,
			14 };
	public static final int[] PIXELS_4 = { 3, 0, 2, 3, 5, 6, 7, 8, 11, 14 };
	public static final int[] PIXELS_5 = { 3, 0, 1, 2, 3, 6, 7, 8, 11, 12, 13,
			14 };
	public static final int[] PIXELS_6 = { 3, 0, 1, 2, 3, 6, 7, 8, 9, 11, 12,
			13, 14 };
	public static final int[] PIXELS_7 = { 3, 0, 1, 2, 5, 8, 11, 14 };
	public static final int[] PIXELS_8 = { 3, 0, 1, 2, 3, 5, 6, 7, 8, 9, 11,
			12, 13, 14 };
	public static final int[] PIXELS_9 = { 3, 0, 1, 2, 3, 5, 6, 7, 8, 11, 12,
			13, 14 };
	public static final int[] PIXELS_PERIOD = { 1, 4 };
	public static final int[] PIXELS_BRACKET_LEFT = { 2, 1, 2, 4, 6, 9 };
	public static final int[] PIXELS_BRACKET_RIGHT = { 2, 0, 3, 5, 7, 8 };
	public static final int[] PIXELS_SPACE = { 2 };

	private int characterSpacing = 1;

	@Override
	public int getCharacterSpacing() {
		return characterSpacing;
	}

	@Override
	public PixelCharacter getPixelCharacter(final char character) {
		return new PixelCharacter(getPixels(character));
	}

	@Override
	public int getPixelHeight() {
		return 5;
	}

	private int[] getPixels(final char character) {
		switch (character) {
		case 'A':
			return PIXELS_A;
		case 'B':
			return PIXELS_B;
		case 'C':
			return PIXELS_C;
		case 'D':
			return PIXELS_D;
		case 'E':
			return PIXELS_E;
		case 'F':
			return PIXELS_F;
		case 'G':
			return PIXELS_G;
		case 'H':
			return PIXELS_H;
		case 'I':
			return PIXELS_I;
		case 'J':
			return PIXELS_J;
		case 'K':
			return PIXELS_K;
		case 'L':
			return PIXELS_L;
		case 'M':
			return PIXELS_M;
		case 'N':
			return PIXELS_N;
		case 'O':
			return PIXELS_O;
		case 'P':
			return PIXELS_P;
		case 'Q':
			return PIXELS_Q;
		case 'R':
			return PIXELS_R;
		case 'S':
			return PIXELS_S;
		case 'T':
			return PIXELS_T;
		case 'U':
			return PIXELS_U;
		case 'V':
			return PIXELS_V;
		case 'W':
			return PIXELS_W;
		case 'X':
			return PIXELS_X;
		case 'Y':
			return PIXELS_Y;
		case 'Z':
			return PIXELS_Z;
		case '0':
			return PIXELS_0;
		case '1':
			return PIXELS_1;
		case '2':
			return PIXELS_2;
		case '3':
			return PIXELS_3;
		case '4':
			return PIXELS_4;
		case '5':
			return PIXELS_5;
		case '6':
			return PIXELS_6;
		case '7':
			return PIXELS_7;
		case '8':
			return PIXELS_8;
		case '9':
			return PIXELS_9;
		case '.':
			return PIXELS_PERIOD;
		case '(':
			return PIXELS_BRACKET_LEFT;
		case ')':
			return PIXELS_BRACKET_RIGHT;
		case ' ':
			return PIXELS_SPACE;
		default:
			return PIXELS_UNKNOWN;
		}
	}

	@Override
	public int getPixelWidth(final String text) {
		int[] pixels = null;
		final int n = text.length();
		int width = 0;
		for (int i = 0; i < n; ++i) {
			if (pixels != null) {
				width += pixels[0] + characterSpacing;
			}
			pixels = getPixels(text.charAt(i));
		}
		if (pixels != null) {
			width += pixels[0];
		}

		return width;
	}

	@Override
	public void setCharacterSpacing(final int characterSpacing) {
		this.characterSpacing = characterSpacing;
	}
}
