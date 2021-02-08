package com.dragonboatrace.tools.state;

import com.dragonboatrace.tools.Hitbox;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * Converts a hitbox to format which can be saved into a json file.
 */
public class HitboxAdapter implements JsonSerializer<Hitbox>, JsonDeserializer<Hitbox> {
  @Override
  public Hitbox deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    JsonObject o = json.getAsJsonObject();

    Hitbox h = new Hitbox(
        o.get("x").getAsFloat(),
        o.get("y").getAsFloat(),
        o.get("width").getAsInt(),
        o.get("height").getAsInt());

    return h;
  }

  @Override
  public JsonElement serialize(Hitbox src, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();

    jsonObject.addProperty("x", src.getX());
    jsonObject.addProperty("y", src.getY());
    jsonObject.addProperty("height", src.getHeight());
    jsonObject.addProperty("width", src.getWidth());

    return jsonObject;

  }
}
