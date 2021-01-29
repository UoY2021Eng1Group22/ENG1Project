package com.dragonboatrace.tools;

import com.badlogic.gdx.Gdx;
import com.dragonboatrace.entities.boats.ComputerBoat;
import com.dragonboatrace.entities.boats.PlayerBoat;
import com.dragonboatrace.screens.MainGameScreen;
import com.google.gson.*;
import com.dragonboatrace.DragonBoatRace;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game saving / restoring function related class.
 */
public class SaveRestore {

    private Race race;
    private DragonBoatRace game;
    private ScrollingBackground bg;

    /**
     * Constructor for class.
     * This class takes the entire game instance, and
     * @param game
     * @param race
     */
    public SaveRestore(DragonBoatRace game, Race race) {
        this.race = race;
        this.game = game;
    }

    public SaveRestore(MainGameScreen screen) {
        this.race = screen.getRace();
        this.game = screen.getGame();
        this.bg = screen.getBackground();
    }

    public void Save() {

//        this.race.

        Gdx.app.log("PLAYER", String.format("%f", this.race.getPlayer().getHealth()));

        this.race.getBoats().forEach((boat -> {
            Gdx.app.log("BOAT", String.format("%s time: %f", boat.getName(), boat.getTime()));
//            Gdx.app.log()
        }));


        // get all boats

        // new save file structure

        StateFile state = new StateFile();

        // convert to json

        Gson json = new Gson();
        json.toJson(state);

        // save to file

    }

//    private boolean

    public void Restore() {

//        Race r = new Race()

        // read from file
        // deserialise
        // restore everything into respective class
    }

    // === getters/setters

    public Race getRace() {
        return race;
    }

    public DragonBoatRace getGame() {
        return game;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setGame(DragonBoatRace game) {
        this.game = game;
    }

    // bean
    private class StateFile implements Serializable {


        private PlayerBoat playerboat;
        private int level;
        private ArrayList<ComputerBoat> computerBoats;

        StateFile() {}

        public int getLevel() {
            return level;
        }

        public PlayerBoat getPlayerboat() {
            return playerboat;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public void setPlayerboat(PlayerBoat playerboat) {
            this.playerboat = playerboat;
        }

    }


}
