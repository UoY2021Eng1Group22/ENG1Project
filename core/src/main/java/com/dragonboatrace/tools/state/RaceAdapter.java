package com.dragonboatrace.tools.state;

import com.dragonboatrace.tools.Race;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class RaceAdapter implements JsonSerializer<Race>, JsonDeserializer<Race> {


  @Override
  public Race deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    return null;
  }

  @Override
  public JsonElement serialize(Race src, Type typeOfSrc, JsonSerializationContext context) {
    return null;
  }
}
