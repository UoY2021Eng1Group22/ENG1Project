package com.dragonboatrace.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dragonboatrace.DragonBoatRace;
import com.dragonboatrace.tools.Settings;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Game launcher class for the Desktop platform.
 */
public class DesktopLauncher {

  /**
   * Main function.
   *
   * @param arg Launch arguments.
   */
  public static void main(String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    Settings.setResolution(size.width, size.height);
    Settings.setFULLSCREEN(false);
    config.width = Settings.WIDTH;
    config.height = Settings.HEIGHT;
    config.fullscreen = Settings.FULLSCREEN;
    config.resizable = true;
    config.vSyncEnabled = false;
    config.foregroundFPS = 30;
    new LwjglApplication(new DragonBoatRace(), config);
  }
}
