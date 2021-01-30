package com.dragonboatrace.tools.state;

import com.dragonboatrace.entities.boats.ComputerBoat;
import com.dragonboatrace.entities.boats.PlayerBoat;

import java.io.Serializable;
import java.util.ArrayList;

// bean
class StateFile implements Serializable {

    private PlayerBoat playerboat;
    private int level;
    private ArrayList<ComputerBoat> computerBoats;

    StateFile() {
    }

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
