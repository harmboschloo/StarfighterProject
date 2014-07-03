package com.harmboschloo.boxy.ui;

import java.util.ArrayList;

import com.google.gwt.event.shared.HandlerRegistration;

public class HandlerRegistrationManager {
	private final ArrayList<HandlerRegistration> registrations = new ArrayList<HandlerRegistration>();

	public void add(final HandlerRegistration registration) {
		registrations.add(registration);
	}

	public void removeAll() {
		for (final HandlerRegistration registration : registrations) {
			registration.removeHandler();
		}
		registrations.clear();
	}
}
