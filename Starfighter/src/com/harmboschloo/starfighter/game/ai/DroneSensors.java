package com.harmboschloo.starfighter.game.ai;

import java.util.Arrays;

import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;
import com.harmboschloo.starfighter.game.object.Asteroid;
import com.harmboschloo.starfighter.game.object.Bullet;
import com.harmboschloo.starfighter.game.object.GameObject;
import com.harmboschloo.starfighter.game.object.Ship;

public class DroneSensors implements HasGameUpdate {
	public static class ObjectData {
		public VectorF center = null;
		public float angle = 0;
		public VectorF velocity = null;
		public float radius = 0;
		public float distance = 0;
		public float nearestDistance = 0;
		public float timeToContact = 0;
		public VectorF contactCenter = null;
	}

	private final Ship ship;
	private float shipRadius;
	private float range;
	private float safetyMargin = 0;
	private float willCollide = 0;
	private float[] constraints;
	private int senseRate = 1;
	private int senseCount = 0;

	private float constraintReduction = 0.1f;

	public DroneSensors(final float range, final Ship ship) {
		this.ship = ship;
		this.range = range;
		constraints = new float[8];
	}

	public void clearConstraints() {
		willCollide = 0;
		Arrays.fill(constraints, 0);
	}

	public void decreaseSafetyMarginByFraction(final float fractionOfShipRadius) {
		safetyMargin -= fractionOfShipRadius * shipRadius;
		if (safetyMargin < 0) {
			safetyMargin = 0;
		}
	}

	private int getConstraintIndex(final float angle) {
		return Limit.wrap(
				(int) Math.round(constraints.length * angle / (2 * Math.PI)),
				0, constraints.length);
	}

	public float getConstraintReduction() {
		return constraintReduction;
	}

	public float[] getConstraints() {
		return constraints;
	}

	public int getConstraintsResolution() {
		return constraints.length;
	}

	public float getRange() {
		return range;
	}

	public float getSafetyMargin() {
		return safetyMargin;
	}

	public int getSenseCount() {
		return senseCount;
	}

	public int getSenseRate() {
		return senseRate;
	}

	public Ship getShip() {
		return ship;
	}

	public float getShipRadius() {
		return shipRadius;
	}

	// reducing constraints instead of clearing will result in smoother control
	// and object will be cleared more easily
	public void reduceConstraints() {
		willCollide = Limit.clamp(willCollide - constraintReduction, 0, 1);
		for (int i = 0; i < constraints.length; ++i) {
			constraints[i] = Limit.clamp(constraints[i] - constraintReduction,
					0, 1);
		}
	}

	public void senseAsteroids(final Game game) {
		for (final Asteroid asteroid : game.getAsteroids()) {
			senseObject(asteroid, game);
		}
	}

	public ObjectData senseObject(final GameObject object, final Game game) {
		if (!object.isActive()) {
			return null;
		}

		// all relative variables
		final ObjectData data = new ObjectData();
		data.center = object.getCenter().minus(ship.getCenter());
		game.getWrapArea().wrapRelativePosition(data.center);
		data.radius = object.getHalfSize().getLength();
		final float outerDistance = data.center.getLength() - data.radius;

		if (outerDistance > range) {
			return null;
		}
		data.distance = outerDistance - shipRadius - safetyMargin;
		data.velocity = object.getVelocity().minus(ship.getVelocity());

		// to local ship coordinates
		ship.getRotation().rotateReverse(data.center);
		ship.getRotation().rotateReverse(data.velocity);

		data.angle = data.center.getAngle();
		final float speed = data.velocity.getLength();

		if (speed == 0) {
			data.nearestDistance = data.distance;
			if (data.distance <= 0) {
				data.timeToContact = 0;
				data.contactCenter = data.center;
			} else {
				data.timeToContact = Float.MAX_VALUE;
				data.contactCenter = null;
			}
		} else {
			final boolean movingTowards = (data.center.dot(data.velocity) < 0);

			if (data.distance <= 0 || movingTowards) {
				final VectorF velocityNormalized = data.velocity
						.getNormalized();
				final float nearestCenterDistance = -data.center
						.dot(velocityNormalized.getPerpendicular());
				final float totalRadius = data.radius + shipRadius
						+ safetyMargin;
				data.nearestDistance = nearestCenterDistance - totalRadius;

				if (data.distance <= 0) {
					data.timeToContact = 0;
					data.contactCenter = data.center;
				} else {
					final float nearestCenterDistanceTravelled = -data.center
							.dot(velocityNormalized);
					final float contactDistanceTravelled = nearestCenterDistanceTravelled
							- (float) Math.sqrt(totalRadius * totalRadius
									- nearestCenterDistance
									* nearestCenterDistance);
					data.timeToContact = contactDistanceTravelled / speed;
					data.contactCenter = data.center.plus(velocityNormalized
							.times(contactDistanceTravelled));
				}
			} else {
				// moving away
				data.nearestDistance = data.distance;
				data.timeToContact = Float.MAX_VALUE;
				data.contactCenter = null;
			}
		}

		updateConstraints(data);

		return data;
	}

	public void senseShipsAndBullets(final Game game) {
		for (final Ship ship : game.getShips()) {
			if (ship != this.ship) {
				senseObject(ship, game);
				for (final Bullet bullet : ship.getBullets()) {
					senseObject(bullet, game);
				}
			}
		}
	}

	public void setConstraintReduction(final float constraintReduction) {
		this.constraintReduction = constraintReduction;
	}

	public void setConstraintsResolution(final int constraintsResolution) {
		constraints = new float[constraintsResolution];
	}

	public void setRange(final float range) {
		this.range = range;
	}

	public void setSafetyMargin(final float safetyMargin) {
		this.safetyMargin = safetyMargin;
	}

	public void setSafetyMarginByFraction(
			final float safetyMarginFractionOfShipRadius) {
		safetyMargin = safetyMarginFractionOfShipRadius * shipRadius;
	}

	public void setSenseCount(final int senseCount) {
		this.senseCount = senseCount;
	}

	public void setSenseRate(final int senseRate) {
		this.senseRate = senseRate;
	}

	@Override
	public void update(final Game game) {
		reduceConstraints();
		++senseCount;
		if (senseCount >= senseRate) {
			shipRadius = ship.getHalfSize().getLength();
			senseAsteroids(game);
			senseShipsAndBullets(game);
			senseCount = 0;
		}
	}

	private void updateConstraint(final int index, final float value) {
		if (value > constraints[index]) {
			constraints[index] = value;
		}
	}

	private void updateConstraints(final float angle, final float angleRange,
			final float value) {
		int index1 = getConstraintIndex(angle - angleRange / 2);
		final int index2 = getConstraintIndex(angle + angleRange / 2);
		while (true) {
			updateConstraint(index1, value);
			if (index1 == index2) {
				break;
			}
			++index1;
			if (index1 >= constraints.length) {
				index1 = 0;
			}
		}
	}

	private void updateConstraints(final ObjectData data) {
		if (data.timeToContact < 2) {
			final float PI = (float) Math.PI;
			final float angle = data.contactCenter.getAngle();
			willCollide = 1;
			updateConstraints(data.contactCenter.getAngle(), PI / 2, 1);
			updateConstraints(angle - PI / 2, PI / 2, 0.5f);
			updateConstraints(angle + PI / 2, PI / 2, 0.5f);
		}
	}

	public boolean willCollide() {
		return (willCollide > 0);
	}
}
