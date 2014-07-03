package com.harmboschloo.boxy.ui;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasAllKeyHandlers;
import com.google.gwt.event.dom.client.HasAllMouseHandlers;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Focusable;

public class CanvasPanel extends Composite implements Focusable,
		HasAllFocusHandlers, HasAllKeyHandlers, HasAllMouseHandlers,
		HasClickHandlers {
	private final CanvasPanelImpl impl = GWT.create(CanvasPanelImpl.class);

	public CanvasPanel() {
		initWidget(impl.getWidget());
	}

	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return impl.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addClickHandler(final ClickHandler handler) {
		return impl.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return impl.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyDownHandler(final KeyDownHandler handler) {
		return impl.addKeyDownHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(final KeyPressHandler handler) {
		return impl.addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyUpHandler(final KeyUpHandler handler) {
		return impl.addKeyUpHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseDownHandler(
			final MouseDownHandler handler) {
		return impl.addMouseDownHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(
			final MouseMoveHandler handler) {
		return impl.addMouseMoveHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(final MouseOutHandler handler) {
		return impl.addMouseOutHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(
			final MouseOverHandler handler) {
		return impl.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseUpHandler(final MouseUpHandler handler) {
		return impl.addMouseUpHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(
			final MouseWheelHandler handler) {
		return impl.addMouseWheelHandler(handler);
	}

	public void clearCanvas() {
		getContext2d().clearRect(0, 0, getWidth(), getHeight());
	}

	public Canvas getCanvas() {
		return impl.getCanvas();
	}

	public Context2d getContext2d() {
		return getCanvas().getContext2d();
	}

	public int getHeight() {
		return impl.getHeight();
	}

	@Override
	public int getTabIndex() {
		return impl.getTabIndex();
	}

	public int getWidth() {
		return impl.getWidth();
	}

	@Override
	public void setAccessKey(final char key) {
		impl.setAccessKey(key);
	}

	@Override
	public void setFocus(final boolean focused) {
		impl.setFocus(focused);
	}

	public void setHeight(final int height) {
		impl.setHeight(height);
	}

	public void setSize(final int width, final int height) {
		impl.setSize(width, height);
	}

	@Override
	public void setTabIndex(final int index) {
		impl.setTabIndex(index);
	}

	public void setWidth(final int width) {
		impl.setWidth(width);
	}
}
