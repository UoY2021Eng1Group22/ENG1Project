package com.dragonboatrace.tools.state;

import com.dragonboatrace.tools.Race;
import com.dragonboatrace.tools.ScrollingBackground;

import java.io.Serializable;

/**
 * State defines a single game state.
 */
class State implements Serializable {

    private Race race;
    private ScrollingBackground background;

    State() {
        // Default values (?)
    }

    State(Race race, ScrollingBackground background) {

    }

    public Race getRace() {
        return race;
    }

    public ScrollingBackground getBackground() {
        return background;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public void setBackground(ScrollingBackground background) {
        this.background = background;
    }
}
