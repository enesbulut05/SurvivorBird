package com.enesbulut.survivorbird;

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

    public Bird(String file) {
        this.texture = new Texture(file+".png");
        this.circle = new Circle();
        this.birdX = Gdx.graphics.getWidth() / 4;
        this.birdY = Gdx.graphics.getHeight() / 2;
        this.birdWidth = Gdx.graphics.getWidth()/18;
        this.birdHeight = Gdx.graphics.getHeight()/10;
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
