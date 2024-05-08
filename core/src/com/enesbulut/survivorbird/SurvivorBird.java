package com.enesbulut.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.enesbulut.survivorbird.Generators.TextGenerator;
import com.enesbulut.survivorbird.Settings.ScoreSettings;
import com.enesbulut.survivorbird.Textures.Bird;
import com.enesbulut.survivorbird.Textures.Buttons;
import com.enesbulut.survivorbird.Textures.Enemies;
import com.enesbulut.survivorbird.Textures.Map;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture survivorBird;
	int scoredEnemy=0;
	TextGenerator scoreText,yourScoreText, bestScoreText,gameOverText,newBestScoreText;
	public static int numberOfEnemies = 4;
	Stage stage;
	ScoreSettings scoreSettings;
	Music backgroundMusic,playMusic,deadSound;
	Bird bird;
	Enemies enemies;
	Map map;
	Buttons buttons;

	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		initialize();

		if(buttons.isSoundEnabled()){
		backgroundMusic.play();
		}
	}

	@Override
	public void render () {
		batch.begin();

		map.drawMap(batch);

		if (Info.getScreenStatus() == ScreenStatus.PLAY){
			gameScreen();
		} else if (Info.getScreenStatus() == ScreenStatus.MAIN) {
			mainScreen();
		} else if (Info.getScreenStatus() == ScreenStatus.OVER) {
			gameOverScreen();
		}

		batch.end();
		bird.resetCircle();

		//Crash control
		for (int i = 0; i< numberOfEnemies; i++){
			if(Intersector.overlaps(bird.getCircle(),enemies.getEnemyWave(i).getCircleByIndex(0)) ||
					Intersector.overlaps(bird.getCircle(),enemies.getEnemyWave(i).getCircleByIndex(1)) ||
					Intersector.overlaps(bird.getCircle(),enemies.getEnemyWave(i).getCircleByIndex(2))){
				if(buttons.isSoundEnabled()){
				deadSound.play();
				}
				Info.setScreenStatus(ScreenStatus.OVER);
			}
		}
	}
	@Override
	public void dispose () {

	}
	private void mainScreen(){
		if(buttons.isSoundEnabled()) {
			backgroundMusic.play();
		} else {
			backgroundMusic.stop();
		}
		batch.draw(survivorBird,(Gdx.graphics.getWidth()/2 -survivorBird.getWidth() / 2),
				(Gdx.graphics.getHeight()*0.68518518518f));

		String bestScoreTextWithValue = "Best Score: "+String.valueOf(Info.getBestScore());
		bestScoreText.calculateMid(bestScoreTextWithValue);
		bestScoreText.drawText(batch,bestScoreTextWithValue,
				bestScoreText.calculateMid(bestScoreTextWithValue),Gdx.graphics.getHeight()*0.68518518518f);

		buttons.setVisibilities();

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();

	}
	private void gameScreen(){

		batch.draw(bird.getTexture(),bird.getBirdX(),bird.getBirdY(),bird.getBirdWidth(),bird.getBirdHeight());
		scoreText.drawText(batch,"Score: "+String.valueOf(Info.getScore()),
				Gdx.graphics.getWidth()*0.04f, Gdx.graphics.getHeight()*0.95f);

		map.startMove();

		if(buttons.isSoundEnabled()) {
			backgroundMusic.stop();
			playMusic.play();
		}

		if (map.getBackgroundX() <= -Gdx.graphics.getWidth()) {
			map.setBackgroundX(0);
		}

		if (map.getMountainX() <= -Gdx.graphics.getWidth()) {
			map.setMountainX(0);
		}

		if (map.getSkyX() <= -Gdx.graphics.getWidth()) {
			map.setSkyX(0);
		}

		if(enemies.getEnemyWave(scoredEnemy).getEnemyX()<Gdx.graphics.getWidth()/4){
			Info.setScore(Info.getScore()+1);
			if( scoredEnemy<numberOfEnemies-1) {
				scoredEnemy++;
			} else {
				scoredEnemy=0;
			}
		}

		enemies.moveEnemies(batch);
		bird.fly(buttons);

		if(bird.getBirdY() > 0 && bird.getBirdY() < Gdx.graphics.getHeight()){
			bird.fall();
		} else {
			if(buttons.isSoundEnabled()) {
				deadSound.play();
			}
			Info.setScreenStatus(ScreenStatus.OVER);
		}

	}
	private void gameOverScreen(){
		if(Info.getScore()>=Info.getBestScore()){
			Info.setBestScore(Info.getScore());
			scoreSettings.saveBestScore(Info.getBestScore());
			newBestScoreText.drawText(batch,"New  Best Score!",newBestScoreText.calculateMid("New  Best Score!"),
					0.81f*Gdx.graphics.getHeight());
		} else {
			gameOverText.drawText(batch,"Game Over", gameOverText.calculateMid("Game Over"),
					0.81f*Gdx.graphics.getHeight());
		}

		String yourScoreTextString = "Your Score: "+String.valueOf(Info.getScore());
		yourScoreText.drawText(batch,yourScoreTextString,yourScoreText.calculateMid(yourScoreTextString),
				Gdx.graphics.getHeight()*0.63888888888f);

		if(buttons.isSoundEnabled()) {
			playMusic.stop();
		}

		buttons.setVisibilities();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		bird.reserBirdY();

		enemies.resetEnemies();
		scoredEnemy = 0;
	}

	private void initialize() {
		map = new Map();
		bird = new Bird("bird");
		enemies = new Enemies();
		buttons = new Buttons(stage);

		scoreSettings = new ScoreSettings();
		Info.setBestScore(scoreSettings.getBestScore());
		Info.setScreenStatus(ScreenStatus.MAIN);

		scoreText = new TextGenerator(Color.WHITE,80);
		bestScoreText = new TextGenerator(Color.BLACK,100);
		yourScoreText = new TextGenerator(Color.BLACK,100);
		gameOverText = new TextGenerator(Color.BLACK,200);
		newBestScoreText = new TextGenerator(Color.BLACK,150);

		survivorBird = new Texture("survivorbird.png");

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
		playMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/playMusic.mp3"));
		deadSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/deadSound.wav"));
		backgroundMusic.setVolume(backgroundMusic.getVolume()/2);
		playMusic.setVolume(playMusic.getVolume()/4);
		deadSound.setVolume(deadSound.getVolume() / 4);

	}

}
