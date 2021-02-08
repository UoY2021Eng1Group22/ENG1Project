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
import com.dragonboatrace.tools.state.PostProcessable;
import com.dragonboatrace.tools.state.SaveRestore;
import com.google.gson.annotations.Expose;

/**
 * Represents the Main Game Screen where the game actually happens.
 *
 * @author Benji Garment, Joe Wrieden
 */
public class MainGameScreen implements Screen, PostProcessable {

  /**
   * The race instance.
   */
  @Expose
  private final Race race;
  /**
   * The background of the window.
   */
  @Expose
  private final ScrollingBackground background;
  /**
   * The game instance.
   */
  @Expose
  private DragonBoatRace game; // P2
  /**
   * Used to make sure the countdown happens at equal intervals.
   */
  private Timer timer; // P2 - hydrate
  /**
   * Use to log the FPS for debugging.
   */
  private FPSLogger logger; // P2 - hydrate
  /**
   * GlyphLayout used for centering fonts
   */
  private GlyphLayout layout; // P2 - hydrate
  /**
   * Font used for rendering to screen
   */
  private BitmapFont font; // P2 - hydrate
  /**
   * Pause game, starts true.
   */
  @Expose
  private boolean paused = true;
  /**
   * The time left on the initial countdown.
   */
  private int countDownRemaining = 3;
  /**
   * The String being displayed in the countdown.
   */
  private String countDownString = "";

  /**
   * The save restore feature instance.
   */
  private SaveRestore saveRestore;

  private boolean saveTriggered = false;
  private boolean saveSuccess = false;

  // P2
  /**
   * Indication of if the game/race has started already.
   */
  @Expose // ?
  private boolean gameHasStarted = false;

  private Timer.Task countDownTask;

  /**
   * Creates a new game screen with a game instance.
   *
   * @param game       The game instance.
   * @param boatChosen The {@link BoatType} that the player chose.
   */
  public MainGameScreen(DragonBoatRace game, BoatType boatChosen) {
    this.game = game;
    this.saveRestore = new SaveRestore();

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
    this.countDownTask = new Timer.Task() {
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
    this.timer = new Timer();
    this.timer.scheduleTask(countDownTask, 0, 1);
    // We don't want the countdown to start before the screen has displayed.
    this.timer.stop();
  }

  /**
   * Runs when the window first starts. Runs the countdown starter.
   */
  public void show() {
    this.timer.start();
    this.saveRestore = new SaveRestore();
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

      // P2: Help text for pausing
      displayShortcutText();

    } else {
      this.background.render(game.getBatch());
      this.race.render(game.getBatch());
      displayCountDown();
    }

    // P2: Pause indicator
    if (isPaused() && gameHasStarted) {
      displayPaused(isPaused());
    }

    // Pressing P now toggles pausing.
    // nb: only save the game when the game is paused.
    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      setPaused(!isPaused());
    }

    // Q goes back to MainMenuScreen now.

    if (isPaused()) {

      if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {

        this.game.setScreen(new MainMenuScreen(game));

      } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {

        saveTriggered = true;
        saveSuccess = this.saveRestore.Save(0, this);

      } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {

        saveTriggered = true;
        saveSuccess = this.saveRestore.Save(1, this);

      } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {

        saveTriggered = true;
        saveSuccess = this.saveRestore.Save(2, this);

      }

    }

    if (saveTriggered) {
      displaySaveStatus(saveSuccess);
    }

    this.game.getBatch().end();

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

  private void displayShortcutText() {

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    //    parameter.size *= 10.0 / Settings.SCALAR;
    parameter.size *= 4.0 / Settings.SCALAR;
    parameter.color = new Color(0x7f7f7f7f);
    BitmapFont f = generator.generateFont(parameter);

    GlyphLayout g = new GlyphLayout();
    g.setText(f, "Press Esc to pause the game.");
    //    font.draw(game.getBatch(), "Press P to pause the game.", (Gdx.graphics.getWidth() - layout.width) / 2, Gdx.graphics.getHeight());
    // Gdx.graphics.getWidth() - layout.width - 100
    f.draw(game.getBatch(), "Press Esc to pause the game.", Gdx.graphics.getWidth() - g.width - 32,
        g.height + 32);
  }

  private void displayPaused(boolean paused) {
    String pausedStr = paused ? "Paused" : "";

    layout.setText(font, pausedStr);
    font.draw(game.getBatch(), pausedStr, (Gdx.graphics.getWidth() - layout.width) / 2,
        Gdx.graphics.getHeight() * 2 / 3.0f);

    // TODO: display help string

    StringBuilder pauseBuilder = new StringBuilder();
    pauseBuilder.append("Press Esc to go back.\n");
    pauseBuilder.append("Press Q to quit the game.\n");

    for (int i = 0; i <= 2; i++) {
      pauseBuilder.append(
          String.format(
              "Press %d to %s slot %d\n", i + 1,
              this.saveRestore.slotIsFree(i) ? "save to" : "overwrite",
              i + 1
          )
      );
    }

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size *= 4.0 / Settings.SCALAR;
    parameter.color = Color.BLACK;
    BitmapFont f = generator.generateFont(parameter);

    GlyphLayout g = new GlyphLayout();
    g.setText(f, pauseBuilder);

    f.draw(game.getBatch(), pauseBuilder, (Gdx.graphics.getWidth() - g.width) / 3,
        Gdx.graphics.getHeight() / 3.0f);
  }

  public void displaySaveStatus(boolean saveSuccess) {

    String statusText = saveSuccess ? "Save successful." : "Save failed.";

    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size *= 4.0 / Settings.SCALAR;
    parameter.color = Color.BLACK;
    BitmapFont f = generator.generateFont(parameter);
    GlyphLayout g = new GlyphLayout();

    g.setText(f, statusText);
    f.draw(game.getBatch(), statusText, (Gdx.graphics.getWidth() - g.width) / 3,
        layout.height + 16);
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

  public void setGame(DragonBoatRace game) {
    this.game = game;
  }

  // P2

  public Race getRace() {
    return race;
  }

  public ScrollingBackground getBackground() {
    return background;
  }

  @Override
  public void postProcess() {
    this.logger = new FPSLogger();
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter =
        new FreeTypeFontGenerator.FreeTypeFontParameter();
    parameter.size *= 10.0 / Settings.SCALAR;
    parameter.color = Color.BLACK;
    this.font = generator.generateFont(parameter);
    this.layout = new GlyphLayout();

    this.countDownRemaining = 3;

    /* Countdown initialisation */
    this.countDownTask = new Timer.Task() {
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
    this.timer = new Timer();
    this.timer.scheduleTask(countDownTask, 0, 1);
    // We don't want the countdown to start before the screen has displayed.

    this.paused = true;
    this.timer.start();

  }
}
