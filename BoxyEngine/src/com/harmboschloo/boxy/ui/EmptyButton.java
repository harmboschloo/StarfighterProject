package com.harmboschloo.boxy.ui;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.client.ui.Button;

public class EmptyButton extends Button {

	public EmptyButton() {
		Empty.widget(this);
		getElement().getStyle().setCursor(Cursor.POINTER);
	}
}
