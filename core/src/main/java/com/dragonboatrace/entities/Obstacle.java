package com.dragonboatrace.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.annotations.Expose;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Represents an Obstacle.
 *
 * @author Benji Garment, Joe Wrieden
 */
public class Obstacle extends Entity {

  /**
   * The speed of the obstacle.
   */
  @Expose
  private final float speed;


  /**
   * The damage the obstacle will deal when colliding with a player.
   */
  @Expose
  private final float damage;

  // New variable to store the obstacles type.
  @Expose
  private final ObstacleType obstacleType;

  /**
   * Creates a new Obstacle of a specific type and bounds in which it can be created.
   *
   * @param obstacleType The type of obstacle.
   * @param startX       The starting x value the obstacle can be created in.
   * @param width        How far from startX the obstacle can be created.
   */
  public Obstacle(ObstacleType obstacleType, float startX, int width) {
    /* Entity creation */
    /* First vector is long as to start it at a random x position within the bounds of the screen */
    /* Form of Entity(Vector2 pos, Vector2 vel, EntityType type, String texture) */
    super(new Vector2(((int) startX + width) / 2.0f + ThreadLocalRandom.current()
        .nextInt(-((int) startX + width) / 2 + EntityType.OBSTACLE.getWidth() / 2,
            ((int) startX + width) / 2 + EntityType.OBSTACLE.getWidth() / 2),
        Gdx.graphics.getHeight()), new Vector2(), EntityType.OBSTACLE, obstacleType.getTexture());
    this.speed = obstacleType.getSpeed();
    this.damage = obstacleType.getDamage();

    // the obstacles type
    this.obstacleType = obstacleType;
  }

  /**
   * Update the obstacle's position relative to the time passed
   * since last frame and the velocity of the boat in that lane.
   *
   * @param deltaTime The time since last frame.
   * @param velY      The y-velocity of the boat in the same lane as the obstacle.
   */
  public void update(float deltaTime, float velY) {
    this.position.add(0, -1 * (velY + this.speed) * deltaTime);
    this.hitbox.move(this.position.x, this.position.y);
  }

  /**
   * Get the obstacles speed attribute, not the velocity it is moving at currently.
   *
   * @return A float of the obstacles speed attribute.
   */
  public float getSpeed() {
    return this.speed;
  }

  /**
   * Get the amount of damage the obstacle will deal at a collision.
   *
   * @return A float of the amount of damage.
   */
  public float getDamage() {
    return this.damage;
  }


  /**
   * The position of the obstacle.
   *
   * @return A Vector2 of the position of the obstacle.
   */
  public Vector2 getPos() {
    return this.position;
  }

  // returns the Obstacles type
  public ObstacleType getObstacleType() {
    return this.obstacleType;
  }

  @Override
  public void postProcess() {
    super.postProcess();
    this.setTexture(obstacleType.getTexture());
  }

}
