package com.dragonboatrace.tools.state;

import com.badlogic.gdx.math.Vector2;
import com.dragonboatrace.entities.Entity;
import com.dragonboatrace.entities.EntityType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

// (de)serializing abstract classes in Gson
// ref: https://ovaraksin.blogspot.com/2011/05/json-with-gson-and-abstract-classes.html

/**
 * EntityAdapter is responsible for (de)serialising Entity object.
 *
 * We write a custom serializer for the Entity class, so that we can serialise generalised
 * properties that are applicable to all Entity-derived child classes, and thus avoiding
 * duplicate code.
 */
class EntityAdapter implements JsonSerializer<Entity>, JsonDeserializer<Entity> {
  public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {

    // position,
    // velocity,
    // type,
    // texture.
    // (hitbox derived from type)

    JsonObject result = new JsonObject();

    // since Entity is an abstract object
    // we need to denote what exact object type the object is. (Represented as a string.)

    result.add("objectType", new JsonPrimitive(src.getClass().getSimpleName()));

    // serialise properties of Entity
    // note: using context.serialise for each of the properties as Gson provides default
    //         serialising methods for these props (e.g. Vector2, entityType - of Enum)

    result.add("entityType", context.serialize(src.getEntityType()));
    result.add("position", context.serialize(src.getPosition()));
    result.add("velocity", context.serialize(src.getVelocity()));

    result.add("properties", context.serialize(src, src.getClass()));

    // Texture might be passed in from concrete classes?

    // result.add("texture", context.serialize(src.getTexture()));

    return result;
  }

  public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
      throws JsonParseException {
    JsonObject jsonObject = json.getAsJsonObject();
    String objectType = jsonObject.get("objectType").getAsString();

    EntityType entityType = context.deserialize(jsonObject.get("entityType"), EntityType.class);
    Vector2 position = context.deserialize(jsonObject.get("position"), Vector2.class);
    Vector2 velocity = context.deserialize(jsonObject.get("velocity"), Vector2.class);

    JsonElement element = jsonObject.get("properties");

    try {
      return context.deserialize(element, Class.forName("com.dragonboatrace.entities." + objectType));
    } catch (ClassNotFoundException ex) {
      throw new JsonParseException("Unknown element type: " + objectType, ex);
    }
  }
}
