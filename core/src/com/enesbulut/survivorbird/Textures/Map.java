package com.enesbulut.survivorbird.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Map  {

    private Texture[] background;
    private Texture[] mountains;
    private Texture[] sky;
    private float backgroundVelocity;
    private float mountainVelocity;
    private float skyVelocity;
    private float backgroundX;
    private float mountainX;
    private float skyX;

    public void setBackgroundX(float backgroundX) {
        this.backgroundX = backgroundX;
    }

    public void setMountainX(float mountainX) {
        this.mountainX = mountainX;
    }

    public void setSkyX(float skyX) {
        this.skyX = skyX;
    }

    public Texture getBackgroundByIndex(int index) {
        return background[index];
    }

    public Texture getMountainsByIndex(int index) {
        return mountains[index];
    }

    public float getBackgroundX() {
        return backgroundX;
    }

    public float getMountainX() {
        return mountainX;
    }

    public float getSkyX() {
        return skyX;
    }

    public Texture getSkyByIndex(int index) {
        return sky[index];
    }

    public Map(){
        this.background = new Texture[2];
        this.mountains = new Texture[2];
        this.sky = new Texture[2];
        for(int i = 0; i<2;i++){
            background[i] = new Texture("background.png");
            mountains[i] = new Texture("mountains.png");
            sky[i] = new Texture("sky.png");
        }
        this.backgroundVelocity = (Gdx.graphics.getWidth()*0.00264550265f);
        this.mountainVelocity = backgroundVelocity/2;
        this.skyVelocity = mountainVelocity/3;
        this.backgroundX = 0;
        this.mountainX = 0;
        this.skyX = 0;
        this.backgroundVelocity = (Gdx.graphics.getWidth()*0.00264550265f);
        this.mountainVelocity = backgroundVelocity/2;
        this.skyVelocity = mountainVelocity/3;
    }

    public void startMove(){
        backgroundX -= backgroundVelocity;
        mountainX -= mountainVelocity;
        skyX -= skyVelocity;
    }
    public void drawMap(Batch batch){
        batch.draw(getSkyByIndex(0), getSkyX(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(getSkyByIndex(1),  getSkyX() + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(getMountainsByIndex(0),getMountainX(),0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(getMountainsByIndex(1),getMountainX() + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.draw(getBackgroundByIndex(0), getBackgroundX(),-(Gdx.graphics.getHeight()/13.5f),
                Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(getBackgroundByIndex(1), getBackgroundX() + Gdx.graphics.getWidth(), -(Gdx.graphics.getHeight()/13.5f),
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
}
