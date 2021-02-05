package com.dragonboatrace.tools.state;

import com.dragonboatrace.entities.Entity;
import com.dragonboatrace.entities.Obstacle;
import com.dragonboatrace.entities.ObstacleType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

class ObstacleAdapter implements JsonSerializer<Obstacle>, JsonDeserializer<Obstacle> {
  @Override
  public Obstacle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {

    JsonObject jsonObject = json.getAsJsonObject();

    // deserialise with Entity first
    Entity entity = context.deserialize(jsonObject, Entity.class);

    // Get the specific obstacle type
    ObstacleType obstacleType = context.deserialize(jsonObject.getAsJsonPrimitive("obstacleType"), ObstacleType.class);

    // initialise an obstacle object
    // (NB: this Obstacle constructor is created specifically for serialising)

    return new Obstacle(obstacleType, entity.getPosition(), entity.getVelocity());
//    return null;
  }

  @Override
  public JsonElement serialize(Obstacle src, Type typeOfSrc, JsonSerializationContext context) {

    // Serialise parent abstract class and its properties, and use it as the root object.
//    JsonObject result = context.serialize(src, Entity.class).getAsJsonObject();

    JsonObject result = new JsonObject();

    // add the obstacle specific property
    result.add("obstacleType", context.serialize(src.getObstacleType()));

    return result;
  }
}
/*
{
  objectType
  obstacleType

 */
