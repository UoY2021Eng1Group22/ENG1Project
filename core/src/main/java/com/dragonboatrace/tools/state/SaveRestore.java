package com.dragonboatrace.tools.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.entities.Entity;
import com.dragonboatrace.entities.Obstacle;
import com.dragonboatrace.entities.ObstacleType;
import com.dragonboatrace.entities.boats.Boat;
import com.dragonboatrace.screens.MainGameScreen;
import com.dragonboatrace.tools.Lane;
import com.dragonboatrace.tools.Race;
import com.dragonboatrace.tools.ScrollingBackground;
import com.google.gson.Gson;
import java.util.ArrayList;

// P2

/**
 * SaveRestore class provides a simple workable interface for interacting with the Save/Restore functionality.
 */
public class SaveRestore {

  private Race race;
  private DragonBoatRace game;
  private ScrollingBackground bg;
  private Gson json;

  /**
   * Creates the SaveRestore class
   *
   * @param screen The game screen (that's currently shown to player.)
   */
  public SaveRestore(MainGameScreen screen) {
    this.race = screen.getRace();
    this.game = screen.getGame();
    this.bg = screen.getBackground();
    this.json = JsonTool.buildGson();
  }

  public void Save(int slot) {

    Boat b = race.getBoats().get(0);
    Lane l = b.getLane();
    ArrayList<Obstacle> os = l.getObstacles();
    Obstacle o = os.get(0);
    String ser = json.toJson(o);
    Obstacle deo = json.fromJson(ser, Obstacle.class);
//    deo.post

    Gdx.app.log("obj", o.getPosition().toString());
    Gdx.app.log("de", deo.getPosition().toString());
    Gdx.app.log("obj", String.valueOf(o.getDamage()));
    Gdx.app.log("de", String.valueOf(deo.getDamage()));
    Gdx.app.log("obj", o.getObstacleType().toString());
    Gdx.app.log("de", deo.getObstacleType().toString());

    Gdx.app.log("serialise", ser);
    Gdx.app.log("bg", json.toJson(bg));

//    Gdx.app.

    Gdx.app.log("de2", deo.getTexture().toString());

//    Json j = new Json();
//    Gdx.app.log("gdx", j.toJson(o.getTexture()));
//    Gdx.app.log("gdx", j.toJson(o.getPosition()));
//
//    Gdx.app.log("obj", o.getTexture().toString());

//    game.

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
