package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;

public class PixelPointContactTester {
	private final VectorI pixels = new VectorI();
	private final VectorI points = new VectorI();
	private final VectorI pixel = new VectorI();
	private final VectorI point = new VectorI();
	private final VectorF pixelSize = new VectorF();
	private final VectorF pixelBasePosition = new VectorF();
	private final VectorF pixelPosition = new VectorF();
	private final VectorF offset = new VectorF();
	private final VectorF step = new VectorF();
	private final VectorF relativeBoxPosition = new VectorF();
	private final Rotation relativeRotation = new Rotation();
	private final VectorF relativeCenter = new VectorF();
	private PixelBox pixelBox = null;
	private PixelBox pointBox = null;

	public PixelPointContactTester() {
	}

	public VectorF calculateAbsolutePixelPosition() {
		final VectorF position = new VectorF(pixelSize.x
				* (pixel.x + 0.5f - 0.5f * pixels.x), pixelSize.y
				* (pixel.y + 0.5f - 0.5f * pixels.y));
		pixelBox.getRotation().rotate(position);
		position.add(pixelBox.getCenter());
		return position;
	}

	public VectorF calculateAbsolutePointPosition() {
		final VectorF position = new VectorF(point);
		position.multiplyBy(step);
		position.add(offset);
		pointBox.getRotation().rotate(position);
		position.add(pointBox.getCenter());
		return position;
	}

	public VectorI getPixel() {
		return pixel;
	}

	public PixelBox getPixelBox() {
		return pixelBox;
	}

	public VectorI getPixels() {
		return pixels;
	}

	public VectorF getPixelSize() {
		return pixelSize;
	}

	public VectorI getPoint() {
		return point;
	}

	public PixelBox getPointBox() {
		return pointBox;
	}

	public VectorI getPoints() {
		return points;
	}

	public VectorF getRelativeBoxPosition() {
		return relativeBoxPosition;
	}

	public void initTest(final PixelBox pixelBox, final PixelBox pointBox) {
		setPixelBox(pixelBox);
		setPointBox(pointBox);
		updateRelativeBoxPosition();
		initTestData();
	}

	public void initTest(final PixelBox pixelBox, final PixelBox pointBox,
			final AreaF wrapArea) {
		setPixelBox(pixelBox);
		setPointBox(pointBox);
		updateRelativeBoxPosition(wrapArea);
		initTestData();
	}

	public void initTest(final PixelBox pixelBox, final PixelBox pointBox,
			final VectorF relativeBoxPosition) {
		setPixelBox(pixelBox);
		setPointBox(pointBox);
		setRelativeBoxPosition(relativeBoxPosition);
		initTestData();
	}

	public void initTestData() {
		assert (pixelBox != null);
		assert (pointBox != null);
		assert (pixels.x > 0);
		assert (pixels.y > 0);

		relativeCenter.copy(relativeBoxPosition);
		pixelBox.getRotation().rotateReverse(relativeCenter);
		relativeRotation.setAngle(pointBox.getRotation().getAngle()
				- pixelBox.getRotation().getAngle());
		pixelBasePosition.copy(pixelBox.getHalfSize());
		pixelBasePosition.add(relativeCenter);

		point.set(0, -1);
	}

	public boolean nextContact() {
		++point.y;
		while (point.x < points.x) {
			while (point.y < points.y) {
				if (test()) {
					return true;
				}
				++point.y;
			}
			++point.x;
			point.y = 0;
		}

		return false;
	}

	public void setPixelBox(final PixelBox pixelBox) {
		this.pixelBox = pixelBox;
		pixels.copy(pixelBox.getPixelCount());
		pixelSize.copy(pixelBox.getPixelOffsetSize());
	}

	public void setPointBox(final PixelBox pointBox) {
		this.pointBox = pointBox;
		points.copy(pointBox.getPixelCount());
		points.add(1);

		offset.set(0, 0);
		step.set(0, 0);
		if (points.x > 1) {
			offset.x = -pointBox.getHalfSize().x;
			step.x = pointBox.getPixelOffsetSize().x;
		}
		if (points.y > 1) {
			offset.y = -pointBox.getHalfSize().y;
			step.y = pointBox.getPixelOffsetSize().y;
		}
	}

	public void setRelativeBoxPosition(final VectorF relativeBoxPosition) {
		this.relativeBoxPosition.copy(relativeBoxPosition);
	}

	public boolean test() {
		pixelPosition.set(point.x, point.y);
		pixelPosition.multiplyBy(step);
		pixelPosition.add(offset);
		relativeRotation.rotate(pixelPosition);
		pixelPosition.add(pixelBasePosition);

		// check within box
		if (pixelPosition.x < 0 || pixelPosition.y < 0
				|| pixelPosition.x > 2 * pixelBox.getHalfSize().x
				|| pixelPosition.y > 2 * pixelBox.getHalfSize().y) {
			return false;
		}

		// calculate pixel
		pixel.set((int) Math.floor(pixelPosition.x / pixelSize.x),
				(int) Math.floor(pixelPosition.y / pixelSize.y));

		// check edge
		if (pixel.x >= pixels.x) {
			pixel.x -= 1;
		}
		if (pixel.y >= pixels.y) {
			pixel.y -= 1;
		}
		assert (pixel.x >= 0);
		assert (pixel.x < pixels.x);
		assert (pixel.y >= 0);
		assert (pixel.y < pixels.y);

		return true;
	}

	public boolean test(final VectorI point) {
		this.point.copy(point);
		return test();
	}

	public void updateRelativeBoxPosition() {
		relativeBoxPosition.copy(pointBox.getCenter());
		relativeBoxPosition.substract(pixelBox.getCenter());
	}

	public void updateRelativeBoxPosition(final AreaF wrapArea) {
		updateRelativeBoxPosition();
		wrapArea.wrapRelativePosition(relativeBoxPosition);
	}
}
