package com.harmboschloo.starfighter.game.object;

import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.collision.Box;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.motion.Integrate;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;

public class Explosion implements HasGameUpdate, Box {
	private static final Rotation rotation = new Rotation();
	private final VectorF center = new VectorF();
	private final VectorF velocity = new VectorF();
	private final VectorF halfSize = new VectorF();
	private CssColor color;
	private float lifetime = -1;
	private float maxLifetime = 0.5f;
	private float maxRadius = 10.f;

	public Explosion(final VectorF center, final VectorF velocity,
			final CssColor color) {
		this.center.copy(center);
		this.velocity.copy(velocity);
		this.color = color;
	}

	public void addLifetime(final float lifetime) {
		this.lifetime += lifetime;
	}

	@Override
	public VectorF getCenter() {
		return center;
	}

	public CssColor getColor() {
		return color;
	}

	@Override
	public VectorF getHalfSize() {
		return halfSize;
	}

	public float getLifetime() {
		return lifetime;
	}

	public float getMaxLifetime() {
		return maxLifetime;
	}

	public float getMaxRadius() {
		return maxRadius;
	}

	public float getRadius() {
		return halfSize.x;
	}

	@Override
	public Rotation getRotation() {
		return rotation;
	}

	public VectorF getVelocity() {
		return velocity;
	}

	public boolean isActive() {
		return (lifetime >= 0);
	}

	@Override
	public boolean isEnabled() {
		return isActive();
	}

	public void setActive(final boolean active) {
		if (!active) {
			lifetime = -1;
		} else if (lifetime < 0) {
			lifetime = 0;
			setRadius(0);
		}
	}

	public void setColor(final CssColor color) {
		this.color = color;
	}

	public void setLifetime(final float lifetime) {
		this.lifetime = lifetime;
	}

	public void setMaxLifetime(final float maxLifetime) {
		this.maxLifetime = maxLifetime;
	}

	public void setMaxRadius(final float maxRadius) {
		this.maxRadius = maxRadius;
	}

	public void setRadius(final float radius) {
		halfSize.set(radius);
	}

	@Override
	public void update(final Game game) {
		if (isActive()) {
			lifetime += game.getTimeStep();
			if (lifetime > maxLifetime) {
				setActive(false);
			} else {
				setRadius((float) (Math.sqrt(lifetime / maxLifetime) * maxRadius));
				Integrate.step(center, velocity, game.getTimeStep());
				game.getWrapArea().wrap(center);
			}
		}
	}
}
