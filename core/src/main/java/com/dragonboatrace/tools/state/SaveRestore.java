package com.dragonboatrace.tools.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.screens.MainGameScreen;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

// P2

/**
 * SaveRestore class provides a simple workable interface for interacting with the Save/Restore functionality.
 * <p>
 * This class should only be instantiated in: RestoreScreen, MainGameScreen
 */
public class SaveRestore {

  private final Gson json;
  private MainGameScreen[] slots;
  private JsonReader jsonReader = null;
  private JsonWriter jsonWriter = null;

  public SaveRestore() {
    this.json = JsonTool.buildGson();
    this.slots = new MainGameScreen[3]; // Temporary storage as there is no persistence yet.
    this.load();
  }

  public boolean Save(int slot, MainGameScreen screen) {

    this.slots[slot] = screen;

    // flush / serialise
    return this.flush();
  }

  public MainGameScreen Restore(int slot) {
    MainGameScreen screen = this.slots[slot];
    return screen;
  }

  /**
   * load is a function that bootstraps the Array from the file, if exists. (adding persistence.)
   */
  private void load() {

    Reader r = null;
    Writer w = null;

    // Gdx.files.isLocalStorageAvailable();

    // Get the local save file handle.
    FileHandle handle = Gdx.files.local("saves.json");

    try {
      r = handle.reader();
    } catch (GdxRuntimeException gex) {
      Gdx.app.error("LOAD", "Could not read the saves.json file.", gex);
    }

    if (r != null) {
      this.jsonReader = this.json.newJsonReader(r);
    }

    try {
      w = handle.writer(true);
    } catch (GdxRuntimeException gex) {
      Gdx.app.error("SAVE", "Could not get the file writer.", gex);
    }

    if (w != null) {
      try {
        this.jsonWriter = this.json.newJsonWriter(w);
      } catch (IOException ex) {
        Gdx.app.error("SAVE", "Could not initialise the JSON writer.", ex);
      }
    }

    if (jsonReader != null) {

      try {

        MainGameScreen[] fileContent = json.fromJson(jsonReader, MainGameScreen[].class);
        if (fileContent != null)
          this.slots = fileContent;
      } catch (JsonIOException ioEx) {
        Gdx.app.error("LOAD", "Problem from JSON I/O.", ioEx);
      } catch (JsonSyntaxException synEx) {
        Gdx.app.error("LOAD", "Problem from JSON syntax.", synEx);
      }

    }

  }

  /**
   * Is a certain slot empty?
   * @param slot slot number.
   * @return if it is free.
   */
  public boolean slotIsFree(int slot) {
    try {
      return this.slots[slot] == null;
    } catch (NullPointerException ex) {
      return true;
    }
  }

  /**
   * Flushes the game state array to disk.
   * @return if the flushing is successful.
   */
  private boolean flush() {
    // can't flush to the file because the file could not be written.
    if (this.jsonWriter == null) {
      return false;
    }

    try {
      json.toJson(this.slots, MainGameScreen[].class, this.jsonWriter);
      this.jsonWriter.flush();
      return true;
    } catch (JsonIOException | IOException ex) {
      Gdx.app.error("FLUSH", "Problem flushing the file to disk.", ex);
    }
    return false;
  }

  public MainGameScreen[] getSlots() {
    return slots;
  }
}
