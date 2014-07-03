package com.harmboschloo.starfighter.game.sound;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.DataResource;

public class Sounds {
	static interface Impl {
		DataResource jump();

		DataResource laser();

		DataResource shipHit();

		DataResource thruster();
	}

	private static final Impl impl = GWT.create(SoundsImpl.class);

	public static DataResource jump() {
		return impl.jump();
	}

	public static DataResource laser() {
		return impl.laser();
	}

	public static DataResource shipHit() {
		return impl.shipHit();
	}

	public static DataResource thruster() {
		return impl.thruster();
	}
}
