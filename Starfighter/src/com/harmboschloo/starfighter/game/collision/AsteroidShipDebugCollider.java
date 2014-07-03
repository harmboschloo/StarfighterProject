package com.harmboschloo.starfighter.game.collision;

import java.util.ArrayList;
import java.util.Collection;

import com.harmboschloo.boxy.debug.ContactData;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.object.Asteroid;
import com.harmboschloo.starfighter.game.object.Ship;

public class AsteroidShipDebugCollider extends AsteroidShipCollider {
	private final ArrayList<ContactData> boxContactData = new ArrayList<ContactData>();
	private final ArrayList<ContactData> pixelPointContactData = new ArrayList<ContactData>();

	public AsteroidShipDebugCollider() {
	}

	public Collection<ContactData> getBoxContactData() {
		return boxContactData;
	}

	public Collection<ContactData> getPixelPointContactData() {
		return pixelPointContactData;
	}

	@Override
	protected void handleBoxContact(final Asteroid asteroid, final Ship ship,
			final VectorF relativePosition) {
		boxContactData.add(new ContactData(asteroid.getCenter(), ship
				.getCenter()));
		super.handleBoxContact(asteroid, ship, relativePosition);
	}

	@Override
	protected void handlePixelContact(final Asteroid asteroid,
			final VectorI pixel, final Ship ship, final VectorI point) {
		pixelPointContactData.add(new ContactData(pixelPointTester
				.calculateAbsolutePixelPosition(), pixelPointTester
				.calculateAbsolutePointPosition()));
		super.handlePixelContact(asteroid, pixel, ship, point);
	}

	@Override
	public void update(final Game game) {
		boxContactData.clear();
		pixelPointContactData.clear();
		super.update(game);
	}
}
