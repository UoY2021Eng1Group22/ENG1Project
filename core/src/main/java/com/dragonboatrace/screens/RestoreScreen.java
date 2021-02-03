package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.tools.state.SaveRestore;

/**
 * The screen for restoring game saves.
 */
class RestoreScreen implements Screen {

  private DragonBoatRace game;
  private SaveRestore saveRestore;

  public RestoreScreen(DragonBoatRace game) {
    this.game = game;
    this.saveRestore = new SaveRestore();
  }

  @Override
  public void show() {

  }

  @Override
  public void render(float delta) {
    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
      System.out.println(saveRestore.Restore(0));
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
      System.out.println(saveRestore.Restore(1));
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
      System.out.println(saveRestore.Restore(2));
    }
  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() { }

  @Override
  public void resume() { }

  @Override
  public void hide() { }

  @Override
  public void dispose() { }
}
