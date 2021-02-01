package com.dragonboatrace.entities.boats;

/**
 * Represents a type of boat with predefined values.
 *
 * @author Benji Garment, Joe Wrieden
 */
public enum BoatType {
    /* ENUM(health, stamina, agility, speed, maxSpeed, image) */

    FAST(50, 120, 85, 275, "fast.png"),
    AGILE(50, 120, 95, 245, "agile.png"),
    ENDURANCE(70, 180, 90, 245, "endurance.png"),
    STRONG(100, 105, 98, 200, "strong.png"),

    FASTEASY(60, 144, 102, 330, "fast.png"),
    AGILEEASY(60, 144, 114, 292, "agile.png"),
    ENDURANCEEASY(84, 216, 108, 294, "endurance.png"),
    STRONGEASY(120, 126, 118, 240, "strong.png"),

    FASTHARD(40, 96, 68, 220, "fast.png"),
    AGILEHARD(40, 96, 76, 196, "agile.png"),
    ENDURANCEHARD(56, 144, 72, 196, "endurance.png"),
    STRONGHARD(80, 84, 78, 160, "strong.png");

    private final float health;
    private final float stamina;
    private final float agility;
    private final float speed;
    private final String imageSrc;

    /**
     * Creates a boat template that can define a {@link Boat}.
     *
     * @param health   The health of the boat.
     * @param stamina  The stamina of the boat.
     * @param agility  The agility of the boat.
     * @param speed    The speed of the boat.
     * @param imageSrc The path of the texture to use for the boat.
     */
    BoatType(float health, float stamina, float agility, float speed, String imageSrc) {
        this.health = health;
        this.stamina = stamina;
        this.agility = agility;
        this.speed = speed;
        this.imageSrc = imageSrc;
    }

    /**
     * Get the health of the boat type.
     *
     * @return A float of the boat type's health.
     */
    public float getHealth() {
        return this.health;
    }

    /**
     * Get the stamina of the boat type.
     *
     * @return A float of the boat type's stamina.
     */
    public float getStamina() {
        return this.stamina;
    }

    /**
     * Get the agility of the boat type.
     *
     * @return A float of the boat type's agility.
     */
    public float getAgility() {
        return this.agility;
    }

    /**
     * Get the speed of the boat type.
     *
     * @return A float of the boat type's speed.
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Get the path of the image used for the texture.
     *
     * @return A string of the path to the image used for the texture.
     */
    public String getImageSrc() {
        return this.imageSrc;
    }

}
