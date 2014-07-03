package com.harmboschloo.starfighter.game.sound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.DataResource.DoNotEmbed;

class SoundsImplIE implements Sounds.Impl {
	interface Bundle extends ClientBundle {
		@DoNotEmbed
		@Source("sounds/jump.mp3")
		DataResource jump();

		@DoNotEmbed
		@Source("sounds/laser.mp3")
		DataResource laser();

		@DoNotEmbed
		@Source("sounds/ship_hit.mp3")
		DataResource shipHit();

		@DoNotEmbed
		@Source("sounds/thruster.mp3")
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
