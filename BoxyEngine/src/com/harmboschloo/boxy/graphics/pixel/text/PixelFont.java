package com.harmboschloo.boxy.graphics.pixel.text;

public interface PixelFont {
	public int getCharacterSpacing();

	PixelCharacter getPixelCharacter(char character);

	public int getPixelHeight();

	public int getPixelWidth(String text);

	public void setCharacterSpacing(int characterSpacing);
}
