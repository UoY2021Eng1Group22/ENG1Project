package com.dragonboatrace.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.lang.Math;

public class Obstacle extends Entity{

    protected Texture img;
    protected ObstacleType type;

	public Obstacle(ObstacleType type) {
        super(new Vector2((int)(Math.random()*(Gdx.graphics.getWidth())), Gdx.graphics.getHeight()), "square.jpg");
        this.type = type;
        
    }


    public void update(float deltaTime){
        this.vel = new Vector2(0, -100).scl(deltaTime);
        super.update(deltaTime);
        
    }

    public void destructor(){
        
    }

    public ObstacleType getType(){return this.type;}

}
