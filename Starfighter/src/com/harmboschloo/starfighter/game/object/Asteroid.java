package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.collision.PixelBox;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.graphics.color.Colorizer;
import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModel;
import com.harmboschloo.boxy.graphics.pixel.PixelStateModel;
import com.harmboschloo.boxy.math.Random;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.motion.DefaultMovingBody;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;
import com.harmboschloo.starfighter.game.PixelState;

public class Asteroid extends DefaultMovingBody implements PixelBox,
		GameObject, HasGameUpdate {
	private final Color color;
	private final ColorPixelModel pixels;
	private final PixelStateModel states;
	private boolean enabled = true;
	private int maxDamage = 0;
	private int damage = 0;
	private int level = 0;

	public Asteroid(final Color color, final VectorI pixelCount,
			final VectorF pixelOffsetSize) {
		this.color = color;
		pixels = new ColorPixelModel(pixelCount, pixelOffsetSize);
		states = new PixelStateModel(pixelCount);
		initModels();
		maxDamage = pixelCount.getArea() - 4;
	}

	@Override
	public boolean damage(final VectorI pixel) {
		if (states.change(pixel, PixelState.UNDAMAGED, PixelState.DAMAGED)) {
			++damage;
			return true;
		}
		return false;
	}

	@Override
	public VectorF getCenter() {
		return getMassCenter();
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public VectorF getHalfSize() {
		return pixels.getHalfSize();
	}

	public int getLevel() {
		return level;
	}

	@Override
	public VectorI getPixelCount() {
		return pixels.getPixelCount();
	}

	@Override
	public VectorF getPixelOffsetSize() {
		return pixels.getPixelOffsetSize();
	}

	public ColorPixelModel getPixels() {
		return pixels;
	}

	public PixelStateModel getStates() {
		return states;
	}

	private void initModels() {
		final CssColorArray colors = pixels.getColors();
		final VectorI pixel = new VectorI();
		final VectorI pixelCount = pixels.getPixelCount();
		for (pixel.x = 0; pixel.x < pixelCount.x; ++pixel.x) {
			for (pixel.y = 0; pixel.y < pixelCount.y; ++pixel.y) {
				colors.set(pixel,
						Colorizer.colorize(Random.range(200, 255), color)
								.makeCssColor());
				states.set(pixel, PixelState.UNDAMAGED);
			}
		}

		pixel.set(0, 0);
		colors.set(pixel, null);
		states.set(pixel, PixelState.CLEAR);
		pixel.set(pixelCount.x - 1, 0);
		colors.set(pixel, null);
		states.set(pixel, PixelState.CLEAR);
		pixel.set(0, pixelCount.y - 1);
		colors.set(pixel, null);
		states.set(pixel, PixelState.CLEAR);
		pixel.set(pixelCount.x - 1, pixelCount.y - 1);
		colors.set(pixel, null);
		states.set(pixel, PixelState.CLEAR);
	}

	@Override
	public boolean isActive() {
		return (isEnabled() && !isDestroyed());
	}

	@Override
	public boolean isDestroyed() {
		return (damage == maxDamage);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void resetDamagedPixels() {
		states.changeAll(PixelState.DAMAGED, PixelState.UNDAMAGED);
		damage = 0;
	}

	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

	@Override
	public void update(final Game game) {
		if (!isEnabled()) {
			return;
		}

		integrateStep(game.getTimeStep());
		game.wrap(getMassCenter());
	}
}
