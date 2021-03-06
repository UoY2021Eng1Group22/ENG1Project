package com.dragonboatrace.entities.boats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.dragonboatrace.entities.Entity;
import com.dragonboatrace.entities.EntityType;
import com.dragonboatrace.entities.Obstacle;
import com.dragonboatrace.tools.Hitbox;
import com.dragonboatrace.tools.Lane;
import com.dragonboatrace.tools.Settings;
import com.dragonboatrace.tools.state.PostProcessable;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;

/**
 * Represents a generic Boat.
 *
 * @author Benji Garment, Joe Wrieden
 */
public abstract class Boat extends Entity implements PostProcessable {

  /**
   * The rate at which the stamina is used or regenerated at.
   */
  @Expose
  private final float staminaRate = 10;

  /**
   * The minimum amount of speed gained from using stamina.
   */
  @Expose
  private final int minBoostSpeed = 5;

  /**
   * The formatter used to align the text on-screen.
   */
  protected GlyphLayout layout;

  /**
   * The health of the boat.
   */
  @Expose
  protected float health;

  /**
   * The stamina of the boat.
   */
  @Expose
  protected float stamina;

  /**
   * The agility of the boat.
   */
  @Expose
  protected float agility;

  /**
   * The speed of the boat.
   *
   * <p>This is the speed attribute of the boat, not how fast it actually is moving.</p>
   */
  @Expose
  protected float speed;

  /**
   * The maximum amount of stamina the boat can have.
   */
  @Expose
  protected float maxStamina;

  /**
   * The lane of the boat.
   */
  @Expose
  protected Lane lane;

  /**
   * The lanes hit box, use to determine if the boat is still in the lane.
   */
  protected Hitbox laneBox;

  /**
   * The name of the boat.
   */
  @Expose
  protected String name;

  /**
   * The total distance travelled by the boat.
   */
  @Expose
  protected float distanceTravelled = 0.0f;

  /**
   * If there has been a recent collision.
   */
  @Expose
  protected boolean recentCollision = false;

  /**
   * Timer used to countdown for when the boat can move again after a collision.
   */
  @Expose
  protected float collisionTime = 0;

  /**
   * Boat Type of boat used to remember the chosen boat type.
   */
  @Expose
  protected BoatType boatType;

  /**
   * The current time of the boat in the current round.
   */
  @Expose
  protected float time;

  /**
   * Total time added up across all rounds.
   */
  @Expose
  protected float totalTime;

  /**
   * Total time penalties the boat got.
   */
  @Expose
  protected float penaltyTime;

  /**
   * Generator for FreeType Font.
   */
  protected FreeTypeFontGenerator generator;

  /**
   * Parameter for FreeType Font.
   */
  protected FreeTypeFontGenerator.FreeTypeFontParameter parameter;

  /**
   * Font for Health Bar.
   */
  protected BitmapFont healthFont;

  /**
   * Font for Stamina Bar.
   */
  protected BitmapFont staminaFont;

  /**
   * Font for Name Tag.
   */
  protected BitmapFont nameFont;

  /**
   * New attribute which marks if the boat has a shield.
   */
  @Expose
  protected boolean shield;


  /**
   * Creates a Boat with the specified BoatType for pre-defined values,
   * a Lane to give the boat its position and a name for easy identification.
   *
   * @param boat The type of boat to use as a template.
   * @param lane The lane the boat is in.
   * @param name The name of the boat.
   */
  public Boat(BoatType boat, Lane lane, String name) {
    /* Get boat position from the position of the lane. */
    super(new Vector2(
        lane.getHitbox().getXpos() + (lane.getHitbox().getWidth()
            - EntityType.BOAT.getWidth()) / 2.0f,
        100), new Vector2(), EntityType.BOAT, boat.getImageSrc());
    this.health = boat.getHealth();
    this.stamina = boat.getStamina();
    this.agility = boat.getAgility();
    this.speed = boat.getSpeed();
    this.maxStamina = boat.getStamina();
    this.lane = lane;
    this.name = name;
    this.boatType = boat;
    this.time = 0;
    this.totalTime = 0;
    this.penaltyTime = 0;

    // New boolean variable
    this.shield = false;

    /* Store the lanes hit box to save time on using Getters. */
    laneBox = lane.getHitbox();

    /* Setup fonts to use in the HUD */
    this.generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    this.layout = new GlyphLayout();

    /*Font for displaying the name */
    parameter.size = 50;
    parameter.color = Color.BLACK;
    this.nameFont = generator.generateFont(parameter);

    layout.setText(nameFont, this.name);
    if (this.layout.width > this.laneBox.getWidth()) {
      parameter.size = (int) (50 / (this.layout.width / this.laneBox.getWidth()));
      parameter.color = Color.BLACK;
      nameFont = generator.generateFont(parameter);
    }

    /* Font for displaying the health */
    parameter.size = 50;
    parameter.color = Color.RED;
    this.healthFont = generator.generateFont(parameter);

    layout.setText(healthFont, "Health:  XXX");
    if (this.layout.width > this.laneBox.getWidth()) {
      parameter.size = (int) (50 / (this.layout.width / this.laneBox.getWidth()));
      parameter.color = Color.RED;
      healthFont = generator.generateFont(parameter);
    }

    /* Font for displaying the stamina */
    parameter.size = 50;
    parameter.color = Color.GREEN;
    this.staminaFont = generator.generateFont(parameter);

    layout.setText(staminaFont, "Stamina: XXX");
    if (this.layout.width > this.laneBox.getWidth()) {
      parameter.size = (int) (50 / (this.layout.width / this.laneBox.getWidth()));
      parameter.color = Color.GREEN;
      staminaFont = generator.generateFont(parameter);
    }

  }

  /**
   * Return a scalar to multiply the velocity by when using stamina.
   *
   * @return A float between 0.25 and 1 which is then scaled
   *     by {@link com.dragonboatrace.tools.Settings#STAMINA_SPEED_DIVISION}.
   */
  protected float velocityPercentage() {
    double result = 0.25 + Math.log(this.stamina + 1) / 3;
    return (float) result / Settings.STAMINA_SPEED_DIVISION;
  }

  /**
   * Get the amount of stamina to use for the current amount of stamina.
   *
   * <p>The amount of stamina that gets used per cycle is not linear.
   * The more stamina you have the slower it is used,
   * and the less stamina the faster.
   *
   * @return A float of how much stamina will be used.
   */
  protected float useStamina() {
    double result =
        Math.pow(this.maxStamina, -this.stamina / (2 * this.maxStamina)) * this.staminaRate
            + this.staminaRate + this.minBoostSpeed;
    return (float) result;
  }

  /**
   * Get the amount of stamina that will be gained for the current amount of stamina.
   *
   * @return A float of how much stamina will be gained.
   */
  protected float regenerateStamina() {
    double result =
        -1 * this.staminaRate * Math.pow(this.maxStamina, -this.stamina / (2 * this.maxStamina))
            + this.staminaRate + 1;
    return (float) result / 10;
  }

  /**
   * Update the position of the boat in relation to the amount of time passed.
   *
   * @param deltaTime The time passed since the last frame.
   */
  public void update(float deltaTime) {

    /* Check if boat is still in the lane */
    if (this.getHitBox().leaves(this.laneBox)) {
      this.penaltyTime += 0.1;
    }
    this.penaltyTime = Math.round(this.penaltyTime * 100) / (float) 100;


    this.distanceTravelled += this.velocity.y * deltaTime;

    /* Update lane contents */
    this.lane.update(deltaTime, this.velocity.y);

    /* Slowly return the velocity to 0 */
    float dampen = agility / 100;
    if (!(this.velocity.isZero((float) 0.001))) {
      this.position.x += this.velocity.x * deltaTime;
      this.velocity.scl(dampen);
    }

    /* The hit box needs moving to keep at the same pos as the boat */
    this.hitbox.move(position.x, position.y);
  }

  /**
   * Render the boat and its lane contents. Renders the boat as well as its health and stamina.
   *
   * @param batch The SpriteBatch that the renders will be added to.
   */
  public void render(SpriteBatch batch) {
    this.lane.render(batch);

    layout.setText(healthFont, "Health:  XXX");

    healthFont.draw(batch, "Health:  " + (int) this.getHealth(),
        this.lane.getHitbox().getXpos() + 5,
        Gdx.graphics.getHeight() - 55);

    layout.setText(staminaFont, "Stamina: XXX");

    staminaFont.draw(batch, "Stamina: " + (int) this.getStamina(),
        this.lane.getHitbox().getXpos() + 5,
        Gdx.graphics.getHeight() - 105);

    super.render(batch);
  }

  /**
   * Check for collisions by getting the contents of the lane
   * and checking their positions to the boat position.
   *
   * @return True if a collision occurred, False if no collision.
   */
  protected boolean checkCollisions() {
    ArrayList<Obstacle> obstacles = this.lane.getObstacles();
    int size = obstacles.size();
    for (int i = 0; i < size; i++) {
      Obstacle obstacle = obstacles.get(i);
      if (obstacle.getHitBox().collidesWith(this.hitbox)) {
        obstacle.dispose();
        this.lane.removeObstacle(obstacle);

        /*
         * This switch case is new.
         * Switches on the obstacles texture (as each unique powerup has a unique texture)
         */
        switch (obstacle.getObstacleType().getTexture()) {
          case "Stamina.png":
            // Adds stamina, up to the max
            if (this.stamina + 50 > this.boatType.getStamina()) {
              this.stamina = this.boatType.getStamina();
            } else {
              this.stamina += 50;
            }
            return false;
          case "Speed.png":
            // Adds speed
            this.speed += 30;
            return false;
          case "Heal.png":
            // Adds health, to the max
            if ((this.health -= obstacle.getDamage()) > this.boatType.getHealth()) {
              this.health = this.boatType.getHealth();
            } else {
              this.health -= obstacle.getDamage();
            }
            return false;
          case "Shield.png":
            // Applies a shield
            if (!(this.shield)) {
              this.shield = true;
            }
            return false;


          case "Agility.png":
            // Adds agility
            this.agility += 30;
            return false;

          default:
            //if the player has a shield powerup,
            // the damage and slowdown is blocked from regular obstacles.
            if (!(this.shield)) {
              size--;
              this.health -= obstacle.getDamage();
            } else {
              this.shield = false;
              return false;
            }
        }


        return true;
      }
    }
    return false;
  }

  /**
   * Update the vertical position of the boat onscreen.
   *
   * <p>This is not implemented in the generic Boat class
   * and must be implemented in the specific Boat kind.
   *
   * @param lineHeight   The height of the finish line Entity.
   * @param raceDistance The length of the race.
   */
  public void updateyPosition(int lineHeight, int raceDistance) {

  }

  /* Adders */

  /**
   * Increase the velocity of the boat with a push.
   *
   * @param pushX The x component of the push
   * @param pushY The y component of the push.
   */
  public void addVelocity(float pushX, float pushY) {
    this.velocity.add(pushX, pushY);
  }

  /**
   * Increase the current boat health.
   *
   * @param change The amount of health to be added.
   */
  public void addHealth(float change) {
    this.health += change;
  }

  /**
   * Increase the current boat stamina.
   *
   * @param change The amount of health to be added.
   */
  public void addStamina(float change) {
    this.stamina += change;
  }

  /* Getters */

  /**
   * Get the current velocity of the boat.
   *
   * @return A Vector2 of the boats current velocity.
   */
  public Vector2 getVelocity() {
    return this.velocity;
  }

  /**
   * Get the speed of the boat.
   *
   * <p>This is the speed property of the boat, not the speed at which it is moving.
   *
   * @return The speed of the boat.
   */
  public float getSpeed() {
    return this.speed;
  }

  /**
   * Get the current health of the boat.
   *
   * @return The health of the boat as a float.
   */
  public float getHealth() {
    return this.health;
  }

  /**
   * Get the current stamina of the boat.
   *
   * @return The stamina of the boat as a float.
   */
  public float getStamina() {
    return this.stamina;
  }

  /**
   * Get the current agility of the boat.
   *
   * @return The agility of the boat as a float.
   */
  public float getAgility() {
    return this.agility;
  }

  /**
   * Get the lane that the boat is in.
   *
   * @return The actual Lane object the boat is in.
   */
  public Lane getLane() {
    return this.lane;
  }

  /**
   * Get the name of the boat.
   *
   * @return A string of the boats name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Get the boat type.
   *
   * @return A BoatType.
   */
  public BoatType getBoatType() {
    return this.boatType;
  }

  /**
   * Get the boat time.
   *
   * @return A float of boat time
   */
  public float getTime() {
    return this.time;
  }

  /**
   * Set the boat time.
   *
   * @param nowTime The time passed since last call.
   */
  public void setTime(float nowTime) {
    this.time += nowTime;
  }

  /**
   * Get the total boat time.
   *
   * @return A float of total boat time
   */
  public float getTotalTime() {
    return this.totalTime;
  }

  /**
   * Set the total boat time.
   *
   * @param nowTime The time passed since last call.
   */
  public void setTotalTime(float nowTime) {
    this.totalTime += nowTime;
  }

  public float getPenaltyTime() {
    return this.penaltyTime;
  }

  /**
   * Get the total distance travelled by the boat so far.
   *
   * @return A float of the distance travelled.
   */
  public float getDistanceTravelled() {
    return this.distanceTravelled;
  }

  /**
   * Dispose of the fonts used in the HUD and then perform {@link Entity}'s dispose.
   */
  public void dispose() {

    this.lane.dispose();
    super.dispose();
  }

  // P2
  @Override
  public void postProcess() {

    super.postProcess();

    this.setTexture(boatType.getImageSrc());
    this.laneBox = this.lane.getHitbox();

    /* Setup fonts to use in the HUD */
    this.generator = new FreeTypeFontGenerator(Gdx.files.internal("osaka-re.ttf"));
    this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    this.layout = new GlyphLayout();

    /*Font for displaying the name */
    parameter.size = 50;
    parameter.color = Color.BLACK;
    this.nameFont = generator.generateFont(parameter);

    layout.setText(nameFont, this.name);
    if (this.layout.width > this.laneBox.getWidth()) {
      parameter.size = (int) (50 / (this.layout.width / this.laneBox.getWidth()));
      parameter.color = Color.BLACK;
      nameFont = generator.generateFont(parameter);
    }

    /* Font for displaying the health */
    parameter.size = 50;
    parameter.color = Color.RED;
    this.healthFont = generator.generateFont(parameter);

    layout.setText(healthFont, "Health:  XXX");
    if (this.layout.width > this.laneBox.getWidth()) {
      parameter.size = (int) (50 / (this.layout.width / this.laneBox.getWidth()));
      parameter.color = Color.RED;
      healthFont = generator.generateFont(parameter);
    }

    /* Font for displaying the stamina */
    parameter.size = 50;
    parameter.color = Color.GREEN;
    this.staminaFont = generator.generateFont(parameter);

    layout.setText(staminaFont, "Stamina: XXX");
    if (this.layout.width > this.laneBox.getWidth()) {
      parameter.size = (int) (50 / (this.layout.width / this.laneBox.getWidth()));
      parameter.color = Color.GREEN;
      staminaFont = generator.generateFont(parameter);
    }

  }

}
