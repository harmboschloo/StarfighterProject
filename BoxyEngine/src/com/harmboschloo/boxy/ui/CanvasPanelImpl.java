package com.harmboschloo.boxy.ui;

import com.google.gwt.canvas.client.Canvas;
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
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;

class CanvasPanelImpl implements Focusable, HasAllFocusHandlers,
		HasAllKeyHandlers, HasAllMouseHandlers, HasClickHandlers {
	private final Canvas canvas;

	CanvasPanelImpl() {
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			throw new CanvasNotSupportedException();
		}
	}

	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return canvas.addBlurHandler(handler);
	}

	@Override
	public HandlerRegistration addClickHandler(final ClickHandler handler) {
		return canvas.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return canvas.addFocusHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyDownHandler(final KeyDownHandler handler) {
		return canvas.addKeyDownHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyPressHandler(final KeyPressHandler handler) {
		return canvas.addKeyPressHandler(handler);
	}

	@Override
	public HandlerRegistration addKeyUpHandler(final KeyUpHandler handler) {
		return canvas.addKeyUpHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseDownHandler(
			final MouseDownHandler handler) {
		return canvas.addMouseDownHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(
			final MouseMoveHandler handler) {
		return canvas.addMouseMoveHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOutHandler(final MouseOutHandler handler) {
		return canvas.addMouseOutHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseOverHandler(
			final MouseOverHandler handler) {
		return canvas.addMouseOverHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseUpHandler(final MouseUpHandler handler) {
		return canvas.addMouseUpHandler(handler);
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(
			final MouseWheelHandler handler) {
		return canvas.addMouseWheelHandler(handler);
	}

	@Override
	public void fireEvent(final GwtEvent<?> event) {
		canvas.fireEvent(event);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public int getHeight() {
		return canvas.getCoordinateSpaceHeight();
	}

	@Override
	public int getTabIndex() {
		return canvas.getTabIndex();
	}

	public Widget getWidget() {
		return canvas;
	}

	public int getWidth() {
		return canvas.getCoordinateSpaceWidth();
	}

	@Override
	public void setAccessKey(final char key) {
		canvas.setAccessKey(key);
	}

	@Override
	public void setFocus(final boolean focused) {
		canvas.setFocus(focused);
	}

	public void setHeight(final int height) {
		canvas.setCoordinateSpaceHeight(height);
		canvas.setHeight(height + "px");
	}

	public void setSize(final int width, final int height) {
		canvas.setPixelSize(width, height);
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
	}

	@Override
	public void setTabIndex(final int index) {
		canvas.setTabIndex(index);
	}

	public void setWidth(final int width) {
		canvas.setCoordinateSpaceWidth(width);
		canvas.setWidth(width + "px");
	}
}
