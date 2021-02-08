package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.entities.Button;
import com.dragonboatrace.entities.EntityType;
import com.dragonboatrace.tools.Settings;
import com.dragonboatrace.tools.state.SaveRestore;

/**
 * The screen for restoring game saves.
 */
class RestoreScreen implements Screen {

  private final DragonBoatRace game;
  private final SaveRestore saveRestore;

  private final Button[] availableSlots;

  public RestoreScreen(DragonBoatRace game) {
    this.game = game;
    this.saveRestore = new SaveRestore();

    // Buttons

    availableSlots = new Button[3];

    for (int i = 0; i < 3; i++) {
      if (!saveRestore.slotIsFree(i)) {
        availableSlots[i] = new Button(
            new Vector2(
                (Gdx.graphics.getWidth() - EntityType.BUTTON.getWidth()) / 2f,
                ((float) i * Gdx.graphics.getHeight()/5.0f + Gdx.graphics.getHeight() / 4.0f) / Settings.SCALAR),
            String.format("slot%d_button_active.png", (i + 1)),
            String.format("slot%d_button_inactive.png", (i + 1))
        );
      }
    }

  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClearColor(32.0f / 255.0f, 96.0f / 255.0f, 184.0f / 255.0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    this.game.getBatch().begin();

    Button[] slotButtons = this.availableSlots;
    for (int i = 0; i < slotButtons.length; i++) {
      if (slotButtons[i] != null) {
        slotButtons[i].render(this.game.getBatch());
        if (slotButtons[i].isHovering() && Gdx.input.isTouched()) {
          this.game.setScreen(saveRestore.Restore(i, this.game));
        }
      }
    }

    this.game.getBatch().end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      this.game.setScreen(new MainMenuScreen(this.game));
    }

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

  @Override
  public void dispose() {
  }
}
