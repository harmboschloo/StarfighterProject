package com.harmboschloo.starfighter.game.sound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;

class SoundsImpl implements Sounds.Impl {
	interface Bundle extends ClientBundle {
		@Source("sounds/jump.wav")
		DataResource jump();

		@Source("sounds/laser.wav")
		DataResource laser();

		@Source("sounds/ship_hit.wav")
		DataResource shipHit();

		@Source("sounds/thruster.wav")
		DataResource thruster();
	}

	private final Bundle bundle = GWT.create(Bundle.class);

	@Override
	public DataResource jump() {
		return bundle.jump();
	}

	@Override
	public DataResource laser() {
		return bundle.laser();
	}

	@Override
	public DataResource shipHit() {
		return bundle.shipHit();
	}

	@Override
	public DataResource thruster() {
		return bundle.thruster();
	}
}
