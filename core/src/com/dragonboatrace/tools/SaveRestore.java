package com.dragonboatrace.tools;

import com.dragonboatrace.entities.boats.ComputerBoat;
import com.dragonboatrace.entities.boats.PlayerBoat;
import com.google.gson.*;
import com.dragonboatrace.DragonBoatRace;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game saving / restoring logic
 */
public class SaveRestore {

    private Race race;
    private DragonBoatRace game;

    public SaveRestore(DragonBoatRace game, Race race) {
        this.race = race;
        this.game = game;
    }

    public void Save() {

//        this.race.

        this.race.getBoats().forEach((boat -> {

        }));

        /*
            Data to be saved from:

            Data to be saved:
                - PlayerBoat
                    - Player Time
                    -
                - ComputerBoat
                - Race
         */

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
