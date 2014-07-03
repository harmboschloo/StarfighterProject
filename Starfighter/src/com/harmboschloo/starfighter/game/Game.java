package com.harmboschloo.starfighter.game;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.harmboschloo.boxy.graphics.color.Color;
import com.harmboschloo.boxy.math.AreaF;
import com.harmboschloo.boxy.math.Random;
import com.harmboschloo.boxy.math.VectorF;
import com.harmboschloo.boxy.math.VectorI;
import com.harmboschloo.boxy.ui.CanvasAppPanel;
import com.harmboschloo.boxy.util.HasUpdate;
import com.harmboschloo.boxy.util.StepTimer;
import com.harmboschloo.boxy.util.TimeSpec;
import com.harmboschloo.starfighter.app.Controller;
import com.harmboschloo.starfighter.app.Starfighter;
import com.harmboschloo.starfighter.app.Stats;
import com.harmboschloo.starfighter.game.ai.AIPlayer;
import com.harmboschloo.starfighter.game.ai.DroneSensors;
import com.harmboschloo.starfighter.game.ai.EvadeDrone;
import com.harmboschloo.starfighter.game.collision.AsteroidShipCollider;
import com.harmboschloo.starfighter.game.collision.BulletAsteroidCollider;
import com.harmboschloo.starfighter.game.collision.BulletBulletCollider;
import com.harmboschloo.starfighter.game.collision.ShipBulletCollider;
import com.harmboschloo.starfighter.game.collision.ShipShipCollider;
import com.harmboschloo.starfighter.game.control.LeftKeyControlHandler;
import com.harmboschloo.starfighter.game.control.RightKeyControlHandler;
import com.harmboschloo.starfighter.game.graphics.GameGraphics;
import com.harmboschloo.starfighter.game.object.Asteroid;
import com.harmboschloo.starfighter.game.object.BigAsteroid;
import com.harmboschloo.starfighter.game.object.DroneShip;
import com.harmboschloo.starfighter.game.object.Explosion;
import com.harmboschloo.starfighter.game.object.Fighter;
import com.harmboschloo.starfighter.game.object.GameObject;
import com.harmboschloo.starfighter.game.object.MediumAsteroid;
import com.harmboschloo.starfighter.game.object.Ship;
import com.harmboschloo.starfighter.game.object.ShipControls;
import com.harmboschloo.starfighter.game.object.SmallAsteroid;
import com.harmboschloo.starfighter.game.sound.DummyShipSounds;
import com.harmboschloo.starfighter.game.sound.PlayerShipSounds;
import com.harmboschloo.starfighter.game.sound.SoundManager;

public class Game implements Controller, HasUpdate {
	public static final int INTRO_STATE = 0;
	public static final int PLAY_STATE = 1;
	public static final int PAUSE_STATE = 2;
	public static final int GAME_OVER_STATE = 4;

	private final CanvasAppPanel gamePanel;
	private GameGraphics graphics = null;
	private final GameUpdateGroup gameUpdateGroup = new GameUpdateGroup();
	private final ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	private final ArrayList<EvadeDrone> drones = new ArrayList<EvadeDrone>();
	private final ArrayList<Player> humanPlayers = new ArrayList<Player>();
	private final ArrayList<Player> players = new ArrayList<Player>();
	private final ArrayList<Ship> ships = new ArrayList<Ship>();
	private final ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	private final ArrayList<Team> teams = new ArrayList<Team>();
	private final StepTimer timer;
	private final AreaF wrapArea = new AreaF();
	private final VectorF viewportBaseSize = new VectorF();
	private boolean hasFocus = false;
	private float gameOverTime = 0;
	private int state = INTRO_STATE;
	private boolean pvp = false;
	private int activeDroneTeams = 0;
	private int activePlayerTeams = 0;

	public Game(final CanvasAppPanel gamePanel) {
		this.gamePanel = gamePanel;
		timer = new StepTimer() {
			@Override
			public void step() {
				update();
			}
		};
		timer.setStepCheckPeriodMs(5);
		timer.setAutoResetSteps(2);

		create();
	}

	public Pilot addAIDrone(final float sensorRange) {
		final Ship ship = new DroneShip(nextDroneColor(), new DummyShipSounds());
		ships.add(ship);
		final EvadeDrone drone = new EvadeDrone(ship, sensorRange);
		final DroneSensors sensors = drone.getAI().getSensors();
		final int n = Config.DRONE_COLORS.length;
		final int r = 2 * n - drones.size() % n;
		sensors.setSenseCount(drones.size() % r);
		sensors.setSenseRate(r);
		// GWT.log("count/rate: " + sensors.getSenseCount() + "/"
		// + sensors.getSenseRate());
		drones.add(drone);
		if (graphics != null) {
			graphics.addDroneShipDrawBox(drone);
		}
		return drone;
	}

	public Player addAIPlayer(final float sensorRange) {
		final Ship ship = new DroneShip(nextPlayerColor(),
				new PlayerShipSounds(players.size()));
		ships.add(ship);
		final Player player = new AIPlayer(ship, sensorRange);
		players.add(player);
		if (graphics != null) {
			graphics.addPlayerShipDrawBox(player);
		}
		return player;
	}

	public void addAsteroid(final Asteroid asteroid) {
		asteroids.add(asteroid);
		if (graphics != null) {
			graphics.addAsteroidDrawBox(asteroid);
		}
	}

	public Asteroid addBigAsteroid(final Color color) {
		final Asteroid asteroid = new BigAsteroid(color);
		addAsteroid(asteroid);
		return asteroid;
	}

	public void addBigAsteroids(final int n) {
		for (int i = 0; i < n; ++i) {
			addBigAsteroid(nextAsteroidColor());
		}
	}

	public void addExplosion(final VectorF center, final GameObject object1,
			final GameObject object2) {
		final Color color = Color.interpolate(object1.getColor(),
				object2.getColor(), 0.5f);
		final VectorF velocity = object1.getVelocity().plus(
				object2.getVelocity());
		velocity.divideBy(4); // average velocities, then loose half

		for (final Explosion explosion : explosions) {
			if (!explosion.isActive()) {
				explosion.getCenter().copy(center);
				explosion.getVelocity().copy(velocity);
				explosion.setColor(color.makeCssColor());
				explosion.setActive(true);
				return;
			}
		}

		final Explosion explosion = new Explosion(center, velocity,
				color.makeCssColor());
		explosions.add(explosion);
		if (graphics != null) {
			graphics.addExplosionsDrawBox(explosion);
		}
	}

	public Player addHumanPlayer() {
		final Ship ship = new Fighter(nextPlayerColor(), new PlayerShipSounds(
				players.size()));
		ships.add(ship);
		final Player player = new HumanPlayer(ship);
		players.add(player);
		humanPlayers.add(player);
		if (graphics != null) {
			graphics.addPlayerShipDrawBox(player);
		}
		return player;
	}

	public Asteroid addMediumAsteroid(final Color color) {
		final Asteroid asteroid = new MediumAsteroid(color);
		addAsteroid(asteroid);
		return asteroid;
	}

	public void addMediumAsteroids(final int n) {
		for (int i = 0; i < n; ++i) {
			addMediumAsteroid(nextAsteroidColor());
		}
	}

	public Asteroid addSmallAsteroid(final Color color) {
		final Asteroid asteroid = new SmallAsteroid(color);
		addAsteroid(asteroid);
		return asteroid;
	}

	public void addSmallAsteroids(final int n) {
		for (int i = 0; i < n; ++i) {
			addSmallAsteroid(nextAsteroidColor());
		}
	}

	public Team addTeam(final boolean isPlayerTeam) {
		final Team team = new Team(isPlayerTeam);
		teams.add(team);
		return team;
	}

	public void animateAsteroids() {
		for (final Asteroid asteroid : asteroids) {
			final float area = asteroid.getHalfSize().getArea();
			final float velocityRange = (float) (2200 / Math.pow(area, 0.6));
			final float angularVelocityRange = (float) (25 / Math
					.pow(area, 0.6));
			asteroid.getVelocity().set(
					Random.range(-velocityRange, velocityRange),
					Random.range(-velocityRange, velocityRange));
			asteroid.setAngularVelocity(Random.range(-angularVelocityRange,
					angularVelocityRange));
		}
	}

	public boolean atGameOver() {
		return (state == GAME_OVER_STATE);
	}

	public boolean atIntro() {
		return (state == INTRO_STATE);
	}

	protected void bindControllers() {
		final int n = humanPlayers.size();
		for (int i = 0; i < n; ++i) {
			final Ship ship = humanPlayers.get(i).getShip();
			if (n == 1) {
				final ShipControls controls = ship.getControls();
				new RightKeyControlHandler(controls).bindTo(gamePanel);
				new LeftKeyControlHandler(controls).bindTo(gamePanel);
			} else if (n > 1) {
				if (i == 0) {
					new LeftKeyControlHandler(ship.getControls())
							.bindTo(gamePanel);
				} else if (i == 1) {
					new RightKeyControlHandler(ship.getControls())
							.bindTo(gamePanel);
				}
			}
		}
	}

	private void checkGameOver() {
		activePlayerTeams = 0;
		activeDroneTeams = 0;
		for (final Team team : teams) {
			if (team.checkActive()) {
				if (team.isPlayerTeam()) {
					++activePlayerTeams;
				} else {
					++activeDroneTeams;
				}
			}
		}

		if (pvp) {
			if (activePlayerTeams <= 1) {
				gameOver();
			}
		} else {
			if (activePlayerTeams == 0 || activeDroneTeams == 0) {
				gameOver();
			}
		}
	}

	public int countActiveDrones() {
		int count = 0;
		for (final EvadeDrone drone : drones) {
			if (drone.isActive()) {
				++count;
			}
		}
		return count;
	}

	public int countActivePlayers() {
		int count = 0;
		for (final Player playser : players) {
			if (playser.isActive()) {
				++count;
			}
		}
		return count;
	}

	private void create() {
		// updaters
		gameUpdateGroup.add(new UnknownGameUpdateGroup(asteroids));
		gameUpdateGroup.add(new UnknownGameUpdateGroup(players));
		gameUpdateGroup.add(new UnknownGameUpdateGroup(drones));
		gameUpdateGroup.add(new UnknownGameUpdateGroup(explosions));

		// colliders
		gameUpdateGroup.add(new BulletBulletCollider());
		gameUpdateGroup.add(new ShipBulletCollider());
		gameUpdateGroup.add(new BulletAsteroidCollider());

		gameUpdateGroup.add(new AsteroidShipCollider());
		gameUpdateGroup.add(new ShipShipCollider());

		// gameUpdateGroup.add(new GameOverChecker());
	}

	private void doBlur() {
		hasFocus = false;
		stopGame();
	}

	private void doFocus() {
		hasFocus = true;
		graphics.redraw(this);
		if (isPlaying()) {
			timer.resume();
		}
	}

	private void doKeyDown(final KeyDownEvent event) {
		switch (event.getNativeKeyCode()) {
		case KeyCodes.KEY_ESCAPE:
			handleEscape();
			event.stopPropagation();
			event.preventDefault();
			return;
		case KeyCodes.KEY_ENTER:
			handleEnter();
			event.stopPropagation();
			event.preventDefault();
			return;
			// case 'Z': //
			// LeftKeyControlHandler.KEY_CODES[KeyControlHandler.ACTION1]:
			// handleEnter();
			// return;
			// case KeyCodes.KEY_CTRL: //
			// RightKeyControlHandler.KEY_CODES[KeyControlHandler.ACTION1]:
			// handleEnter();
			// return;
		case 'S':
			SoundManager.get().toggleMute();
			event.stopPropagation();
			event.preventDefault();
			return;
		default:
			break;
		}

		if (Config.DEBUG_ENABLED) {
			switch (event.getNativeKeyCode()) {
			case 'P':
				if (timer.isRunning()) {
					timer.stop();
				} else {
					timer.resume();
				}
				event.preventDefault();
				return;
			case 'O':
				if (!timer.isRunning()) {
					gameUpdateGroup.update(this);
					graphics.redraw(this);
					event.preventDefault();
					return;
				}
				break;
			case 'I':
				for (final Pilot pilot : drones) {
					final Ship ship = pilot.getShip();
					if (ship.isActive()) {
						final VectorI pixel = new VectorI();
						for (pixel.x = 0; pixel.x < ship.getPixelCount().x; ++pixel.x) {
							for (pixel.y = 0; pixel.y < ship.getPixelCount().y; ++pixel.y) {
								ship.damage(pixel);
							}
						}
						break;
					}
				}
				event.stopPropagation();
				event.preventDefault();
				break;
			default:
				break;
			}
		}
	}

	private void doResize() {
		graphics.updateClipAreas();
		graphics.redraw(this);
	}

	private void gameOver() {
		assert (state == PLAY_STATE);
		gameOverTime += getTimeStep();
		if (gameOverTime > 0.5f) {
			state = GAME_OVER_STATE;

			final String mode = Starfighter.currentMode();
			final int runTime = (int) Math.round(timer.getTimeSpec()
					.getRunTime());

			Stats.track(mode, "gameover", runTime);

			if (pvp) {
				if (players.get(0).isActive()) {
					Stats.track(mode, "left-win", runTime);
				} else if (players.get(1).isActive()) {
					Stats.track(mode, "right-win", runTime);
				} else {
					Stats.track(mode, "draw", runTime);
				}
			} else {
				int value = countActiveDrones();
				if (value > 0) {
					Stats.track(mode, "lose-" + value, runTime);
				} else {
					value = countActivePlayers();
					if (value > 0) {
						Stats.track(mode, "win", runTime);
					} else {
						Stats.track(mode, "draw", runTime);
					}
				}
			}

			stopGame();
		}
	}

	public int getActiveDroneTeams() {
		return activeDroneTeams;
	}

	public int getActivePlayerTeams() {
		return activePlayerTeams;
	}

	public List<Asteroid> getAsteroids() {
		return asteroids;
	}

	public List<EvadeDrone> getDrones() {
		return drones;
	}

	public ArrayList<Explosion> getExplosions() {
		return explosions;
	}

	public CanvasAppPanel getGamePanel() {
		return gamePanel;
	}

	public GameGraphics getGraphics() {
		return graphics;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public int getState() {
		return state;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public TimeSpec getTimeSpec() {
		return timer.getTimeSpec();
	}

	public float getTimeStep() {
		return timer.getTimeStep();
	}

	public VectorF getViewportBaseSize() {
		return viewportBaseSize;
	}

	public AreaF getWrapArea() {
		return wrapArea;
	}

	private void goToTitleScreen() {
		stop();
		Starfighter.goToTitleScreen();
	}

	private void handleEnter() {
		switch (state) {
		case Game.INTRO_STATE:
			startGame();
			break;
		case Game.PLAY_STATE:
			break;
		case Game.PAUSE_STATE:
			resumeGame();
			break;
		case Game.GAME_OVER_STATE:
			goToTitleScreen();
			break;
		}
	}

	private void handleEscape() {
		switch (state) {
		case Game.PLAY_STATE:
			pauseGame();
			break;
		case Game.INTRO_STATE:
		case Game.PAUSE_STATE:
		case Game.GAME_OVER_STATE:
			goToTitleScreen();
			break;
		}
	}

	public boolean hasFocus() {
		return hasFocus;
	}

	public boolean isPaused() {
		return (state == PAUSE_STATE);
	}

	public boolean isPlaying() {
		return (state == PLAY_STATE);
	}

	public boolean isPVP() {
		return pvp;
	}

	public Color nextAsteroidColor() {
		return Config.ASTEROID_COLORS[asteroids.size()
				% Config.ASTEROID_COLORS.length];
	}

	public Color nextDroneColor() {
		return Config.DRONE_COLORS[(drones.size()) % Config.DRONE_COLORS.length];
	}

	public Color nextPlayerColor() {
		return Config.PLAYER_COLORS[players.size()
				% Config.PLAYER_COLORS.length];
	}

	private void pauseGame() {
		assert (state == PLAY_STATE);
		state = PAUSE_STATE;
		stopGame();
	}

	public void positionAsteroids() {
		final VectorF offset = wrapArea.getSize().divide(2);
		final VectorF margin = wrapArea.getSize().divide(6);
		int i = 0;
		for (final Asteroid asteroid : asteroids) {
			if (i % 2 == 0) {
				asteroid.getMassCenter().set(
						Random.range(offset.x - margin.x, offset.x + margin.x),
						Random.range(-margin.y, margin.y));
			} else {
				asteroid.getMassCenter().set(Random.range(-margin.x, margin.x),
						Random.range(offset.y - margin.y, offset.y + margin.y));
			}
			++i;
		}
	}

	private void positionShip(final Ship ship, final int i, final VectorF offset) {
		ship.getMassCenter().set(i * offset.x, i * offset.y);
		ship.getRotation().setAngle((float) (Math.PI * 3 / 2));
		ship.getVelocity().set(0);
		ship.setAngularVelocity(0);
		ship.updateBoxPosition();
	}

	public void positionShips() {
		final int n = players.size() + drones.size();
		final VectorF offset = wrapArea.getSize().divide(n);
		int iPlayer = 0;
		int iDrone = 0;
		while (iPlayer < players.size()) {
			positionShip(players.get(iPlayer).getShip(), iPlayer + iDrone,
					offset);
			++iPlayer;
			int iDroneMax = iPlayer * drones.size() / players.size();
			if (iPlayer == players.size()) {
				iDroneMax = drones.size();
			}
			while (iDrone < iDroneMax) {
				positionShip(drones.get(iDrone).getShip(), iPlayer + iDrone,
						offset);
				++iDrone;
			}
		}
	}

	private void resumeGame() {
		assert (state == PAUSE_STATE);
		state = PLAY_STATE;
		timer.resume();
	}

	@Override
	public void run() {
		timer.setTimeStep(Config.getTimeStep());
		hasFocus = true;
		gameOverTime = 0;
		state = INTRO_STATE;
		Stats.track(Starfighter.currentMode(), "intro");

		graphics = new GameGraphics(this);
		graphics.updateClipAreas();

		gamePanel.focus();

		gamePanel.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(final ResizeEvent event) {
				doResize();
			}
		});
		gamePanel.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(final FocusEvent event) {
				doFocus();
			}
		});
		gamePanel.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(final BlurEvent event) {
				doBlur();
			}
		});
		gamePanel.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(final KeyDownEvent event) {
				doKeyDown(event);
			}
		});

		bindControllers();

		graphics.redraw(this);
	}

	public void setPVP(final boolean pvp) {
		this.pvp = pvp;
	}

	public void setState(final int state) {
		this.state = state;
	}

	private void startGame() {
		assert (state == INTRO_STATE);
		state = PLAY_STATE;
		Stats.track(Starfighter.currentMode(), "play");
		timer.start();
	}

	@Override
	public void stop() {
		timer.stop();
		SoundManager.get().stopAll();
		gamePanel.clearHandlers();
		graphics = null;
	}

	private void stopGame() {
		timer.stop();
		graphics.redraw(this);
		SoundManager.get().stopAllRepeating();
		for (final Player player : humanPlayers) {
			player.getShip().getControls().resetAll();
		}
	}

	@Override
	public void update() {
		assert (gamePanel.isAttached());

		if (!timer.isRunning()) {
			return;
		}

		// debug
		if (Config.DEBUG_ENABLED) {
			if (!timer.isRunning()) {
				Window.alert("timer not running!");
			}
			if (!gamePanel.isAttached()) {
				Window.alert("gamePanel detached!");
			}
		}

		gameUpdateGroup.update(this);

		checkGameOver();

		if (isPlaying()) {
			graphics.update(this);
		}
	}

	public void wrap(final VectorF vector) {
		wrapArea.wrap(vector);
	}
}
