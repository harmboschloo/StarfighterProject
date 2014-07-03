package com.harmboschloo.starfighter.app;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.harmboschloo.boxy.ui.CanvasAppPanel;
import com.harmboschloo.starfighter.game.AITestGame;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.OnePlayerGame;
import com.harmboschloo.starfighter.game.TwoPlayerCoopGame;
import com.harmboschloo.starfighter.game.TwoPlayerDuelGame;
import com.harmboschloo.starfighter.titlescreen.TitleScreen;

public class Starfighter extends LayoutPanel implements EntryPoint,
		ValueChangeHandler<String> {
	public static final String VERSION = "PV1-20111019";

	private static final String titleScreenToken = "";
	private static final String onePlayerToken = "1p";
	private static final String twoPlayerCoopToken = "2pcoop";
	private static final String twoPlayerDuelToken = "2pduel";
	private static final int FOOTER_HEIGHT = 0;

	public static String currentMode() {
		return History.getToken();
	}

	public static void goToOnePlayerGame() {
		History.newItem(onePlayerToken);
	}

	public static void goToTitleScreen() {
		History.newItem(titleScreenToken);
	}

	public static void goToTwoPlayerCoopGame() {
		History.newItem(twoPlayerCoopToken);
	}

	public static void goToTwoPlayerDuelGame() {
		History.newItem(twoPlayerDuelToken);
	}

	private CanvasAppPanel gamePanel = null;
	private TitleScreen titleScreen = null;
	private Controller currentController = null;

	@Override
	public void onModuleLoad() {
		try {
			gamePanel = new CanvasAppPanel();
			// gamePanel.getElement().getStyle().setBackgroundColor("#000000");
			add(gamePanel);
			setWidgetLeftRight(gamePanel, 0, Unit.PX, 0, Unit.PX);
			setWidgetTopBottom(gamePanel, 0, Unit.PX, FOOTER_HEIGHT, Unit.PX);

			titleScreen = new TitleScreen(gamePanel);

			History.addValueChangeHandler(this);
		} catch (final Throwable exception) {
			final LoadError error = new LoadError();
			add(error);
			setWidgetLeftRight(error, 0, Unit.PX, 0, Unit.PX);
			setWidgetTopBottom(error, 0, Unit.PX, FOOTER_HEIGHT, Unit.PX);
		}

		// getElement().getStyle().setBackgroundColor("#000000");
		RootLayoutPanel.get().add(this);

		// remove pre load info
		final Element element = Document.get().getElementById("sfPreLoadInfo");
		if (element != null) {
			element.removeFromParent();
		}

		History.fireCurrentHistoryState();

		// resize code after module is loaded and offset width/height are set
		scheduleResize();
	}

	@Override
	public void onResize() {
		super.onResize();
	}

	@Override
	public void onValueChange(final ValueChangeEvent<String> event) {
		stop();

		final String token = event.getValue();

		if (Config.DEBUG_ENABLED) {
			if (token.equals("ai")) {
				currentController = new AITestGame(gamePanel);
			}
		}

		if (token.equals(titleScreenToken)) {
			currentController = titleScreen;
		} else if (token.equals(onePlayerToken)) {
			titleScreen.setPointerPosition(TitleScreen.ONE_PLAYER_POSITION);
			currentController = new OnePlayerGame(gamePanel);
		} else if (token.equals(twoPlayerCoopToken)) {
			titleScreen
					.setPointerPosition(TitleScreen.TWO_PLAYER_COOP_POSITION);
			currentController = new TwoPlayerCoopGame(gamePanel);
		} else if (token.equals(twoPlayerDuelToken)) {
			titleScreen
					.setPointerPosition(TitleScreen.TWO_PLAYER_DUEL_POSITION);
			currentController = new TwoPlayerDuelGame(gamePanel);
		}

		if (currentController != null) {
			currentController.run();
		} else {
			goToTitleScreen();
		}
	}

	private void scheduleResize() {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				onResize();
			}
		});
	}

	public void stop() {
		gamePanel.clearHandlers();
		if (currentController != null) {
			currentController.stop();
			currentController = null;
		}
	}
}
