package com.harmboschloo.boxy.util;

import com.google.gwt.user.client.Timer;

public abstract class StepTimer {
	private final Timer timer;
	private final TimeSpec timeSpec = new TimeSpec();
	private int stepCheckPeriodMs = 1;
	private int autoResetSteps = 0;
	private boolean running = false;

	public StepTimer() {
		timer = new Timer() {
			@Override
			public void run() {
				checkStep();
			}
		};
	}

	private void checkStep() {
		if (timeSpec.getRealRunTime() >= timeSpec.getRunTime()) {
			timeSpec.step();
			step();

			if (autoResetSteps > 0) {
				if (timeSpec.getRealRunTime() > (timeSpec.getRunTime() + autoResetSteps
						* timeSpec.getTimeStep())) {
					timeSpec.resetRealRunTimeToRunTime();
				}
			}
		}
	}

	public int getAutoResetSteps() {
		return autoResetSteps;
	}

	public int getStepCheckPeriodMs() {
		return stepCheckPeriodMs;
	}

	public TimeSpec getTimeSpec() {
		return timeSpec;
	}

	public float getTimeStep() {
		return timeSpec.getTimeStep();
	}

	public boolean isRunning() {
		return running;
	}

	public void restart() {
		stop();
		start();
	}

	public void resume() {
		running = true;
		timeSpec.resetRealRunTimeToRunTime();
		step();
		timer.scheduleRepeating(stepCheckPeriodMs);
	}

	public void setAutoResetSteps(final int autoResetSteps) {
		this.autoResetSteps = autoResetSteps;
	}

	public void setStepCheckPeriodMs(final int stepCheckPeriodMs) {
		this.stepCheckPeriodMs = stepCheckPeriodMs;
	}

	public void setTimeStep(final float timeStep) {
		timeSpec.setTimeStep(timeStep);
	}

	public void start() {
		running = true;
		timeSpec.reset();
		step();
		timer.scheduleRepeating(stepCheckPeriodMs);
	}

	public abstract void step();

	public void stop() {
		timer.cancel();
		running = false;
	}
}