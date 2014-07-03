package com.harmboschloo.boxy.debug;

import com.harmboschloo.boxy.math.VectorF;

public class ContactData {
	public final VectorF position1 = new VectorF();
	public final VectorF position2 = new VectorF();

	public ContactData() {
	}

	public ContactData(final VectorF position1, final VectorF position2) {
		this.position1.copy(position1);
		this.position2.copy(position2);
	}
}
