package com.harmboschloo.starfighter.titlescreen;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.harmboschloo.boxy.graphics.pixel.text.PixelFont;
import com.harmboschloo.boxy.graphics.pixel.text.PixelFont5x5Default;
import com.harmboschloo.boxy.graphics.pixel.text.PixelLabel;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.ui.CanvasAppPanel;
import com.harmboschloo.boxy.ui.EmptyAnchor;
import com.harmboschloo.boxy.ui.EmptyButton;
import com.harmboschloo.starfighter.app.Controller;
import com.harmboschloo.starfighter.app.Starfighter;
import com.harmboschloo.starfighter.game.Config;

public class TitleScreen implements Controller {
	public static final int ONE_PLAYER_POSITION = 0;
	public static final int TWO_PLAYER_COOP_POSITION = 1;
	public static final int TWO_PLAYER_DUEL_POSITION = 2;
	private static final int POSITION_COUNT = 3;

	private final CanvasAppPanel gamePanel;
	private final PixelLabel starfighterText;
	private final PixelLabel previewText;
	private final PixelLabel playerText;
	private final PixelLabel playerCoopText;
	private final PixelLabel playerDuelText;
	private final PixelLabel harmmadeText;
	private final PixelLabel indieDBText;
	private final PixelLabel copyText;
	private final AreaF playerArea = new AreaF();
	private final AreaF playerCoopArea = new AreaF();
	private final AreaF playerDuelArea = new AreaF();
	private final AreaF harmmadeArea = new AreaF();
	private final AreaF indieDBArea = new AreaF();
	private final Button playerButton;
	private final Button playerCoopButton;
	private final Button playerDuelButton;
	private final Anchor harmmadeAnchor;
	private final Anchor indieDBAnchor;
	private final ResizeHandler resizeHandler;
	private final KeyDownHandler keyDownHandler;
	private final VectorF size = new VectorF(70, 32);
	private double scale = 1;
	private int pointerPosition = ONE_PLAYER_POSITION;

	public TitleScreen(final CanvasAppPanel gamePanel) {
		this.gamePanel = gamePanel;

		harmmadeAnchor = new EmptyAnchor("http://www.harmmade.com/", "_top");
		harmmadeAnchor.setTitle("More games at harmmade.com!");
		indieDBAnchor = new EmptyAnchor(
				"http://www.indiedb.com/games/starfighter/", "_top");
		indieDBAnchor
				.setTitle("Follow, share, news, screens, videos at indiedb.com!");
		playerButton = new EmptyButton();
		playerCoopButton = new EmptyButton();
		playerDuelButton = new EmptyButton();

		final PixelFont font = new PixelFont5x5Default();
		starfighterText = new PixelLabel("STARFIGHTER", font, CssColor.make(75,
				150, 225), new VectorF(1), Config.PIXEL_PADDING_FRACTION);
		previewText = new PixelLabel("PREVIEW", font, CssColor.make(225, 0, 0),
				new VectorF(0.5f), Config.PIXEL_PADDING_FRACTION);
		playerText = new PixelLabel("1 PLAYER", font, CssColor.make(255, 255,
				255), new VectorF(0.25f), Config.PIXEL_PADDING_FRACTION);
		playerCoopText = new PixelLabel("2 PLAYER CO-OP", font, CssColor.make(
				255, 255, 255), new VectorF(0.25f),
				Config.PIXEL_PADDING_FRACTION);
		playerDuelText = new PixelLabel("2 PLAYER DUEL", font, CssColor.make(
				255, 255, 255), new VectorF(0.25f),
				Config.PIXEL_PADDING_FRACTION);
		harmmadeText = new PixelLabel("HARMMADE.COM", font, CssColor.make(255,
				185, 69), new VectorF(0.25f), Config.PIXEL_PADDING_FRACTION);
		indieDBText = new PixelLabel("FOLLOW/SHARE/NEWS", font, CssColor.make(
				72, 148, 16), new VectorF(0.25f), Config.PIXEL_PADDING_FRACTION);
		copyText = new PixelLabel("(C) 2011 HARM BOSCHLOO", font,
				CssColor.make(200, 200, 200), new VectorF(0.25f),
				Config.PIXEL_PADDING_FRACTION);

		playerArea.min.set(-5.5f, -0.5f);
		playerArea.setWidth(22.5f);
		playerArea.setHeight(2.f);

		playerCoopArea.min.set(-5.5f, 1.5f);
		playerCoopArea.setWidth(22.5f);
		playerCoopArea.setHeight(2.f);

		playerDuelArea.min.set(-5.5f, 3.5f);
		playerDuelArea.setWidth(22.5f);
		playerDuelArea.setHeight(2.f);

		harmmadeArea.min.set(-21, 8);
		harmmadeArea.setWidth(18);
		harmmadeArea.setHeight(2.f);

		indieDBArea.min.set(-1, 8);
		indieDBArea.setWidth(26);
		indieDBArea.setHeight(2.f);

		resizeHandler = new ResizeHandler() {
			@Override
			public void onResize(final ResizeEvent event) {
				updateAll();
				redraw();
			}
		};

		keyDownHandler = new KeyDownHandler() {
			@Override
			public void onKeyDown(final KeyDownEvent event) {
				doKeyDown(event);
			}
		};

		playerButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				startGame1();
			}
		});

		playerButton.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(final MouseOverEvent event) {
				setPointerPosition(ONE_PLAYER_POSITION);
				redrawPointer();
			}
		});

		playerButton.addKeyDownHandler(keyDownHandler);

		playerCoopButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				startGame2Coop();
			}
		});

		playerCoopButton.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(final MouseOverEvent event) {
				setPointerPosition(TWO_PLAYER_COOP_POSITION);
				redrawPointer();
			}
		});

		playerCoopButton.addKeyDownHandler(keyDownHandler);

		playerDuelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				startGame2Duel();
			}
		});

		playerDuelButton.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(final MouseOverEvent event) {
				setPointerPosition(TWO_PLAYER_DUEL_POSITION);
				redrawPointer();
			}
		});

		playerDuelButton.addKeyDownHandler(keyDownHandler);

		harmmadeAnchor.addKeyDownHandler(keyDownHandler);
		indieDBAnchor.addKeyDownHandler(keyDownHandler);
	}

	public void doKeyDown(final KeyDownEvent event) {
		switch (event.getNativeKeyCode()) {
		case KeyCodes.KEY_UP:
			pointerPositionUp();
			redrawPointer();
			break;
		case KeyCodes.KEY_DOWN:
			pointerPositionDown();
			redrawPointer();
			break;
		case KeyCodes.KEY_ENTER:
			startGame();
			break;
		default:
			break;
		}
	}

	public void pointerPositionDown() {
		setPointerPosition((pointerPosition + 1) % POSITION_COUNT);
	}

	public void pointerPositionUp() {
		setPointerPosition(pointerPosition == 0 ? (POSITION_COUNT - 1)
				: (pointerPosition - 1) % POSITION_COUNT);
	}

	private void redraw() {
		if (scale > 0) {
			final int w = gamePanel.getWidth();
			final int h = gamePanel.getHeight();

			final Context2d context = gamePanel.getContext();
			context.save();

			context.clearRect(0, 0, w, h);

			// context.setFillStyle("#00ff00");
			// context.fillRect(mouse.x - 5, mouse.y - 5, 10, 10);

			context.translate(w / 2.f, h / 2.f);
			context.scale(scale, scale);

			// debug
			// context.setFillStyle("#333333");
			// context.fillRect(-size.x / 2, -size.y / 2, size.x, size.y);
			// context.setFillStyle("#555555");
			// context.fillRect(playerArea.min.x, playerArea.min.y,
			// playerArea.getWidth(), playerArea.getHeight());
			// context.fillRect(playerCoopArea.min.x, playerCoopArea.min.y,
			// playerCoopArea.getWidth(), playerCoopArea.getHeight());
			// context.fillRect(playerDuelArea.min.x, playerDuelArea.min.y,
			// playerDuelArea.getWidth(), playerDuelArea.getHeight());
			// context.fillRect(harmmadeArea.min.x, harmmadeArea.min.y,
			// harmmadeArea.getWidth(), harmmadeArea.getHeight());
			// context.fillRect(indieDBArea.min.x, indieDBArea.min.y,
			// indieDBArea.getWidth(), indieDBArea.getHeight());
			// context.setFillStyle("#0000ff");
			// context.fillRect(pointer.x - 0.1, pointer.y - 0.1, 0.2, 0.2);

			// Title //

			context.save();

			context.translate(0, -10);
			starfighterText.draw(context);

			context.translate(2, 3.5);
			context.rotate(-Math.PI / 12);
			context.setGlobalAlpha(0.7);
			previewText.draw(context);

			context.restore();

			// Menu //

			context.save();

			context.translate(2.5, 0.5);
			playerText.draw(context);

			context.translate(3.9, 2);
			playerCoopText.draw(context);

			context.translate(-0.5, 2);
			playerDuelText.draw(context);

			context.restore();

			// Footer //

			context.translate(-12, 9);
			harmmadeText.draw(context);

			context.translate(24, 0);
			indieDBText.draw(context);

			context.translate(-12, 3);
			copyText.draw(context);

			context.restore();

			redrawPointer();
		}
	}

	private void redrawPointer() {
		if (scale > 0) {
			final int w = gamePanel.getWidth();
			final int h = gamePanel.getHeight();

			final Context2d context = gamePanel.getContext();
			context.save();

			context.translate(w / 2.f, h / 2.f);
			context.scale(scale, scale);

			context.setFillStyle("#000000");
			context.fillRect(-5.5, -0.5, 2, 6);

			context.setFillStyle("#ffffff");

			switch (pointerPosition) {
			case ONE_PLAYER_POSITION:
				context.fillRect(-5, 0, 1, 1);
				break;
			case TWO_PLAYER_COOP_POSITION:
				context.fillRect(-5, 2, 1, 1);
				break;
			case TWO_PLAYER_DUEL_POSITION:
				context.fillRect(-5, 4, 1, 1);
				break;
			default:
				break;
			}

			context.restore();
		}
	}

	@Override
	public void run() {
		gamePanel.addResizeHandler(resizeHandler);
		gamePanel.addKeyDownHandler(keyDownHandler);
		gamePanel.add(playerButton);
		gamePanel.add(playerCoopButton);
		gamePanel.add(playerDuelButton);
		gamePanel.add(harmmadeAnchor);
		gamePanel.add(indieDBAnchor);

		updateAll();
		redraw();

		gamePanel.focus();
	}

	public void setPointerPosition(final int position) {
		pointerPosition = position;
	}

	private void startGame() {
		switch (pointerPosition) {
		case ONE_PLAYER_POSITION:
			startGame1();
			break;
		case TWO_PLAYER_COOP_POSITION:
			startGame2Coop();
			break;
		case TWO_PLAYER_DUEL_POSITION:
			startGame2Duel();
			break;
		default:
			break;
		}
	}

	private void startGame1() {
		stop();
		Starfighter.goToOnePlayerGame();
	}

	private void startGame2Coop() {
		stop();
		Starfighter.goToTwoPlayerCoopGame();
	}

	private void startGame2Duel() {
		stop();
		Starfighter.goToTwoPlayerDuelGame();
	}

	@Override
	public void stop() {
		gamePanel.clearHandlers();
		playerButton.removeFromParent();
		playerCoopButton.removeFromParent();
		playerDuelButton.removeFromParent();
		harmmadeAnchor.removeFromParent();
		indieDBAnchor.removeFromParent();
	}

	private void updateAll() {
		updateScale();
		updateWidget(playerButton, playerArea);
		updateWidget(playerCoopButton, playerCoopArea);
		updateWidget(playerDuelButton, playerDuelArea);
		updateWidget(harmmadeAnchor, harmmadeArea);
		updateWidget(indieDBAnchor, indieDBArea);
	}

	private void updateScale() {
		scale = Math.min(gamePanel.getWidth() / size.x, gamePanel.getHeight()
				/ size.y);
	}

	private void updateWidget(final Widget widget, final AreaF area) {
		final VectorF halfSize = new VectorF(gamePanel.getWidth() / 2.f,
				gamePanel.getHeight() / 2.f);
		final VectorF min = new VectorF(area.min);
		min.multiplyBy((float) scale);
		min.add(halfSize);
		final VectorF max = new VectorF(area.max);
		max.multiplyBy((float) scale);
		max.add(halfSize);
		gamePanel.setWidgetLeftRight(widget, min.x, Unit.PX, halfSize.x * 2
				- max.x, Unit.PX);
		gamePanel.setWidgetTopBottom(widget, min.y, Unit.PX, halfSize.y * 2
				- max.y, Unit.PX);

		// FF fix
		widget.getElement().getStyle().setWidth(100, Unit.PCT);
		widget.getElement().getStyle().setWidth(100, Unit.PCT);
	}

}
