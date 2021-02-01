package com.dragonboatrace.tools.state;

import com.dragonboatrace.entities.Entity;
import com.google.gson.*;

import java.lang.reflect.Type;

// (de)serializing abstract classes in Gson
// ref: https://ovaraksin.blogspot.com/2011/05/json-with-gson-and-abstract-classes.html

/**
 * EntityAdapter is responsible for (de)serialising entity related classes.
 *
 */
class EntityAdapter implements JsonSerializer<Entity>, JsonDeserializer<Entity> {
    public JsonElement serialize(Entity src, Type typeOfSrc, JsonSerializationContext context) {
        /*
            position,
            velocity,
            type,
            texture.
            (hitbox derived from type)
         */

        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src));
        return result;
    }


    public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            return context.deserialize(element, Class.forName("com.dragonboatrace.entities." + type));
        } catch (ClassNotFoundException ex) {
            throw new JsonParseException("Unknown element type: " + type, ex);
        }
    }
}
