package com.harmboschloo.boxy.collision;

import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Limit;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class BoxContactTester {
	private final VectorF relativePosition = new VectorF();
	private final Rotation relativeRotation = new Rotation();
	private Box box1 = null;
	private Box box2 = null;

	private boolean doTest() {
		assert (box1 != null);
		assert (box2 != null);

		// check box outer radii vs distance
		if (relativePosition.getLengthSquared() > box1.getHalfSize()
				.plus(box2.getHalfSize()).getLengthSquared()) {
			return false;
		}

		VectorF p1, p2, pCenter;
		final VectorF pMin = new VectorF();
		final VectorF pMax = new VectorF();
		VectorF halfSize;

		// first test/transform box2 relative to box1
		// calculate transformed corner points p1,p2 of box2
		// TODO: check combining rotations of box1 & box2 into one,
		// and see if that gives better performance
		p1 = relativeRotation.rotateNew(box2.getHalfSize());
		p2 = new VectorF(box2.getHalfSize().x, -box2.getHalfSize().y);
		relativeRotation.rotate(p2);
		// make use of box symmetry
		pMin.set(Limit.min(p1.x, -p1.x, p2.x, -p2.x),
				Limit.min(p1.y, -p1.y, p2.y, -p2.y));
		pMax.set(Limit.max(p1.x, -p1.x, p2.x, -p2.x),
				Limit.max(p1.y, -p1.y, p2.y, -p2.y));
		// rotate box2 relative to box1
		pCenter = box1.getRotation().rotateReverseNew(relativePosition);
		pMin.add(pCenter);
		pMax.add(pCenter);
		// check limits of box2 relative to box1
		halfSize = box1.getHalfSize();
		if (!(pMin.x <= halfSize.x && pMin.y <= halfSize.y
				&& pMax.x >= -halfSize.x && pMax.y >= -halfSize.y)) {
			return false;
		}

		// now check box1 relative to box2
		// calculate transformed corner points p1,p2 of box1
		p1 = relativeRotation.rotateReverseNew(box1.getHalfSize());
		p2 = new VectorF(box1.getHalfSize().x, -box1.getHalfSize().y);
		relativeRotation.rotateReverse(p2);
		// make use of box symmetry
		pMin.set(Limit.min(p1.x, -p1.x, p2.x, -p2.x),
				Limit.min(p1.y, -p1.y, p2.y, -p2.y));
		pMax.set(Limit.max(p1.x, -p1.x, p2.x, -p2.x),
				Limit.max(p1.y, -p1.y, p2.y, -p2.y));
		// rotate box1 relative to box2
		pCenter = box2.getRotation().rotateReverseNew(
				relativePosition.opposite());
		pMin.add(pCenter);
		pMax.add(pCenter);
		// check limits of box1 relative to box2
		halfSize = box2.getHalfSize();
		if (!(pMin.x <= halfSize.x && pMin.y <= halfSize.y
				&& pMax.x >= -halfSize.x && pMax.y >= -halfSize.y)) {
			return false;
		}

		return true;
	}

	public Box getBox1() {
		return box1;
	}

	public Box getBox2() {
		return box2;
	}

	public VectorF getRelativePosition() {
		return relativePosition;
	}

	public Rotation getRelativeRotation() {
		return relativeRotation;
	}

	public void reset() {
		box1 = null;
		box2 = null;
		relativePosition.set(0, 0);
		relativeRotation.reset();
	}

	public void setBox1(final Box box1) {
		this.box1 = box1;
	}

	public void setBox2(final Box box2) {
		this.box2 = box2;
	}

	public boolean test() {
		return doTest();
	}

	public boolean test(final Box box1, final Box box2) {
		setBox1(box1);
		setBox2(box2);
		updateRelativePositionAndRotation();
		return doTest();
	}

	public boolean test(final Box box1, final Box box2, final AreaF wrapArea) {
		setBox1(box1);
		setBox2(box2);
		updateRelativePositionAndRotation(wrapArea);
		return doTest();
	}

	public boolean test(final Box box1, final Box box2,
			final VectorF relativePosition) {
		setBox1(box1);
		setBox2(box2);
		this.relativePosition.copy(relativePosition);
		return doTest();
	}

	public void updateRelativePositionAndRotation() {
		relativePosition.copy(box2.getCenter());
		relativePosition.substract(box1.getCenter());
		relativeRotation.setAngle(Limit.wrapAngle(box2.getRotation().getAngle()
				- box1.getRotation().getAngle()));
	}

	public void updateRelativePositionAndRotation(final AreaF wrapArea) {
		updateRelativePositionAndRotation();
		wrapArea.wrapRelativePosition(relativePosition);
	}

}
