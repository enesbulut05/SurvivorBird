package com.enesbulut.survivorbird.Textures;

import static com.enesbulut.survivorbird.SurvivorBird.numberOfEnemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public class Enemies {
    private EnemyWave[] enemies;
    public Enemies() {
        this.enemies = new EnemyWave[numberOfEnemies];
        for (int i = 0 ; i<numberOfEnemies ; i++){
            enemies[i] = new EnemyWave("bee", i);
            enemies[i].generateEnemyOffSet();
        }
    }

    public EnemyWave getEnemyWave(int index ) {
        return enemies[index];
    }
    public void moveEnemies(SpriteBatch batch){
        for(int i = 0; i<numberOfEnemies; i++) {

            enemies[i].upEnemyVelocity();

            if(enemies[i].getEnemyX() < -0.04629629629* Gdx.graphics.getWidth()){
                enemies[i].setEnemyX(enemies[i].getEnemyX() + numberOfEnemies * enemies[i].getDistance());
                enemies[i].generateEnemyOffSet();
            } else {
                enemies[i].setEnemyX(enemies[i].getEnemyX()-enemies[i].getEnemyVelocity());
            }


            for(int j = 0; j<3 ; j++){
                batch.draw(enemies[i].getTextureByIndex(j), enemies[i].getEnemyX(),
                        Gdx.graphics.getHeight()/2 + enemies[i].getEnemyOffSetByIndex(j),
                        Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);
                enemies[i].setCircleIndex(j,new Circle(enemies[i].getEnemyX() + Gdx.graphics.getWidth() / 36 ,
                        Gdx.graphics.getHeight()/2 + enemies[i].getEnemyOffSetByIndex(j) +
                                Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 50));
            }


        }
    }
    public void resetEnemies(){
        for (int i = 0; i < numberOfEnemies; i++) {

            getEnemyWave(i).setEnemyX(Gdx.graphics.getWidth() - getEnemyWave(i).getTextureByIndex(0).getWidth() /2
            + i * getEnemyWave(i).getDistance());

            getEnemyWave(i).generateEnemyOffSet();
            getEnemyWave(i).resetCircles();

        }
    }
}
