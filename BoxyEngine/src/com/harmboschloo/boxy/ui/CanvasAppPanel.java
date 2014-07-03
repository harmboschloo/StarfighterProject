package com.harmboschloo.boxy.ui;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
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
import com.google.gwt.event.logical.shared.HasResizeHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.harmboschloo.boxy.math.VectorI;

public class CanvasAppPanel extends LayoutPanel implements Focusable,
		HasAllFocusHandlers, HasAllKeyHandlers, HasAllMouseHandlers,
		HasClickHandlers, HasResizeHandlers {
	class GameResizeEvent extends ResizeEvent {
		GameResizeEvent(final int width, final int height) {
			super(width, height);
		}
	}

	private final CanvasPanel canvasPanel;

	private final HandlerRegistrationManager handlerRegistrations = new HandlerRegistrationManager();

	public CanvasAppPanel() {
		canvasPanel = new CanvasPanel();
		updateCanvasSize();
		add(canvasPanel);
	}

	private HandlerRegistration add(final HandlerRegistration registration) {
		handlerRegistrations.add(registration);
		return registration;
	}

	@Override
	public HandlerRegistration addBlurHandler(final BlurHandler handler) {
		return add(canvasPanel.addBlurHandler(handler));
	}

	@Override
	public HandlerRegistration addClickHandler(final ClickHandler handler) {
		return add(canvasPanel.addClickHandler(handler));
	}

	@Override
	public HandlerRegistration addFocusHandler(final FocusHandler handler) {
		return add(canvasPanel.addFocusHandler(handler));
	}

	@Override
	public HandlerRegistration addKeyDownHandler(final KeyDownHandler handler) {
		return add(canvasPanel.addKeyDownHandler(handler));
	}

	@Override
	public HandlerRegistration addKeyPressHandler(final KeyPressHandler handler) {
		return add(canvasPanel.addKeyPressHandler(handler));
	}

	@Override
	public HandlerRegistration addKeyUpHandler(final KeyUpHandler handler) {
		return add(canvasPanel.addKeyUpHandler(handler));
	}

	@Override
	public HandlerRegistration addMouseDownHandler(
			final MouseDownHandler handler) {
		return add(canvasPanel.addMouseDownHandler(handler));
	}

	@Override
	public HandlerRegistration addMouseMoveHandler(
			final MouseMoveHandler handler) {
		return add(canvasPanel.addMouseMoveHandler(handler));
	}

	@Override
	public HandlerRegistration addMouseOutHandler(final MouseOutHandler handler) {
		return add(canvasPanel.addMouseOutHandler(handler));
	}

	@Override
	public HandlerRegistration addMouseOverHandler(
			final MouseOverHandler handler) {
		return add(canvasPanel.addMouseOverHandler(handler));
	}

	@Override
	public HandlerRegistration addMouseUpHandler(final MouseUpHandler handler) {
		return add(canvasPanel.addMouseUpHandler(handler));
	}

	@Override
	public HandlerRegistration addMouseWheelHandler(
			final MouseWheelHandler handler) {
		return add(canvasPanel.addMouseWheelHandler(handler));
	}

	@Override
	public HandlerRegistration addResizeHandler(final ResizeHandler handler) {
		return add(canvasPanel.addHandler(handler, ResizeEvent.getType()));
	}

	public void clearCanvas() {
		canvasPanel.clearCanvas();
	}

	public void clearHandlers() {
		handlerRegistrations.removeAll();
	}

	public void focus() {
		setFocus(true);
	}

	public Canvas getCanvas() {
		return canvasPanel.getCanvas();
	}

	public CanvasPanel getCanvasPanel() {
		return canvasPanel;
	}

	public Context2d getContext() {
		return canvasPanel.getContext2d();
	}

	public int getHeight() {
		return canvasPanel.getHeight();
	}

	public VectorI getSize() {
		return new VectorI(getWidth(), getHeight());
	}

	@Override
	public int getTabIndex() {
		return canvasPanel.getTabIndex();
	}

	public int getWidth() {
		return canvasPanel.getWidth();
	}

	@Override
	public void onResize() {
		super.onResize();
		updateCanvasSize();
		canvasPanel.fireEvent(new GameResizeEvent(getWidth(), getHeight()));
	}

	@Override
	public void onUnload() {
		clearHandlers();
	}

	@Override
	public void setAccessKey(final char key) {
		canvasPanel.setAccessKey(key);
	}

	@Override
	public void setFocus(final boolean focused) {
		canvasPanel.setFocus(focused);
	}

	@Override
	public void setTabIndex(final int index) {
		canvasPanel.setTabIndex(index);
	}

	private void updateCanvasSize() {
		// have to set canvas size manually
		canvasPanel.setSize(getOffsetWidth(), getOffsetHeight());
	}
}
