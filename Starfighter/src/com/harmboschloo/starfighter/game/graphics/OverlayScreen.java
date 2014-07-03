package com.harmboschloo.starfighter.game.graphics;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.i18n.client.NumberFormat;
import com.harmboschloo.boxy.graphics.pixel.text.PixelLabel;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;

public class OverlayScreen implements HasGameUpdate {
	private static final float S1 = 1.25f * 5;
	private static final float S2 = 0.85f * 5;
	private static final float S3 = 0.75f * 5;
	private static final float S4 = 0.5f * 5;

	private final PixelLabel missionText;
	private final PixelLabel duelText;
	private final PixelLabel startText;
	private final PixelLabel continueText;
	private final PixelLabel playerText1;
	private final PixelLabel moveText1;
	private final PixelLabel fireText1;
	private final PixelLabel jumpText1;
	private final PixelLabel playerText2;
	private final PixelLabel moveText2;
	private final PixelLabel fireText2;
	private final PixelLabel jumpText2;
	private final PixelLabel muteText;
	private final PixelLabel stopText;
	private final PixelLabel pausedText;
	private final PixelLabel noFocusText;
	private final PixelLabel winText;
	private final PixelLabel loseText;
	private final PixelLabel drawText;
	private final PixelLabel leftWinText;
	private final PixelLabel rightWinText;
	private final VectorF size = new VectorF();
	private PixelLabel dronesDestroyedText = null;
	private PixelLabel timeText = null;
	private double time = 0;

	public OverlayScreen() {
		missionText = makeLabel("DESTROY ALL DRONES", S1);
		duelText = makeLabel("PLAYER DUEL", S1);
		startText = makeLabel("PRESS ENTER TO START", S3);
		continueText = makeLabel("PRESS ENTER TO CONTINUE", S3);
		playerText1 = makeLabel("RIGHT PLAYER", S2);
		moveText1 = makeLabel("MOVE - ARROWS", S4);
		fireText1 = makeLabel("FIRE - M     ", S4);
		jumpText1 = makeLabel("JUMP - N     ", S4);
		playerText2 = makeLabel("LEFT PLAYER", S2);
		moveText2 = makeLabel("MOVE - RDFG  ", S4);
		fireText2 = makeLabel("FIRE - X     ", S4);
		jumpText2 = makeLabel("JUMP - Z     ", S4);
		muteText = makeLabel("MUTE SOUND - S  ", S4);
		stopText = makeLabel("STOP GAME  - ESC", S4);
		pausedText = makeLabel("GAME PAUSED", S1);
		noFocusText = makeLabel("CLICK TO FOCUS", S1);
		winText = makeLabel("YOU WIN", S1);
		loseText = makeLabel("YOU LOSE", S1);
		drawText = makeLabel("IT'S A DRAW", S1);
		leftWinText = makeLabel("LEFT PLAYER WINS", S1);
		rightWinText = makeLabel("RIGHT PLAYER WINS", S1);

		size.x = missionText.getHalfSize().x * 2 + 2 * S1;
		size.y = 95;
	}

	private void drawControls(final Context2d context, final Game game) {
		if (game.getPlayers().size() > 1) {
			playerText1.draw(context);
			context.translate(0, 0.5 * S2 + S4 + 0.5 * S4);
		}
		moveText1.draw(context);
		context.translate(0, 1.5 * S4);
		fireText1.draw(context);
		context.translate(0, 1.5 * S4);
		jumpText1.draw(context);
		context.translate(0, 0.5 * S4 + S1 + 0.5 * S2);
		if (game.getPlayers().size() > 1) {
			playerText2.draw(context);
			context.translate(0, 0.5 * S2 + S4 + 0.5 * S4);
			moveText2.draw(context);
			context.translate(0, 1.5 * S4);
			fireText2.draw(context);
			context.translate(0, 1.5 * S4);
			jumpText2.draw(context);
			context.translate(0, S4 + S1);
		}
		muteText.draw(context);
		context.translate(0, 1.5 * S4);
		stopText.draw(context);
	}

	private void drawGameOver(final Context2d context, final Game game) {
		if (!game.isPVP()) {
			if (game.getActivePlayerTeams() > 0) {
				winText.draw(context);
			} else if (game.getActiveDroneTeams() > 0) {
				loseText.draw(context);
			} else {
				drawText.draw(context);
			}
			context.translate(0, 0.5 * S1 + 1.5 * S1 + 0.5 * S3);
			updateDronesDestroyedText(game);
			dronesDestroyedText.draw(context);
		} else {
			if (game.getPlayers().get(0).isActive()) {
				leftWinText.draw(context);
			} else if (game.getPlayers().get(1).isActive()) {
				rightWinText.draw(context);
			} else {
				drawText.draw(context);
			}
		}
		context.translate(0, 0.5 * S1 + 1.5 * S1 + 0.5 * S3);
		updateTimeText(game);
		timeText.draw(context);
		context.translate(0, 0.5 * S3 + 1.5 * S1 + 0.5 * S3);
		continueText.draw(context);
	}

	private void drawIntro(final Context2d context, final Game game) {
		if (!game.isPVP()) {
			missionText.draw(context);
		} else {
			duelText.draw(context);
		}
		context.translate(0, 0.5 * S1 + 1.5 * S1 + 0.5 * S3);
		startText.draw(context);
		context.translate(0, 0.5 * S3 + 1.5 * S1 + 0.5 * S2);
		drawControls(context, game);
	}

	private void drawPaused(final Context2d context, final Game game) {
		pausedText.draw(context);
		context.translate(0, 0.5 * S1 + 1.5 * S1 + 0.5 * S3);
		continueText.draw(context);
		context.translate(0, 0.5 * S3 + 1.5 * S1 + 0.5 * S2);
		drawControls(context, game);
	}

	private PixelLabel makeLabel(final String text, final float size) {
		return new PixelLabel(text, Config.FONT_MONOSPACE,
				Config.SCREEN_TEXT_COLOR, new VectorF(size / 5),
				Config.PIXEL_PADDING_FRACTION);
	}

	@Override
	public void update(final Game game) {
		final Canvas canvas = game.getGamePanel().getCanvas();
		final int w = canvas.getCoordinateSpaceWidth();
		final int h = canvas.getCoordinateSpaceHeight();
		final double scale = Math.min(w / size.x, h / size.y) / 1.25;

		if (scale > 0) {
			final Context2d context = canvas.getContext2d();
			context.save();

			context.setGlobalAlpha(0.75);
			context.setFillStyle("#000000");
			context.fillRect(0, 0, w, h);
			context.setGlobalAlpha(1);

			context.translate(w / 2, h / 2);
			context.scale(scale, scale);

			// context.setFillStyle("#444444");
			// context.fillRect(-size.x / 2, -size.y / 2, size.x, size.y);

			if (!game.hasFocus()) {
				noFocusText.draw(context);
			} else {
				context.translate(0, -size.y / 2 + 1.5 * S1);

				switch (game.getState()) {
				case Game.INTRO_STATE:
					drawIntro(context, game);
					break;
				case Game.PAUSE_STATE:
					drawPaused(context, game);
					break;
				case Game.GAME_OVER_STATE:
					drawGameOver(context, game);
					break;
				}
			}
			context.restore();
		}
	}

	private void updateDronesDestroyedText(final Game game) {
		if (dronesDestroyedText == null) {
			final int droneCount = game.getDrones().size();
			final int dronesDestroyed = droneCount - game.countActiveDrones();
			dronesDestroyedText = makeLabel(dronesDestroyed + " OF "
					+ droneCount + " DRONES DESTROYED", S2);
		}
	}

	private void updateTimeText(final Game game) {
		final double runTime = game.getTimeSpec().getRunTime();
		if (time != runTime) {
			time = runTime;
			timeText = makeLabel("TIME: "
					+ NumberFormat.getFormat("#.00").format(time) + " SECONDS",
					S3);
		}
	}
}
