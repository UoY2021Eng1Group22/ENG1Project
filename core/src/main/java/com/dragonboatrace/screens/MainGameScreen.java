package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Timer;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.entities.boats.BoatType;
import com.dragonboatrace.tools.Race;
import com.dragonboatrace.tools.ScrollingBackground;
import com.dragonboatrace.tools.Settings;
import com.dragonboatrace.tools.state.SaveRestore;

/**
 * Represents the Main Game Screen where the game actually happens.
 *
 * @author Benji Garment, Joe Wrieden
 */
public class MainGameScreen implements Screen {

  /**
   * The game instance.
   */
  private final DragonBoatRace game;
  /**
   * Used to make sure the countdown happens at equal intervals.
   */
  private final Timer timer;
  /**
   * The race instance.
   */
  private final Race race;
  /**
   * The background of the window.
   */
  private final ScrollingBackground background;
  /**
   * Use to log the FPS for debugging.
   */
  private final FPSLogger logger;
  /**
   * GlyphLayout used for centering fonts
   */
  private final GlyphLayout layout;
  /**
   * Font used for rendering to screen
   */
  private final BitmapFont font;
  /**
   * Pause game, starts true.
   */
  private boolean paused = true;
  /**
   * The time left on the initial countdown.
   */
  private int countDownRemaining = 3;
  /**
   * The String being displayed in the countdown.
   */
  private String countDownString = "";

  // P2
  /** Indication of if the game/race has started already. */
  private boolean gameHasStarted = false;

  /**
   * Creates a new game screen with a game instance.
   *
   * @param game       The game instance.
   * @param boatChosen The {@link BoatType} that the player chose.
   */
  public MainGameScreen(DragonBoatRace game, BoatType boatChosen) {
    this.game = game;

    this.logger = new FPSLogger();

    this.race = new Race(10000, boatChosen, this.game.getRound());
    this.background = new ScrollingBackground();
    this.background.resize(Gdx.graphics.getWidth());

    /* Font related items */
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size *= 10.0 / Settings.SCALAR;
    parameter.color = Color.BLACK;
    this.font = generator.generateFont(parameter);
    this.layout = new GlyphLayout();

    /* Countdown initialisation */
    Timer.Task countDownTask = new Timer.Task() {
      @Override
      public void run() {
        paused = true;
        if (countDownRemaining == 3) {
          countDownString = "READY";
          countDownRemaining--;
        } else if (countDownRemaining == 2) {
          countDownString = "STEADY";
          countDownRemaining--;
        } else if (countDownRemaining == 1) {
          countDownString = "GO";
          countDownRemaining--;
        } else {
          countDownString = "";
          paused = false;
          gameHasStarted = true;
          this.cancel();
        }
      }
    };
    timer = new Timer();
    timer.scheduleTask(countDownTask, 0, 1);
    // We don't want the countdown to start before the screen has displayed.
    timer.stop();
  }

  public MainGameScreen(DragonBoatRace game, Race race, ScrollingBackground bg) {
    this.game = game;
    this.race = race;
    this.background = bg;

    this.background.resize(Gdx.graphics.getWidth());

    /* Font related items */
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size *= 10.0 / Settings.SCALAR;
    parameter.color = Color.BLACK;
    this.font = generator.generateFont(parameter);
    this.layout = new GlyphLayout();

    this.logger = new FPSLogger();

    /* Countdown initialisation */
    Timer.Task countDownTask = new Timer.Task() {
      @Override
      public void run() {
        paused = true;
        if (countDownRemaining == 3) {
          countDownString = "READY";
          countDownRemaining--;
        } else if (countDownRemaining == 2) {
          countDownString = "STEADY";
          countDownRemaining--;
        } else if (countDownRemaining == 1) {
          countDownString = "GO";
          countDownRemaining--;
        } else {
          countDownString = "";
          paused = false;
          this.cancel();
        }
      }
    };
    timer = new Timer();
    timer.scheduleTask(countDownTask, 0, 1);
    // We don't want the countdown to start before the screen has displayed.
    timer.stop();

  }

  /**
   * Runs when the window first starts. Runs the countdown starter.
   */
  public void show() {
    timer.start();
  }

  /**
   * Render the main game window. Includes rendering the background and the {@link Race}.
   *
   * @param deltaTime The time since the last frame.
   */
  public void render(float deltaTime) {
    Gdx.gl.glClearColor(32.0f / 255.0f, 96.0f / 255.0f, 184.0f / 255.0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    this.game.getBatch().begin();
    if (!isPaused()) {
      this.logger.log();
      this.background.update(deltaTime * this.race.getPlayer().getVelocity().y);
      this.background.render(game.getBatch());
      this.race.update(deltaTime, this.game);
      this.race.render(game.getBatch());
    } else {
      this.background.render(game.getBatch());
      this.race.render(game.getBatch());
      displayCountDown();
    }

    // P2: Pause indicator
    if (isPaused() && gameHasStarted) {
      displayPaused(isPaused());
    }


//    if (getPaused() && Gdx.input)

    // P2: Help text for when pausing

    this.game.getBatch().end();

    // P2
    // Pressing P now toggles pausing.
    // nb: only save the game when the game is paused.
    if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
      setPaused(!isPaused());
    }

    // P2
    // Currently (01-30), pressing O triggers the saving subroutine.
//    if (getPaused() && Gdx.input.isKeyJustPressed(Input.Keys.O)) {
//      (new SaveRestore(this)).Save(1);
//    }

    // Escape goes back to MainMenuScreen now.

    if (isPaused() && Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      this.game.setScreen(new MainMenuScreen(game));
    }

    // P2
    // TODO: If slot is occupied, then show notification

    if (isPaused()) {
      if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1))
        (new SaveRestore(this)).Save(1);
      else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2))
        (new SaveRestore(this)).Save(2);
      else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3))
        (new SaveRestore(this)).Save(3);
    }

  }

  /**
   * Render the current status of the countdown.
   */
  private void displayCountDown() {
    layout.setText(font, this.countDownString);
    font.draw(game.getBatch(), this.countDownString, (Gdx.graphics.getWidth() - layout.width) / 2,
        Gdx.graphics.getHeight() / 2.0f);
  }

  // P2

  private void displayPaused(boolean paused) {
    String pausedStr = paused ? "Paused" : "";

    layout.setText(font, pausedStr);
    font.draw(game.getBatch(), pausedStr, (Gdx.graphics.getWidth() - layout.width) / 2,
        Gdx.graphics.getHeight() / 2.0f);

    // TODO: display help string


  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  // getters / setters for the required dynamic data

  @Override
  public void dispose() {
    this.game.getBatch().dispose();
  }

  public boolean isPaused() {
    return this.paused;
  }

  public void setPaused(boolean paused) {
    this.paused = paused;
  }

  public DragonBoatRace getGame() {
    return game;
  }

  public Race getRace() {
    return race;
  }

  // test: constructor

  public ScrollingBackground getBackground() {
    return background;
  }


}
