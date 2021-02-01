package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.entities.Button;
import com.dragonboatrace.entities.EntityType;
import com.dragonboatrace.tools.Settings;

/**
 * Represents the pause screen (entered when ESC is pressed in game.)
 */
public class PauseScreen implements Screen {

  private final DragonBoatRace game;
  private final Screen prevScreen;
  private final Button contButton;
  private final Button exitButton;
  private final Button menuButton;
  private final Button saveButton;

  public PauseScreen(DragonBoatRace game, Screen prevScreen) {
    this.game = game;
    this.prevScreen = prevScreen;

    this.contButton = new Button(
        new Vector2(
            (Gdx.graphics.getWidth() - EntityType.BUTTON.getWidth()) / 2.0f,
            400f / Settings.SCALAR),
        "agile_button_active.png", "agile_button_inactive.png");

    this.saveButton = new Button(
        new Vector2(
            (Gdx.graphics.getWidth() - EntityType.BUTTON.getWidth()) / 2.0f,
            1000f / Settings.SCALAR),
        "medium_button_active.png", "medium_button_inactive.png");

    this.menuButton = new Button(
        new Vector2(
            (Gdx.graphics.getWidth() - EntityType.BUTTON.getWidth()) / 2.0f,
            800f / Settings.SCALAR),
        "easy_button_active.png", "easy_button_inactive.png");

    this.exitButton = new Button(
        new Vector2(
            (Gdx.graphics.getWidth() - EntityType.BUTTON.getWidth()) / 2.0f,
            200f / Settings.SCALAR),
        "exit_button_active.png", "exit_button_inactive.png");
  }

  @Override
  public void show() {
  }

  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    this.game.getBatch().begin();
//        this.game.getBatch().

    contButton.render(this.game.getBatch());
    if (contButton.isHovering() && Gdx.input.isTouched()) {
      this.game.setScreen(this.prevScreen);
    }

//        saveButton.render(this.game.getBatch());
//        if (saveButton.isHovering() && Gdx.input.isTouched()) {
//            (new SaveRestore()).Save();
//        }

    menuButton.render(this.game.getBatch());
    if (menuButton.isHovering() && Gdx.input.isTouched()) {
//            prevScreen.dispose();
      this.game.setScreen(new MainMenuScreen(this.game));
    }

    exitButton.render(this.game.getBatch());
    if (exitButton.isHovering() && Gdx.input.isTouched()) {
//            this.dispose();
      Gdx.app.exit();
    }

    this.game.getBatch().end();
  }

  @Override
  public void dispose() {
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

  @Override
  public void resize(int width, int height) {

  }
}
