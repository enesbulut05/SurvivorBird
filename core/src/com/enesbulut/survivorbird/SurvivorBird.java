package com.enesbulut.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.enesbulut.survivorbird.Settings.ScoreSettings;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture survivorBird;
	float velocity = 0;
	float gravity;
	//int score = 0;
	//int bestScore;
	int scoredEnemy=0;
	TextGenerator scoreText;
	TextGenerator yourScoreText;
	TextGenerator bestScoreText;
	TextGenerator gameOverText;
	TextGenerator newBestScoreText;
	static int numberOfEnemies = 4;
	Stage stage;
	ScoreSettings scoreSettings;
	Music backgroundMusic;
	Music playMusic;
	Music deadSound;
	Bird bird;
	Enemies enemies;
	Map map;
	Buttons buttons;
	TextGenerator buttonText;



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

		if (Info.getGameState() == 1){
			gameScreen();
		} else if (Info.getGameState() == 0) {
			mainScreen();
		} else if (Info.getGameState() == 2) {
			gameOverScreen();
		}

		batch.end();
		bird.resetCircle();

		for (int i = 0; i< numberOfEnemies; i++){
			if(Intersector.overlaps(bird.getCircle(),enemies.getEnemyWave(i).getCircleByIndex(0)) ||
					Intersector.overlaps(bird.getCircle(),enemies.getEnemyWave(i).getCircleByIndex(1)) ||
					Intersector.overlaps(bird.getCircle(),enemies.getEnemyWave(i).getCircleByIndex(2))){
				if(buttons.isSoundEnabled()){
				deadSound.play();
				}
				Info.setGameState(2);
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

		if(buttons.isNewModEnabled() && Gdx.input.isTouched()){
			velocity -= 0.00092592592*Gdx.graphics.getHeight();
			if (velocity < - 0.01666666666*Gdx.graphics.getHeight()){
				velocity = (float) (-0.01666666666*Gdx.graphics.getHeight());
			}
		}

		if (!buttons.isNewModEnabled() &&Gdx.input.justTouched()){

			velocity -= 0.00925925925*Gdx.graphics.getHeight();
			if (velocity < - 0.01666666666*Gdx.graphics.getHeight()){
				velocity = (float) (-0.01666666666*Gdx.graphics.getHeight());
			}
		}

		if(bird.getBirdY() > 0 && bird.getBirdY() < Gdx.graphics.getHeight()){
			velocity = velocity + gravity;
			bird.setBirdY(bird.getBirdY()-velocity);
		} else {
			if(buttons.isSoundEnabled()){
				deadSound.play();
			}
			Info.setGameState(2);
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

		for (int i = 0; i < numberOfEnemies; i++) {
			//enemies = new Enemies();
			enemies.resetEnemies();
		}
		velocity = 0;
		scoredEnemy = 0;
	}

	private void initialize() {
		map = new Map();
		bird = new Bird("bird");
		enemies = new Enemies();
		buttons = new Buttons(stage);

		scoreSettings = new ScoreSettings();
		Info.setBestScore(scoreSettings.getBestScore());
		Info.setGameState(0);

		scoreText = new TextGenerator(Color.WHITE,80);
		bestScoreText = new TextGenerator(Color.BLACK,100);
		yourScoreText = new TextGenerator(Color.BLACK,100);
		gameOverText = new TextGenerator(Color.BLACK,200);
		newBestScoreText = new TextGenerator(Color.BLACK,150);

		survivorBird = new Texture("survivorbird.png");
		gravity =0.00046296296f*Gdx.graphics.getHeight();

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
		playMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/playMusic.mp3"));
		deadSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/deadSound.wav"));
		backgroundMusic.setVolume(backgroundMusic.getVolume()/2);
		playMusic.setVolume(playMusic.getVolume()/4);
		deadSound.setVolume(deadSound.getVolume() / 4);

	}

}
