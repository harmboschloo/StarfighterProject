package com.harmboschloo.starfighter.game.graphics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.harmboschloo.boxy.graphics.viewport.DrawBoxManagerStatsDrawer;
import com.harmboschloo.boxy.graphics.viewport.FPSDrawer;
import com.harmboschloo.boxy.graphics.viewport.Viewport;
import com.harmboschloo.boxy.graphics.viewport.ViewportClearer;
import com.harmboschloo.boxy.graphics.viewport.ViewportDrawManager;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.ui.CanvasAppPanel;
import com.harmboschloo.boxy.ui.CanvasNotSupportedException;
import com.harmboschloo.boxy.util.HasUpdate;
import com.harmboschloo.boxy.util.SkipUpdateGroup;
import com.harmboschloo.boxy.util.UpdateGroup;
import com.harmboschloo.starfighter.game.Config;
import com.harmboschloo.starfighter.game.Game;
import com.harmboschloo.starfighter.game.HasGameUpdate;
import com.harmboschloo.starfighter.game.Pilot;
import com.harmboschloo.starfighter.game.Player;
import com.harmboschloo.starfighter.game.ai.EvadeDrone;
import com.harmboschloo.starfighter.game.object.Asteroid;
import com.harmboschloo.starfighter.game.object.Bullet;
import com.harmboschloo.starfighter.game.object.Explosion;
import com.harmboschloo.starfighter.game.object.Ship;

public class GameGraphics implements HasGameUpdate {
	private final CanvasAppPanel gamePanel;
	private final UpdateGroup drawUpdateGroup = new UpdateGroup();
	private final ArrayList<SortableDrawBox> drawBoxes = new ArrayList<SortableDrawBox>();
	private final ArrayList<Viewport> viewports = new ArrayList<Viewport>();
	private final OverlayScreen overlayScreen;

	public GameGraphics(final Game game) {
		if (!Canvas.isSupported()) {
			throw new CanvasNotSupportedException();
		}

		gamePanel = game.getGamePanel();
		overlayScreen = new OverlayScreen();

		int playerIndex = 0;
		for (final Player player : game.getPlayers()) {
			drawUpdateGroup.add(createDrawGroup(playerIndex, player.getShip(),
					game));
			++playerIndex;
		}

		for (final Asteroid asteroid : game.getAsteroids()) {
			drawBoxes.add(new AsteroidDrawBox(asteroid, DrawOrder.ASTEROID_BASE
					+ asteroid.getLevel()));
		}

		for (final Pilot pilot : game.getDrones()) {
			drawBoxes
					.add(new ShipDrawBox(pilot.getShip(), DrawOrder.DRONE_SHIP));
		}

		for (final Player player : game.getPlayers()) {
			drawBoxes.add(new ShipDrawBox(player.getShip(),
					DrawOrder.PLAYER_SHIP));
		}

		for (final Explosion explosion : game.getExplosions()) {
			drawBoxes.add(new ExplosionDrawBox(explosion, DrawOrder.EXPLOSION));
		}

		orderDrawBoxes();
	}

	public void addAsteroidDrawBox(final Asteroid asteroid) {
		addDrawBox(new AsteroidDrawBox(asteroid, DrawOrder.ASTEROID_BASE
				+ asteroid.getLevel()));
	}

	public void addBulletDrawBox(final Bullet bullet) {
		addDrawBox(new BulletDrawBox(bullet, DrawOrder.BULLET));
	}

	private void addDrawBox(final SortableDrawBox drawBox) {
		drawBoxes.add(drawBox);
		orderDrawBoxes();
	}

	public void addDroneShipDrawBox(final Pilot pilot) {
		addDrawBox(new ShipDrawBox(pilot.getShip(), DrawOrder.DRONE_SHIP));
	}

	public void addExplosionsDrawBox(final Explosion explosion) {
		addDrawBox(new ExplosionDrawBox(explosion, DrawOrder.EXPLOSION));
	}

	public void addPlayerShipDrawBox(final Player player) {
		addDrawBox(new ShipDrawBox(player.getShip(), DrawOrder.PLAYER_SHIP));
	}

	private HasUpdate createDrawGroup(final int playerIndex, final Ship ship,
			final Game game) {
		final AreaF wrapArea = game.getWrapArea();
		final VectorF viewportBaseSize = game.getViewportBaseSize();

		final Viewport viewport = new Viewport(gamePanel.getContext(),
				viewportBaseSize);
		viewport.add(new ViewportClearer());

		final VectorI gridSize = new VectorI();
		final int n = Config.NUMBER_OF_VISIBLE_GRID_LINES;
		gridSize.x = (int) Math.ceil(wrapArea.getWidth() * n
				/ viewportBaseSize.x);
		gridSize.y = (int) Math.ceil(wrapArea.getHeight() * n
				/ viewportBaseSize.y);
		viewport.add(new GridDrawer(gridSize, Config.BACKGROUND_GRID_COLORS[1]
				.makeCssColor(), wrapArea));

		final ViewportDrawManager drawManager = new ViewportDrawManager(
				wrapArea, drawBoxes);
		viewport.add(drawManager);

		if (Config.DEBUG_ENABLED) {
			for (final EvadeDrone drone : game.getDrones()) {
				if (drone.getShip() == ship) {
					viewport.add(new AIDebugDrawer(drone.getAI()));
					break;
				}
			}
			for (final Player player : game.getPlayers()) {
				if (player.getShip() == ship && player instanceof EvadeDrone) {
					viewport.add(new AIDebugDrawer(((EvadeDrone) player)
							.getAI()));
					break;
				}
			}
			viewport.add(new FPSDrawer(10, 10, CssColor.make(255, 255, 255)));
			viewport.add(new DrawBoxManagerStatsDrawer(10, 20, CssColor.make(
					255, 255, 255), drawManager));
		}

		viewport.add(new ShipLocationTracker(game.getShips(), wrapArea));
		viewport.add(new ShipStateIcon(ship));
		viewports.add(viewport);

		final UpdateGroup drawGroup = new SkipUpdateGroup(1, playerIndex);
		// drawGroup.add(new ViewportCenterController(viewport,
		// ship.getMassCenter()));
		drawGroup
				.add(new ShipViewportCenterController(ship, viewport, wrapArea));
		drawGroup.add(viewport);

		return drawGroup;
	}

	public void orderDrawBoxes() {
		final Comparator<SortableDrawBox> comparator = new Comparator<SortableDrawBox>() {
			@Override
			public int compare(final SortableDrawBox box1,
					final SortableDrawBox box2) {
				if (box1.getDrawOrder() == box2.getDrawOrder()) {
					return 0;
				} else if (box1.getDrawOrder() < box2.getDrawOrder()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		Collections.sort(drawBoxes, comparator);
	}

	public void redraw(final Game game) {
		gamePanel.clearCanvas();

		// draw twice because every draw group skips one update
		drawUpdateGroup.update();
		drawUpdateGroup.update();

		updateOverlayScreen(game);
	}

	@Override
	public void update(final Game game) {
		drawUpdateGroup.update();

		updateOverlayScreen(game);
	}

	public void updateClipAreas() {
		gamePanel.clearCanvas();

		if (Config.SQUARE_VIEWPORTS) {
			final float width = gamePanel.getWidth();
			final float height = gamePanel.getHeight();
			final int n = viewports.size();
			final float size = Math.min(width / n, height);
			final float widthSpacing = (width - n * size) / (2 * n);
			final float heightSpacing = (height - size) / 2;
			final float fullWidth = size + 2 * widthSpacing;
			int i = 0;
			for (final Viewport viewport : viewports) {
				viewport.getClipArea().min.set(i * fullWidth + widthSpacing,
						heightSpacing);
				viewport.getClipArea().max.set((i + 1) * fullWidth
						- widthSpacing, heightSpacing + size);
				viewport.updateBox();
				++i;
			}
		} else {
			final float width = gamePanel.getWidth() / viewports.size();
			final float height = gamePanel.getHeight();
			int i = 0;
			for (final Viewport viewport : viewports) {
				viewport.getClipArea().min.set(i * width, 0);
				viewport.getClipArea().max.set((i + 1) * width, height);
				viewport.updateBox();
				++i;
			}
		}
	}

	private void updateOverlayScreen(final Game game) {
		if (!game.isPlaying() || !game.hasFocus()) {
			overlayScreen.update(game);
		}
	}
}
