package com.harmboschloo.boxy.collision;

import java.util.ArrayList;
import java.util.List;

import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Rotation;
import com.harmboschloo.boxy.math.VectorF;

public class BoxListListContactIterator<TBox1 extends Box, TBox2 extends Box>
		implements BoxContactIterator<TBox1, TBox2> {
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
	private final List<? extends TBox1> boxes1;
	private final List<? extends TBox2> boxes2;

	private int i1 = 0;

	private int i2 = 0;

	public BoxListListContactIterator(final List<? extends TBox1> boxes1,
			final List<? extends TBox2> boxes2) {
		this.relativeImpl = new RelativeImpl();
		this.boxes1 = boxes1;
		this.boxes2 = boxes2;
	}

	public BoxListListContactIterator(final List<? extends TBox1> boxes1,
			final List<? extends TBox2> boxes2, final AreaF wrapArea) {
		this.relativeImpl = new RelativeWrapImpl(wrapArea);
		this.boxes1 = boxes1;
		this.boxes2 = boxes2;
	}

	public BoxListListContactIterator(final TBox1 box1,
			final List<? extends TBox2> boxes2) {
		this.relativeImpl = new RelativeImpl();
		final ArrayList<TBox1> boxes1 = new ArrayList<TBox1>();
		boxes1.add(box1);
		this.boxes1 = boxes1;
		this.boxes2 = boxes2;
	}

	public BoxListListContactIterator(final TBox1 box1,
			final List<? extends TBox2> boxes2, final AreaF wrapArea) {
		this.relativeImpl = new RelativeWrapImpl(wrapArea);
		final ArrayList<TBox1> boxes1 = new ArrayList<TBox1>();
		boxes1.add(box1);
		this.boxes1 = boxes1;
		this.boxes2 = boxes2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TBox1 getBox1() {
		return (TBox1) tester.getBox1();
	}

	@SuppressWarnings("unchecked")
	@Override
	public TBox2 getBox2() {
		return (TBox2) tester.getBox2();
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
		while (i1 < boxes1.size()) {
			final Box box1 = boxes1.get(i1);
			if (box1.isEnabled()) {
				tester.setBox1(box1);
				while (i2 < boxes2.size()) {
					final Box box2 = boxes2.get(i2);
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
			i2 = 0;
		}
		return false;
	}

	@Override
	public void reset() {
		i1 = 0;
		i2 = 0;
		tester.reset();
	}
}
