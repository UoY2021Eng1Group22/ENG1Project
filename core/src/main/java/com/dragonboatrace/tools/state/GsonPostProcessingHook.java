package com.dragonboatrace.tools.state;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

// https://blog.simplypatrick.com/tils/2016/2016-03-02-post-processing-gson-deserialization/
// https://medium.com/mobile-app-development-publication/post-processing-on-gson-deserialization-26ce5790137d

/**
 * This enables hooks when Gson is deserialising the object.
 * Use case: hydrates the object after deserialisation.
 */
class GsonPostProcessingHook implements TypeAdapterFactory {

  @Override
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
    TypeAdapter<T> delegateAdapter = gson.getDelegateAdapter(this, type);

    return new TypeAdapter<T>() {
      @Override
      public void write(JsonWriter out, T value) throws IOException {
        delegateAdapter.write(out, value);
      }

      @Override
      public T read(JsonReader in) throws IOException {
        T obj = delegateAdapter.read(in);
        if (obj instanceof PostProcessable) {
          ((PostProcessable) obj).postProcess();
        }
        return obj;
      }
    };
  }
}
