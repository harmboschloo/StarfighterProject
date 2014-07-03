package com.harmboschloo.boxy.ui;

public class CanvasNotSupportedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CanvasNotSupportedException() {
		super("Canvas not supported");
	}
}
