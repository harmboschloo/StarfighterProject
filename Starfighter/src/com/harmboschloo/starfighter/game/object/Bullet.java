package com.harmboschloo.starfighter.game.object;

import com.harmboschloo.boxy.collision.PixelBox;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.graphics.color.Colorizer;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModel;
import com.harmboschloo.boxy.graphics.pixel.PixelStateModel;
import com.harmboschloo.boxy.graphics.pixel.PixelStatePointCountTracker;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.motion.MovingBody;
import com.harmboschloo.starfighter.game.PixelState;

public class Bullet implements MovingBody, PixelBox, GameObject {
	private final Color color;
	private final ColorPixelModel pixels;
	private final PixelStateModel states;
	private final PixelStatePointCountTracker tracker;
	private final VectorF massCenter = new VectorF();
	private final Rotation rotation = new Rotation();
	private final VectorF velocity = new VectorF();
	private float lifetime = -1;
	private int maxDamage = 0;
	private int damage = 0;

	public Bullet(final Color color, final VectorI pixelCount,
			final VectorF pixelOffsetSize, final float maxLifetime) {
		this.color = color;
		pixels = new ColorPixelModel(pixelCount, pixelOffsetSize);
		states = new PixelStateModel(pixelCount);
		states.setAll(PixelState.UNDAMAGED);
		pixels.getColors()
				.setAll(Colorizer.colorize(200, color).makeCssColor());
		tracker = new PixelStatePointCountTracker(PixelState.UNDAMAGED);
		tracker.track(states);
		maxDamage = pixelCount.getArea();
	}

	private void addDamage(final int damage) {
		this.damage += damage;
		if (isDestroyed()) {
			setEnabled(false);
		}
	}

	public void addLifetime(final float lifetime) {
		this.lifetime += lifetime;
	}

	@Override
	public boolean damage(final VectorI pixel) {
		if (states.change(pixel, PixelState.UNDAMAGED, PixelState.CLEAR)) {
			addDamage(1);
			return true;
		}
		return false;
	}

	public int damageAroundPoint(final VectorI point, final int max) {
		final int count = states.changeAroundPoint(point, PixelState.UNDAMAGED,
				PixelState.CLEAR, max);
		addDamage(count);
		return count;
	}

	@Override
	public float getAngularVelocity() {
		return 0;
	}

	@Override
	public VectorF getCenter() {
		return massCenter;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public VectorF getHalfSize() {
		return pixels.getHalfSize();
	}

	public float getLifetime() {
		return lifetime;
	}

	@Override
	public VectorF getMassCenter() {
		return massCenter;
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

	@Override
	public Rotation getRotation() {
		return rotation;
	}

	public PixelStateModel getStates() {
		return states;
	}

	public PixelStatePointCountTracker getUndamagedTracker() {
		return tracker;
	}

	@Override
	public VectorF getVelocity() {
		return velocity;
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
		return (lifetime >= 0);
	}

	@Override
	public void resetDamagedPixels() {
		states.setAll(PixelState.UNDAMAGED);
		damage = 0;
	}

	@Override
	public void setAngularVelocity(final float angularVelocity) {
	}

	@Override
	public void setEnabled(final boolean enabled) {
		if (!enabled) {
			lifetime = -1;
		} else if (lifetime < 0) {
			lifetime = 0;
			resetDamagedPixels();
		}
	}

	public void setLifetime(final float lifetime) {
		this.lifetime = lifetime;
	}
}
