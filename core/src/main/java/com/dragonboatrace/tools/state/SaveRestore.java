package com.dragonboatrace.tools.state;

import com.badlogic.gdx.Gdx;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.screens.MainGameScreen;
import com.dragonboatrace.tools.Race;
import com.dragonboatrace.tools.ScrollingBackground;

// P2

/**
 * SaveRestore class provides a simple workable interface for interacting with the Save/Restore functionality.
 */
public class SaveRestore {

  private Race race;
  private DragonBoatRace game;
  private ScrollingBackground bg;
  private JsonTool json;

  /**
   * Creates the SaveRestore class
   *
   * @param screen The game screen (that's currently shown to player.)
   */
  public SaveRestore(MainGameScreen screen) {
    this.race = screen.getRace();
    this.game = screen.getGame();
    this.bg = screen.getBackground();
    this.json = new JsonTool();
  }

  public void Save() {

    Gdx.app.log("BG", json.serialize(bg));

    // get all boats

    // new save file structure

//        StateFile state = new StateFile();

    // convert to json

//        json.toJson(state);

    // save to file

  }

//    private boolean

  public void Restore() {
    // read from file
    // deserialize
    // restore everything into respective class
  }

  // === getters/setters

  public Race getRace() {
    return race;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public DragonBoatRace getGame() {
    return game;
  }

  public void setGame(DragonBoatRace game) {
    this.game = game;
  }


}
