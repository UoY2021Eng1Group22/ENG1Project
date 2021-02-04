package com.dragonboatrace.tools.state;

import com.badlogic.gdx.math.Vector2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JsonTool is a helper class for interaction with the Gson library:
 * - Registering all custom serialisation adapters for each data type that needs to be saved.
 * - Providing convenient functions to interact with the Gson library, mainly serialise and deserialise.
 */
public class JsonTool {

  public static Gson buildGson() {
    // builder is used for building the Gson instance
    GsonBuilder builder = new GsonBuilder();

    // only serialise @Expose fields
    builder.excludeFieldsWithoutExposeAnnotation();

    // Vector2 needs custom serialisation
    builder.registerTypeAdapter(Vector2.class, new Vector2Adapter());

    // TypeAdapterFactory for gson post processing hook (to hydrate the classes.)
    builder.registerTypeAdapterFactory(new GsonPostProcessingHook());

    // build and set the Gson instance
    return builder.create();
  }

}
