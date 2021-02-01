package com.dragonboatrace.tools.state;

import com.dragonboatrace.tools.ScrollingBackground;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

/**
 * ScrollingBackgroundAdapter is responsible for (de)serialising the state of the scrolling background.
 * Only the values `y1`, `y2`, and `xPosition` is serialised, as these are the only important information.
 */
class ScrollingBackgroundAdapter
    implements JsonSerializer<ScrollingBackground>, JsonDeserializer<ScrollingBackground> {

  @Override
  public ScrollingBackground deserialize(JsonElement json, Type typeOfT,
                                         JsonDeserializationContext context)
      throws JsonParseException {

    JsonObject jsonObject = json.getAsJsonObject();

    return new ScrollingBackground(jsonObject.get("y1").getAsFloat(),
        jsonObject.get("y2").getAsFloat(),
        jsonObject.get("xPosition").getAsFloat());

  }

  @Override
  public JsonElement serialize(ScrollingBackground src, Type typeOfSrc,
                               JsonSerializationContext context) {
    JsonObject result = new JsonObject();
    result.add("xPosition", new JsonPrimitive(src.getxPosition()));
    result.add("y1", new JsonPrimitive(src.getY1()));
    result.add("y2", new JsonPrimitive(src.getY2()));
    return result;
  }
}

// Instance creator not needed:
// ScrollingBackground has a no-arg constructor.
