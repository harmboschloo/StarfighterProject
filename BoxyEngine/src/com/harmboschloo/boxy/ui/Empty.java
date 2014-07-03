package com.harmboschloo.boxy.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

public class Empty {
	private static class Impl {
		void empty(final Widget widget) {
			final Style style = widget.getElement().getStyle();
			style.setBackgroundColor("transparent");
			style.setBorderWidth(0, Unit.PX);
		}
	}

	@SuppressWarnings("unused")
	private static class ImplIE extends Impl {
		@Override
		void empty(final Widget widget) {
			super.empty(widget);
			widget.getElement().getStyle()
					.setBackgroundImage("url(about:blank)");
		}
	}

	public static void widget(final Widget widget) {
		final Impl impl = GWT.create(Impl.class);
		impl.empty(widget);
	}
}
