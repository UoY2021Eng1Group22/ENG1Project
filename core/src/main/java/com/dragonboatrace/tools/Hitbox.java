package com.dragonboatrace.tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Represents an Entities Hitbox.
 *
 * @author Benji Garment, Joe Wrieden
 */
public class Hitbox {

  /**
   * The width of the hit box.
   */
  private final int width;
  /**
   * The height of the hit box.
   */
  private final int height;
  /**
   * Used to render the hit boxes when debugging.
   */
  private final ShapeRenderer renderer;
  /**
   * The x position of the bottom left corner.
   */
  private float xpos;
  /**
   * The y position of the bottom left corner.
   */
  private float ypos;

  /**
   * Create a new hit box at a specified position with a width and height.
   *
   * @param xpos      The x position of the bottom left corner.
   * @param ypos      The y position of the bottom left corner.
   * @param width  The width of the hit box.
   * @param height The height of the hit box
   */
  public Hitbox(float xpos, float ypos, int width, int height) {
    this.xpos = xpos;
    this.ypos = ypos;
    this.width = width;
    this.height = height;
    renderer = new ShapeRenderer();
  }

  /**
   * Move the hit box's position to the one specified, not additive.
   *
   * <p>Used to make sure the hit boxes position is the same as the entity it represents.
   *
   * @param x The new x position.
   * @param y The new y position.
   */
  public void move(float x, float y) {
    this.xpos = x;
    this.ypos = y;
  }

  /**
   * Render the hit boxes for debugging.
   */
  public void render() {
    renderer.begin(ShapeRenderer.ShapeType.Line);
    renderer.setColor(Color.RED);
    renderer.rect(this.xpos, this.ypos, this.width, this.height);
    renderer.end();
  }

  /**
   * Check if the hit box is colliding with another hit box.
   *
   * @param box The hit box that the collision is being checked against.
   * @return A boolean of if the two hit boxes are intersecting.
   */
  public boolean collidesWith(Hitbox box) {
    return this.xpos + this.width > box.getXpos() && this.xpos < box.getXpos() + box.getWidth()
        && this.ypos < box.getYpos() + box.getHeight() && this.ypos + this.height > box.getYpos();
  }

  /**
   * Checks if the another hit box has left the area of this hit box.
   *
   * @param box The other hit box.
   * @return A boolean of if the other hit box is partially outside of this hit box
   */
  public boolean leaves(Hitbox box) {
    return this.xpos + this.width > box.getXpos() + box.getWidth() || this.xpos < box.getXpos()
        || this.ypos < box.getYpos() && this.ypos + this.height > box.getYpos();
  }

  /**
   * Get the x coordinate of the hit box.
   *
   * @return A float of the x position of the bottom left corner.
   */
  public float getXpos() {
    return this.xpos;
  }

  /**
   * Get the y coordinate of the hit box.
   *
   * @return A float of the y position of the bottom left corner.
   */
  public float getYpos() {
    return this.ypos;
  }

  /**
   * Get the width of the hit box.
   *
   * @return An int representing how wide the hit box is.
   */
  public int getWidth() {
    return width;
  }

  /**
   * Get the height of the hit box.
   *
   * @return An int representing how tall the hit box is.
   */
  public int getHeight() {
    return height;
  }
}
