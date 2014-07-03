package com.harmboschloo.boxy.graphics.pixel.text;

public class PixelCharacterIterator {
	private final String text;
	private final PixelFont font;
	private int index = -1;
	private int position = 0;
	private PixelCharacter character = null;

	public PixelCharacterIterator(final String text, final PixelFont font) {
		this.text = text;
		this.font = font;
	}

	public char getChar() {
		return text.charAt(index);
	}

	public int getPixelPosition() {
		return position;
	}

	public boolean hasNext() {
		return (index < text.length() - 1);
	}

	public PixelCharacter next() {
		++index;

		if (character != null) {
			position += character.getPixelWidth() + font.getCharacterSpacing();
		}

		character = font.getPixelCharacter(getChar());
		return character;
	}
}
