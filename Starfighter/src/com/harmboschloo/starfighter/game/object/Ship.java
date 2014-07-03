package com.harmboschloo.starfighter.game.object;

import java.util.ArrayList;
import java.util.Collection;

import com.harmboschloo.boxy.collision.PixelBox;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.graphics.color.Colorizer;
import com.harmboschloo.boxy.graphics.color.CssColorArray;
import com.harmboschloo.boxy.graphics.pixel.ColorPixelModel;
import com.harmboschloo.boxy.graphics.pixel.PixelStateModel;
import com.harmboschloo.boxy.graphics.pixel.PixelStatePointCountTracker;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.motion.DefaultDynamicBody;
import com.harmboschloo.boxy.motion.Integrate;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;
import com.harmboschloo.starfighter.game.PixelState;
import com.harmboschloo.starfighter.game.sound.ShipSounds;
import com.harmboschloo.starfighter.game.sound.SoundManager;

public class Ship extends DefaultDynamicBody implements PixelBox, GameObject,
		HasGameUpdate {
	public static final int READY_TO_JUMP = 0;
	public static final int STARTING_JUMP = 1;
	public static final int ENDING_JUMP = 2;
	private float maxSpeed = 0;
	private float maxAngularVelocity = 0;
	private float thrustAcceleration = 0;
	private float rotateAcceleration = 0;
	private float rotateAngularAcceleration = 0;
	private float performanceDamageGain = 0;

	private boolean enabled = true;

	private float boxOffsetOriginal = 0;
	private float boxOffset = 0;
	private final VectorF boxCenter = new VectorF();

	private final VectorI bulletPixelCountSize = new VectorI();
	private final VectorF bulletPixelOffsetSize = new VectorF();
	private float bulletFireDelay = 0;
	private float bulletSpeedIncrease = 0;
	private float bulletMaxLifetime = 0;
	private float lastFiredTime = 0;
	private final ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	private int jumpStatus = READY_TO_JUMP;
	private float jumpRange = 100;
	private float jumpSequenceTime = 1;
	private float jumpProgress = 0;
	private final VectorF jumpScale = new VectorF();

	private final Color color;
	private final ShipPixelData pixelData;
	private final ColorPixelModel pixels;
	private final PixelStateModel states;
	private final PixelStatePointCountTracker tracker;
	private final ShipControls controls = new ShipControls();
	private final ShipStats stats;
	private final ShipSounds sounds;

	public Ship(final Color color, final ShipPixelData pixelData,
			final ShipSounds sounds) {
		this.color = color;
		this.pixelData = pixelData;
		this.sounds = sounds;
		pixels = new ColorPixelModel(pixelData.getPixelCount(),
				pixelData.getPixelOffsetSize());
		states = new PixelStateModel(pixelData.getPixelCount());
		initModels(pixelData);
		tracker = new PixelStatePointCountTracker(PixelState.UNDAMAGED);
		tracker.track(states);
		stats = new ShipStats(pixelData.getPixels().length);
	}

	@Override
	public boolean damage(final VectorI pixel) {
		if (states.change(pixel, PixelState.UNDAMAGED, PixelState.DAMAGED)) {
			stats.addDamage(1);
			sounds.playHit();
			return true;
		}
		return false;
	}

	public int damageAroundPoint(final VectorI point, final int max) {
		final int count = states.changeAroundPoint(point, PixelState.UNDAMAGED,
				PixelState.DAMAGED, max);
		if (count > 0) {
			stats.addDamage(count);
			sounds.playHit();
		}
		return count;
	}

	private Bullet findBullet(final Game game) {
		// find dead bullet to use
		for (final Bullet bullet : bullets) {
			if (!bullet.isEnabled()) {
				return bullet;
			}
		}

		final Bullet bullet = new Bullet(color, bulletPixelCountSize,
				bulletPixelOffsetSize, bulletMaxLifetime);
		bullets.add(bullet);
		game.getGraphics().addBulletDrawBox(bullet);

		return bullet;
	}

	private void fireBullet(final Bullet bullet) {
		lastFiredTime = 0;
		bullet.getMassCenter().set(6, 0);
		getRotation().rotate(bullet.getMassCenter());
		bullet.getMassCenter().add(getMassCenter());
		bullet.getRotation().copy(getRotation());
		bullet.getVelocity().set(bulletSpeedIncrease, 0);
		bullet.getRotation().rotate(bullet.getVelocity());
		bullet.getVelocity().add(getVelocity());
		bullet.setEnabled(true);

		SoundManager.get().getLaserSound().play();
	}

	public float getBoxOffset() {
		return boxOffset;
	}

	public float getBulletFireDelay() {
		return bulletFireDelay;
	}

	public float getBulletMaxLifetime() {
		return bulletMaxLifetime;
	}

	public VectorI getBulletPixelCount() {
		return bulletPixelCountSize;
	}

	public VectorF getBulletPixelOffsetSize() {
		return bulletPixelOffsetSize;
	}

	public Collection<Bullet> getBullets() {
		return bullets;
	}

	public float getBulletSpeedIncrease() {
		return bulletSpeedIncrease;
	}

	@Override
	public VectorF getCenter() {
		return boxCenter;
	}

	@Override
	public Color getColor() {
		return color;
	}

	public ShipControls getControls() {
		return controls;
	}

	@Override
	public VectorF getHalfSize() {
		return pixels.getHalfSize();
	}

	public float getJumpProgress() {
		return jumpProgress;
	}

	public float getJumpRange() {
		return jumpRange;
	}

	public VectorF getJumpScale() {
		return jumpScale;
	}

	public float getJumpSequenceTime() {
		return jumpSequenceTime;
	}

	public int getJumpStatus() {
		return jumpStatus;
	}

	public float getLastFiredTime() {
		return lastFiredTime;
	}

	public float getMaxAngularVelocity() {
		return maxAngularVelocity;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public float getPerformanceDamageGain() {
		return performanceDamageGain;
	}

	@Override
	public VectorI getPixelCount() {
		return pixels.getPixelCount();
	}

	public ShipPixelData getPixelData() {
		return pixelData;
	}

	@Override
	public VectorF getPixelOffsetSize() {
		return pixels.getPixelOffsetSize();
	}

	public ColorPixelModel getPixels() {
		return pixels;
	}

	public float getRotateAcceleration() {
		return rotateAcceleration;
	}

	public PixelStateModel getStates() {
		return states;
	}

	public ShipStats getStats() {
		return stats;
	}

	public float getThrustAcceleration() {
		return thrustAcceleration;
	}

	public PixelStatePointCountTracker getUndamagedTracker() {
		return tracker;
	}

	private void initModels(final ShipPixelData data) {
		final CssColorArray colors = pixels.getColors();
		colors.setAll(null);
		states.setAll(PixelState.CLEAR);

		final int[] pixelData = data.getPixels();
		final int[] colorData = data.getColors();
		assert (pixelData.length == colorData.length);

		int n = pixelData.length;
		for (int i = 0; i < n; ++i) {
			final int pixelIndex = pixelData[i];
			colors.set(pixelIndex, Colorizer.colorize(colorData[i], color)
					.makeCssColor());
			states.set(pixelIndex, PixelState.UNDAMAGED);
		}

		final int[] thrustPixels = data.getThrustPixels();
		n = thrustPixels.length;
		for (int i = 0; i < n; ++i) {
			colors.set(thrustPixels[i], Config.THRUST_COLOR);
		}
	}

	@Override
	public boolean isActive() {
		return (isEnabled() && !isDestroyed());
	}

	@Override
	public boolean isDestroyed() {
		return (stats.getTotalDamage() >= stats.getMaxDamage());
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public boolean isJumping() {
		return (jumpStatus != READY_TO_JUMP);
	}

	@Override
	public void resetDamagedPixels() {
		states.changeAll(PixelState.DAMAGED, PixelState.UNDAMAGED);
		stats.resetDamage();
	}

	public void resetDamagedPixels(final int max) {
		final int n = states.getCount();
		int count = 0;
		for (int i = 0; i < n && count < max; ++i) {
			if (states.change(i, PixelState.DAMAGED, PixelState.UNDAMAGED)) {
				stats.addDamage(-1);
				++count;
			}
		}
	}

	public void setBoxOffset(final float boxOffset) {
		this.boxOffset = boxOffset;
		boxOffsetOriginal = boxOffset;
	}

	public void setBulletFireDelay(final float bulletFireDelay) {
		this.bulletFireDelay = bulletFireDelay;
	}

	public void setBulletMaxLifetime(final float bulletMaxLifetime) {
		this.bulletMaxLifetime = bulletMaxLifetime;
	}

	public void setBulletsEnabled(final boolean enabled) {
		for (final Bullet bullet : bullets) {
			bullet.setEnabled(enabled);
		}
	}

	public void setBulletSpeedIncrease(final float bulletSpeedIncrease) {
		this.bulletSpeedIncrease = bulletSpeedIncrease;
	}

	@Override
	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
		if (!enabled) {
			setBulletsEnabled(false);
		}
	}

	public void setJumpRange(final float jumpRange) {
		this.jumpRange = jumpRange;
	}

	public void setJumpSequenceTime(final float jumpSequenceTime) {
		this.jumpSequenceTime = jumpSequenceTime;
	}

	public void setMaxAngularVelocity(final float maxAngularVelocity) {
		this.maxAngularVelocity = maxAngularVelocity;
	}

	public void setMaxSpeed(final float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void setPerformanceDamageGain(final float performanceDamageGain) {
		this.performanceDamageGain = performanceDamageGain;
	}

	public void setRotateAcceleration(final float rotateAcceleration) {
		this.rotateAcceleration = rotateAcceleration;
	}

	public void setThrustAcceleration(final float thrustAcceleration) {
		this.thrustAcceleration = thrustAcceleration;
	}

	public void update(final Bullet bullet, final float timeStep,
			final AreaF wrapArea) {
		if (bullet.isEnabled()) {
			if (bullet.getLifetime() < bulletMaxLifetime) {
				final VectorF position = bullet.getMassCenter();
				Integrate.step(position, bullet.getVelocity(), timeStep);
				wrapArea.wrap(position);
				bullet.addLifetime(timeStep);
			} else {
				bullet.setEnabled(false);
			}
		}
	}

	@Override
	public void update(final Game game) {
		if (!isEnabled()) {
			return;
		}

		final float timeStep = game.getTimeStep();
		assert (timeStep != 0);

		// jump control
		if (!isDestroyed()) {
			switch (jumpStatus) {
			case READY_TO_JUMP:
				if (controls.jump()) {
					jumpStatus = STARTING_JUMP;
					sounds.playJump();
					updateJumpProgress(timeStep);
				}
				break;
			case STARTING_JUMP:
				updateJumpProgress(timeStep);
				if (jumpProgress >= 0.5f) {
					final VectorF positoinChange = new VectorF(jumpRange, 0);
					getRotation().rotate(positoinChange);
					getMassCenter().add(positoinChange);
					// final AreaF area = game.getWrapArea();
					// getMassCenter().set(Random.range(area.min.x, area.max.x),
					// Random.range(area.min.y, area.max.y));
					jumpStatus = ENDING_JUMP;
				}
				break;
			case ENDING_JUMP:
				updateJumpProgress(timeStep);
				if (jumpProgress >= 1.f) {
					jumpStatus = READY_TO_JUMP;
					jumpProgress = 0;
				}
				break;
			}
		}

		float angularVelocity = getAngularVelocity();
		rotateAngularAcceleration = 0;

		if (!isJumping() && !isDestroyed()) {
			final float performancePercentage = 1 - performanceDamageGain
					* stats.getTotalDamage() / stats.getMaxDamage();
			final float thrustAcceleration = this.thrustAcceleration
					* performancePercentage;
			final float rotateAcceleration = this.rotateAcceleration
					* performancePercentage;

			// thruster controls
			addLocalAcceleration(new VectorF(controls.thrust()
					* thrustAcceleration, 0));

			// rotational thruster controls
			final float controlValue = controls.rotate();
			if (controlValue == 0) {
				// stop turning controller
				// calculates acceleration to stop rotating
				// limited to default thruster acceleration
				// keeps existing rotation by external forces
				rotateAngularAcceleration = Limit.clamp(-angularVelocity
						/ timeStep, -rotateAcceleration, rotateAcceleration);
			} else {
				rotateAngularAcceleration = controlValue * rotateAcceleration;
			}
			addAngularAcceleration(rotateAngularAcceleration);

			if (controls.thrust() != 0) {
				sounds.playThruster(true);
			} else if (rotateAngularAcceleration != 0) {
				sounds.playThruster(true);
			} else {
				sounds.playThruster(false);
			}
		} else {
			sounds.playThruster(false);
		}

		// integrate accelerations and limit velocities
		Integrate.step(getVelocity(), getAcceleration(), timeStep);
		Limit.clamp(getVelocity(), -maxSpeed, maxSpeed);
		angularVelocity = Integrate.step(angularVelocity,
				getAngularAcceleration(), timeStep);
		angularVelocity = Limit.clamp(angularVelocity, -maxAngularVelocity,
				maxAngularVelocity);
		setAngularVelocity(angularVelocity);

		// reset accelerations
		getAcceleration().set(0);
		setAngularAcceleration(0);

		// integrate velocities
		Integrate.step(getMassCenter(), getVelocity(), timeStep);
		Integrate.step(getRotation(), angularVelocity, timeStep);

		// wrap position
		game.wrap(getMassCenter());

		// update box position
		updateBoxPosition();

		// update bullets
		for (final Bullet bullet : bullets) {
			update(bullet, timeStep, game.getWrapArea());
		}

		// fire control
		if (lastFiredTime < bulletFireDelay) {
			lastFiredTime += timeStep;
		} else if (controls.fire() && !isJumping() && !isDestroyed()) {
			fireBullet(findBullet(game));
		}
	}

	public void updateBoxPosition() {
		boxCenter.set(boxOffset, 0);
		getRotation().rotate(boxCenter);
		boxCenter.add(getMassCenter());
	}

	private void updateJumpProgress(final float timeStep) {
		jumpProgress += timeStep / jumpSequenceTime;
		if (jumpProgress > 1.f) {
			jumpProgress = 1.f;
		}
		float fraction = (jumpProgress < 0.5f ? 1 - 2 * jumpProgress
				: 2 * jumpProgress - 1);
		final float min = 0.01f;
		if (fraction < min) {
			fraction = min;
		}
		jumpScale.set(fraction, fraction * fraction);
		// Limit.clamp(jumpScale, 0, 1);
		pixels.updatePixelOffsetSize(pixelData.getPixelOffsetSize().times(
				jumpScale));
		boxOffset = jumpScale.x * boxOffsetOriginal;
	}

	public void updateThrustPixels() {
		states.set(pixelData.getThrustPixels(), PixelState.CLEAR);

		if (!isJumping() && !isDestroyed()) {
			final float thrust = controls.thrust();
			if (thrust > 0) {
				states.set(pixelData.getThrustForwardPixels(),
						PixelState.THRUSTER);
			} else if (thrust < 0) {
				states.set(pixelData.getThrustBackPixels(), PixelState.THRUSTER);
			} else if (rotateAngularAcceleration > 0) {
				states.set(pixelData.getRotateRightPixels(),
						PixelState.THRUSTER);
			} else if (rotateAngularAcceleration < 0) {
				states.set(pixelData.getRotateLeftPixels(), PixelState.THRUSTER);
			}
		}
	}
}
