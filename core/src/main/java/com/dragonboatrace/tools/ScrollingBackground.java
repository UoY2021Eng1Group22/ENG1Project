package com.dragonboatrace.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dragonboatrace.tools.state.PostProcessable;
import com.google.gson.annotations.Expose;

/**
 * Represents the scrolling background.
 *
 * @author Benji Garment, Joe Wrieden
 */
public class ScrollingBackground implements PostProcessable {

  /**
   * The texture to use as the background.
   */
  private Texture image; // P2 - hydrate

  // P2: checkstyle
  /**
   * The x position that both the images use to align vertically.
   */
  @Expose // P2
  private float xposition;
  /**
   * The y position of the first image.
   */
  @Expose // P2
  private float y1;
  /**
   * The y position the second image.
   */
  @Expose // P2
  private float y2;
  /**
   * The scale at which to draw the background.
   */
  @Expose
  private float imageScale;

  /**
   * Creates a new scrolling background.
   */
  public ScrollingBackground() {
    this.image = new Texture(Gdx.files.local("background.png"));
    this.y1 = 0;
    this.y2 = image.getHeight();
    this.imageScale = 1;
    this.xposition = 0; // P2: checkstyle
  }

  // P2
  /**
   * Creates a ScrollingBackground but is scrolled to a specific position.
   */
  public ScrollingBackground(float y1, float y2, float xposition) {
    this.image = new Texture(Gdx.files.local("background.png"));
    this.y1 = y1;
    this.y2 = y2;
    this.imageScale = 1;
    this.xposition = xposition;
  }

  /**
   * Scroll the background according the distance travelled by the player.
   *
   * @param deltaDistance The distance travelled in the last frame by the player.
   */
  public void update(float deltaDistance) {
    y1 -= deltaDistance;
    y2 -= deltaDistance;
    // Push the second image to the top if at the bottom
    //   and the first image to the top if at the bottom.
    if (y1 + image.getHeight() * imageScale <= 0) {
      y1 = y2 + image.getHeight() * imageScale;
    }

    if (y2 + image.getHeight() * imageScale <= 0) {
      y2 = y1 + image.getHeight() * imageScale;
    }
  }

  /**
   * Render the background at its current y1 and y2 positions.
   *
   * @param batch The SpriteBatch to be added to.
   */
  public void render(SpriteBatch batch) {
    /* render background at the position given by the vector */
    batch.draw(image, this.xposition, y1, image.getWidth() * imageScale,
        image.getHeight() * imageScale);
    batch.draw(image, this.xposition, y2, image.getWidth() * imageScale,
        image.getHeight() * imageScale);
  }

  /**
   * Resize the background to scale with width.
   *
   * @param width The width to scale to.
   */
  public void resize(int width) {
    imageScale = (float) width / image.getWidth();
    y1 = 0;
    y2 = image.getHeight() * imageScale;
  }

  // P2

  @Override
  public void postProcess() {
    this.image = new Texture(Gdx.files.local("background.png"));
  }
}
