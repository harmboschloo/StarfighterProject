package com.harmboschloo.boxy.graphics.viewport;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.harmboschloo.boxy.collision.Box;
import com.harmboschloo.boxy.collision.DefaultBox;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.util.Group;
import com.harmboschloo.boxy.util.HasUpdate;

public class Viewport extends Group<HasViewportUpdate> implements Box,
		HasUpdate {
	private final AreaF clipArea = new AreaF();
	private final VectorF baseSize = new VectorF();
	private final Box box = new DefaultBox();
	private float scale = 1;
	private Context2d context = null;

	public Viewport(final Canvas canvas, final VectorF baseSize) {
		this.baseSize.copy(baseSize);
		context = canvas.getContext2d();
		clipArea.min.set(0, 0);
		clipArea.max.set(canvas.getCoordinateSpaceWidth(),
				canvas.getCoordinateSpaceHeight());
		updateBox();
	}

	public Viewport(final Context2d context, final AreaF clipArea,
			final VectorF baseSize) {
		this.baseSize.copy(baseSize);
		this.context = context;
		this.clipArea.copy(clipArea);
		updateBox();
	}

	public Viewport(final Context2d context, final VectorF baseSize) {
		this.baseSize.copy(baseSize);
		this.context = context;
		clipArea.copy(clipArea);
		updateBox();
	}

	public VectorF getBaseSize() {
		return baseSize;
	}

	@Override
	public VectorF getCenter() {
		return box.getCenter();
	}

	public AreaF getClipArea() {
		return clipArea;
	}

	public Context2d getContext() {
		return context;
	}

	@Override
	public VectorF getHalfSize() {
		return box.getHalfSize();
	}

	@Override
	public Rotation getRotation() {
		return box.getRotation();
	}

	public float getScale() {
		return scale;
	}

	@Override
	public boolean isEnabled() {
		return box.isEnabled();
	}

	@Override
	public void update() {
		final float width = clipArea.getWidth();
		final float height = clipArea.getHeight();

		context.save();

		context.beginPath();
		context.rect(clipArea.min.x, clipArea.min.y, width, height);
		context.clip();

		context.translate(clipArea.min.x + width / 2, clipArea.min.y + height
				/ 2);
		context.scale(scale, scale);

		final VectorF center = getCenter();
		context.translate(-center.x, -center.y);

		updateElements();

		context.restore();
	}

	public void updateBox() {
		final float width = clipArea.getWidth();
		final float height = clipArea.getHeight();
		scale = Math.min(width / baseSize.x, height / baseSize.y);
		if (scale > 0) {
			box.getHalfSize().set(0.5f * width / scale, 0.5f * height / scale);
		} else {
			box.getHalfSize().set(0, 0);
		}
	}

	protected void updateElements() {
		for (final HasViewportUpdate element : getElements()) {
			element.update(this);
		}
	}
}
