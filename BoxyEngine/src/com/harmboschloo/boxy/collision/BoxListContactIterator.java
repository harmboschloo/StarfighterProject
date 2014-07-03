package com.harmboschloo.boxy.collision;

import java.util.List;

import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class BoxListContactIterator<TBox extends Box> implements
		BoxContactIterator<TBox, TBox> {
	class RelativeImpl {
		void update() {
			tester.updateRelativePositionAndRotation();
		}
	}

	class RelativeWrapImpl extends RelativeImpl {
		private final AreaF wrapArea;

		RelativeWrapImpl(final AreaF wrapArea) {
			this.wrapArea = wrapArea;
		}

		@Override
		void update() {
			tester.updateRelativePositionAndRotation(wrapArea);
		}
	}

	private final BoxContactTester tester = new BoxContactTester();
	private final RelativeImpl relativeImpl;
	private final List<? extends TBox> boxes;

	private int i1 = 0;

	private int i2 = 1;

	public BoxListContactIterator(final List<? extends TBox> boxes) {
		this.relativeImpl = new RelativeImpl();
		this.boxes = boxes;
	}

	public BoxListContactIterator(final List<? extends TBox> boxes,
			final AreaF wrapArea) {
		this.relativeImpl = new RelativeWrapImpl(wrapArea);
		this.boxes = boxes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TBox getBox1() {
		return (TBox) tester.getBox1();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TBox getBox2() {
		return (TBox) tester.getBox2();
	}

	@Override
	public VectorF getRelativePosition() {
		return tester.getRelativePosition();
	}

	@Override
	public Rotation getRelativeRotation() {
		return tester.getRelativeRotation();
	}

	@Override
	public boolean nextContact() {
		while (i1 < boxes.size()) {
			final Box box1 = boxes.get(i1);
			if (box1.isEnabled()) {
				tester.setBox1(box1);
				while (i2 < boxes.size()) {
					final Box box2 = boxes.get(i2);
					++i2;
					if (box2.isEnabled()) {
						tester.setBox2(box2);
						relativeImpl.update();
						if (tester.test()) {
							return true;
						}
					}
				}
			}
			++i1;
			i2 = (i1 + 1);
		}
		return false;
	}

	@Override
	public void reset() {
		i1 = 0;
		i2 = 1;
		tester.reset();
	}
}
