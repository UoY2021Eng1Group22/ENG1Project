package com.dragonboatrace.tools.state;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * De-serialises a vector ready to be saved.
 */
public class Vector2Adapter implements JsonSerializer<Vector2>, JsonDeserializer<Vector2> {

  @Override
  public Vector2 deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    JsonObject jsonObject = json.getAsJsonObject();

    return new Vector2(jsonObject.get("x").getAsFloat(), jsonObject.get("y").getAsFloat());
  }

  @Override
  public JsonElement serialize(Vector2 src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("x", src.x);
    jsonObject.addProperty("y", src.y);

    return jsonObject;
  }
}
