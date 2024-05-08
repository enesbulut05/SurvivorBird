package com.enesbulut.survivorbird.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

public class Bird {
    private Texture texture;



    private String file;



    private Circle circle;
    private float birdX;
    private float birdY;
    private float birdWidth;
    private float birdHeight;
    private float velocity;
    private float gravity;

    public Bird(String file) {
        this.texture = new Texture(file+".png");
        this.circle = new Circle();
        this.birdX = Gdx.graphics.getWidth() / 4;
        this.birdY = Gdx.graphics.getHeight() / 2;
        this.birdWidth = Gdx.graphics.getWidth()/18;
        this.birdHeight = Gdx.graphics.getHeight()/10;
        this.velocity = 0;
        this.gravity = 0.00046296296f*Gdx.graphics.getHeight();
    }
    public void fly(Buttons buttons){
        if(buttons.isNewModEnabled() && Gdx.input.isTouched()){
            velocity -= 0.00092592592*Gdx.graphics.getHeight();
            if (velocity < - 0.01666666666*Gdx.graphics.getHeight()){
                velocity = (-0.01666666666f*Gdx.graphics.getHeight());
            }
        }

        if (!buttons.isNewModEnabled() &&Gdx.input.justTouched()){
            velocity -= 0.00925925925*Gdx.graphics.getHeight();
            if (velocity < - 0.01666666666*Gdx.graphics.getHeight()){
                velocity = (float) (-0.01666666666*Gdx.graphics.getHeight());
            }
        }
    }
    public void fall(){
        velocity = velocity + gravity;
        setBirdY(getBirdY()-velocity);
    }

    public Circle getCircle() {
        return circle;
    }

    public Texture getTexture() {
        return texture;
    }
    public float getBirdX() {
        return birdX;
    }

    public void setBirdX(float birdX) {
        this.birdX = birdX;
    }

    public float getBirdY() {
        return birdY;
    }

    public void setBirdY(float birdY) {
        this.birdY = birdY;
    }
    public float getBirdWidth() {
        return birdWidth;
    }

    public float getBirdHeight() {
        return birdHeight;
    }
    public void reserBirdY(){
        this.birdY = Gdx.graphics.getHeight()/2;
    }
    public void resetCircle(){
        this.circle.set(birdX + Gdx.graphics.getWidth()/36,birdY +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40);
    }
}
