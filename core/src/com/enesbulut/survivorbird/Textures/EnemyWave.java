package com.enesbulut.survivorbird.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.enesbulut.survivorbird.Info;

import java.util.Random;

public class EnemyWave {
    private Texture[] textures;
    private Circle[] circles;
    private float[] enemyOffSet;

    private float enemyWidth;
    private float enemyHeight;
    private float distance;
    private float enemyVelocity;
    private float enemyX;

    public Texture getTextureByIndex(int index) {
        return textures[index];
    }

    public Circle getCircleByIndex(int index) {
        return circles[index];
    }

    public void setCircles(Circle[] circles) {
        this.circles = circles;
    }
    public void resetCircles(){
        circles[0] = new Circle();
        circles[1] = new Circle();
        circles[2] = new Circle();
    }
    public void setCircleIndex(int index,Circle circle) {
        this.circles[index] = circle;
    }

    public float getEnemyOffSetByIndex(int index) {
        return enemyOffSet[index];
    }

    public float getEnemyX() {
        return enemyX;
    }

    public void setEnemyX(float enemyX) {
        this.enemyX = enemyX;
    }

    public float getDistance() {
        return distance;
    }

    public float getEnemyVelocity() {
        return enemyVelocity;
    }

    public EnemyWave(String file, int j){
        this.textures = new Texture[3];
        circles = new Circle[3];
        for (int i = 0; i < 3; i++){
            textures[i] = new Texture(file + ".png");
            circles[i] = new Circle();
        }
        this.enemyWidth = Gdx.graphics.getWidth() / 18;
        this.enemyHeight = Gdx.graphics.getHeight() / 10;
        this.enemyOffSet = new float[3];
        this.distance = Gdx.graphics.getWidth() / 2;
        this.enemyVelocity = (0.00440917107f*Gdx.graphics.getWidth());
        this.enemyX = Gdx.graphics.getWidth() - getTextureByIndex(0).getWidth() / 2 + j * distance;

    }
    public void generateEnemyOffSet(){
        Random random = new Random();
        enemyOffSet[0] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815f );
        enemyOffSet[1] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815f );
        while (Math.abs(enemyOffSet[1] - enemyOffSet[0]) < Gdx.graphics.getWidth()*0.09259259259) {
            enemyOffSet[1] =(random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815f);
        }

        enemyOffSet[2] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815f );
        while (Math.abs(enemyOffSet[2] - enemyOffSet[0]) < Gdx.graphics.getWidth()*0.09259259259
                || Math.abs(enemyOffSet[2] - enemyOffSet[1]) < Gdx.graphics.getHeight()/10.2) {
            enemyOffSet[2] =(random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815f );
        }

    }

    public void upEnemyVelocity () {
        int score = Info.getScore();
        if (score <= 20 && score % 5 == 0) {
            enemyVelocity = (score/10 + (0.00440917107f*Gdx.graphics.getWidth()));
        }
        if (score <= 50 &&score > 20 && score % 10== 0) {
            enemyVelocity =  (score/15 + (0.00529100529f*Gdx.graphics.getWidth()));
        }
        if (score < 100 && score > 50 && score % 10== 0) {
            enemyVelocity = (score/60 + Gdx.graphics.getWidth()*0.00676072898f);
        }
    }


}
