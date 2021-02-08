package com.dragonboatrace.tests;

import static org.junit.Assert.assertTrue;


import com.badlogic.gdx.Gdx;
import com.dragonboatrace.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(GdxTestRunner.class)
public class TestAssets {

  @Test
  public void isAvailable() {
    String[] fileNames = new String[] {
        "agile.png",
        "agile_button_active.png",
        "agile_button_inactive.png",
        "Agility.png",
        "back_button_active.png",
        "back_button_inactive.png",
        "background.png",
        "branch.png",
        "continue_button_active.png",
        "continue_button_inactive.png",
        "down_arrow.png",
        "dragon.png",
        "easy_button_active.png",
        "easy_button_inactive.png",
        "easy_difficulty.png",
        "endurance.png",
        "endurance_button_active.png",
        "endurance_button_inactive.png",
        "exit_button_active.png",
        "exit_button_inactive.png",
        "fast.png",
        "fast_button_active.png",
        "fast_button_inactive.png",
        "finish.png",
        "hard_button_active.png",
        "hard_button_inactive.png",
        "hard_difficulty.png",
        "Heal.png",
        "help_button_active.png",
        "help_button_inactive.png",
        "help_screen_info.png",
        "leaf.png",
        "line.png",
        "medium_button_active.png",
        "medium_button_inactive.png",
        "medium_difficulty.png",
        "menu_button_active.png",
        "menu_button_inactive.png",
        "osaka-re.ttf",
        "play_button_active.png",
        "play_button_inactive.png",
        "rock.png",
        "save_button_active.png",
        "save_button_inactive.png",
        "settings_button_active.png",
        "settings_button_inactive.png",
        "Shield.png",
        "slot1_button_active.png",
        "slot1_button_inactive.png",
        "slot2_button_active.png",
        "slot2_button_inactive.png",
        "slot3_button_active.png",
        "slot3_button_inactive.png",
        "Speed.png",
        "Stamina.png",
        "strong.png",
        "strong_button_active.png",
        "strong_button_inactive.png",
        "up_arrow.png"
    };

    for (String fn : fileNames) {
      assertTrue(String.format("%s does not exist.", fn), Gdx.files.internal(fn).exists());
    }

  }

}
