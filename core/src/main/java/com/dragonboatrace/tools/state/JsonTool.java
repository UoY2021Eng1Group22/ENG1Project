package com.dragonboatrace.tools.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.dragonboatrace.entities.Entity;
import com.dragonboatrace.entities.Obstacle;
import com.dragonboatrace.entities.boats.Boat;
import com.dragonboatrace.entities.boats.ComputerBoat;
import com.dragonboatrace.entities.boats.PlayerBoat;
import com.dragonboatrace.tools.Hitbox;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 * JsonTool is a helper class for interaction with the Gson library:
 * - Building a custom Gson instance
 * - Registering custom serialisation adapters for each data type that needs to be saved.
 * - Setting up hooks for all the deserialisation calls.
 */
public class JsonTool {

  static Gson buildGson() {
    // builder is used for building the Gson instance
    GsonBuilder builder = new GsonBuilder();

    // only serialise @Expose fields
    builder.excludeFieldsWithoutExposeAnnotation();

    // Vector2 needs custom serialisation
    builder.registerTypeAdapter(Vector2.class, new Vector2Adapter());

    builder.registerTypeAdapter(Hitbox.class, new HitboxAdapter());

    // TypeAdapterFactory for gson post processing hook (to hydrate the classes.)
    builder.registerTypeAdapterFactory(new GsonPostProcessingHook());

    RuntimeTypeAdapterFactory<Entity> entityRuntimeTypeAdapterFactory
            = RuntimeTypeAdapterFactory.of(Entity.class);
    entityRuntimeTypeAdapterFactory.registerSubtype(Obstacle.class);
    entityRuntimeTypeAdapterFactory.registerSubtype(Boat.class);

    RuntimeTypeAdapterFactory<Boat> boatRuntimeTypeAdapterFactory
            = RuntimeTypeAdapterFactory.of(Boat.class);
    boatRuntimeTypeAdapterFactory.registerSubtype(PlayerBoat.class);
    boatRuntimeTypeAdapterFactory.registerSubtype(ComputerBoat.class);

    builder.registerTypeAdapterFactory(entityRuntimeTypeAdapterFactory);
    builder.registerTypeAdapterFactory(boatRuntimeTypeAdapterFactory);

    // build and set the Gson instance
    return builder.create();
  }

}
