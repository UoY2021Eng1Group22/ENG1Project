package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.tools.Settings;
import com.dragonboatrace.tools.state.SaveRestore;

/**
 * The screen for restoring game saves.
 */
class RestoreScreen implements Screen {

  private DragonBoatRace game;
  private SaveRestore saveRestore;
  private TextButton b;

//  private final Texture

  public RestoreScreen(DragonBoatRace game) {
    this.game = game;
    this.saveRestore = new SaveRestore();

    FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
    param.size *= 10.0 / Settings.SCALAR;
    param.color = Color.WHITE;

    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
    buttonStyle.font = fontGen.generateFont(param);
    GlyphLayout layout = new GlyphLayout();
    layout.setText(buttonStyle.font, "Slot 1");

    this.b = new TextButton("Slot 1", buttonStyle);
  }

  @Override
  public void show() {
    System.out.println(saveRestore.slotIsFree(0));
    System.out.println(saveRestore.slotIsFree(1));
    System.out.println(saveRestore.slotIsFree(2));
  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClearColor(32.0f/255.0f, 96.0f/255.0f, 184.0f/255.0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    this.game.getBatch().begin();

    this.b.setPosition(1024, 768);
    this.b.draw(this.game.getBatch(), 1);


    this.game.getBatch().end();



    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
      System.out.println(saveRestore.Restore(0));
      this.game.setScreen(saveRestore.Restore(0));
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
      this.game.setScreen(saveRestore.Restore(1));
    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
      this.game.setScreen(saveRestore.Restore(2));
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
