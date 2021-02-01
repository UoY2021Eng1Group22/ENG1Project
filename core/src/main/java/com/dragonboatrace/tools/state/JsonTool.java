package com.dragonboatrace.tools.state;

import com.dragonboatrace.tools.ScrollingBackground;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JsonTool is a helper class for interaction with the Gson library:
 * - Registering all custom serialisation adapters for each data type that needs to be saved.
 * - Providing convenient functions for
 */
class JsonTool {

    private final Gson gson;

    JsonTool() {

        // builder is used for building the Gson instance
        GsonBuilder builder = new GsonBuilder();

        // Register adapters.
        builder.registerTypeAdapter(ScrollingBackground.class, new ScrollingBackgroundAdapter());

        // build and set the Gson instance
        this.gson = builder.create();

    }

    /**
     *
     * @return A Json string
     */
    String serialize(Object o) {
//        this.gson
        return this.gson.toJson(o);
    }

    void deserialize() {

    }

}
