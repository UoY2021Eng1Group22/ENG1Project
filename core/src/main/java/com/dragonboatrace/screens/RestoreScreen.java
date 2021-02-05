package com.dragonboatrace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.entities.Button;
import com.dragonboatrace.tools.Settings;
import com.dragonboatrace.tools.state.SaveRestore;

/**
 * The screen for restoring game saves.
 */
class RestoreScreen implements Screen {

  private DragonBoatRace game;
  private SaveRestore saveRestore;

  private Button slot1Button;
  private Button slot2Button;
  private Button slot3Button;

  public RestoreScreen(DragonBoatRace game) {
    this.game = game;
    this.saveRestore = new SaveRestore();

//    FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
//    FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
//    param.size *= 10.0 / Settings.SCALAR;
//    param.color = Color.WHITE;
//
//    TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
//    buttonStyle.font = fontGen.generateFont(param);
//    GlyphLayout layout = new GlyphLayout();
//    layout.setText(buttonStyle.font, "Slot 1");

    // Buttons

//    this.slot1Button = new Button(new Vector2(0,0), )

  }

  @Override
  public void show() {
  }

  @Override
  public void render(float delta) {

    Gdx.gl.glClearColor(32.0f / 255.0f, 96.0f / 255.0f, 184.0f / 255.0f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    this.game.getBatch().begin();

    this.slot1Button.render(this.game.getBatch());

    if (this.slot1Button.isHovering() && Gdx.input.isTouched()) {
      this.game.setScreen(saveRestore.Restore(0, this.game));
    }

    this.game.getBatch().end();

    if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
      this.game.setScreen(new MainMenuScreen(this.game));
    }

//    if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
//      this.game.setScreen(saveRestore.Restore(0, this.game));
//    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
//      this.game.setScreen(saveRestore.Restore(1, this.game));
//    } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
//      this.game.setScreen(saveRestore.Restore(2, this.game));
//    } else if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//      this.game.setScreen();
//    }
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
