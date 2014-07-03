package com.harmboschloo.starfighter.game.ai;

import java.util.ArrayList;

import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.object.GameObject;
import com.harmboschloo.starfighter.game.object.Ship;

public class DroneDebugSensors extends DroneSensors {
	private final ArrayList<ObjectData> objectData = new ArrayList<ObjectData>();

	public DroneDebugSensors(final float range, final Ship ship) {
		super(range, ship);
	}

	public ArrayList<ObjectData> getObjectData() {
		return objectData;
	}

	@Override
	public ObjectData senseObject(final GameObject object, final Game game) {
		final ObjectData data = super.senseObject(object, game);
		if (data != null) {
			objectData.add(data);
		}
		return data;
	}

	@Override
	public void update(final Game game) {
		objectData.clear();
		super.update(game);
	}
}
