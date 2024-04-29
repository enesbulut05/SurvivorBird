package com.enesbulut.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture[] background;
	Texture[] mountains;
	Texture[] sky;
	Texture gameOver;
	Texture newBestScore;
	Texture survivorBird;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	Random random;

	float birdX = 0;
	float birdY = 0;
	int gameState = 0;
	float velocity = 0;
	float gravity;
	float backgroundVelocity;
	float mountainVelocity;
	float skyVelocity;
	float backgroundX;
	float mountainX;
	float skyX;
	float enemyVelocity;
	int score = 0;
	int bestScore;
	int scoredEnemy=0;
	BitmapFont scoreFont;
	BitmapFont yourScoreFont;
	BitmapFont bestScoreFont;
	BitmapFont survivorBirdFont;
	Circle birdCircle;

	boolean soundEnabled = true;
	boolean newModEnabled = true;
	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet1 = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float distance = 0;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;
	Stage stage;
	private ImageButton playButton;
	private ImageButton homeButton;
	private ImageButton againButton;
	private ImageButton scoresButton;
	private ImageButton soundButton;
	private ImageButton exitButton;
	private ImageButton modeButton;
	private TextureAtlas buttonAtlas;
	ScoreSettings scoreSettings;

	Music backgroundMusic;
	Music playMusic;
	Sound hitSound;
	Music deadSound;




	@Override
	public void create () {
		batch = new SpriteBatch();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		initialize();
		buttons();
		scoreSettings = new ScoreSettings();
		bestScore = scoreSettings.getBestScore();

		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/mainMusic.mp3"));
		playMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/playMusic.mp3"));
		deadSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/deadSound.wav"));
		backgroundMusic.setVolume(backgroundMusic.getVolume()/2);
		playMusic.setVolume(playMusic.getVolume()/4);
		deadSound.setVolume(deadSound.getVolume() / 4);






		if(soundEnabled){
		backgroundMusic.play();
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(sky[0], skyX, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(sky[1], skyX + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch.draw(mountains[0], mountainX, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(mountains[1], mountainX + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch.draw(background[0], backgroundX, (float) -(Gdx.graphics.getHeight()/13.5), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(background[1], backgroundX + Gdx.graphics.getWidth(), (float) -(Gdx.graphics.getHeight()/13.5), Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




		if (gameState == 1){

			batch.draw(bird,birdX,birdY, Gdx.graphics.getWidth()/18,Gdx.graphics.getHeight()/10);
			scoreFont.draw(batch,"Score: "+String.valueOf(score), (float) (Gdx.graphics.getWidth()*0.04), (float) (Gdx.graphics.getHeight()*0.95));

			backgroundX -= backgroundVelocity;
			mountainX -= mountainVelocity; // Mountain'ın hareketi
			skyX -= skyVelocity; // Sky'ın hareketi

			if(soundEnabled) {
				backgroundMusic.stop();
				playMusic.play();
			}
			playButton.setVisible(false);
			soundButton.setVisible(false);
			modeButton.setVisible(false);
			exitButton.setVisible(false);
			againButton.setVisible(false);
			homeButton.setVisible(false);

			if (backgroundX <= -Gdx.graphics.getWidth()) {
				backgroundX = 0;
			}

			if (mountainX <= -Gdx.graphics.getWidth()) {
				mountainX = 0;
			}

			if (skyX <= -Gdx.graphics.getWidth()) {
				skyX = 0;
			}


			if(enemyX[scoredEnemy]<Gdx.graphics.getWidth()/4){
				score++;
				if( scoredEnemy<numberOfEnemies-1) {
					scoredEnemy++;
				} else {
					scoredEnemy=0;
				}
			}

			if (score <= 20 && score % 5 == 0) {
				enemyVelocity = (float) (score/10 + (0.00440917107*Gdx.graphics.getWidth()));
			}
			if (score <= 50 &&score > 20 && score % 10== 0) {
				enemyVelocity = (float) (score/15 + (0.00529100529*Gdx.graphics.getWidth()));
			}
			if (score < 100 && score > 50 && score % 10== 0) {
				enemyVelocity = (float) (score/60 + Gdx.graphics.getWidth()*0.00676072898);
			}


			for(int i = 0; i<numberOfEnemies; i++) {

				if(enemyX[i] < -0.04629629629*Gdx.graphics.getWidth()){
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					generateEnemyOffSet(i);

				} else {
					enemyX[i] -= enemyVelocity;
				}


				batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffSet[i],
						Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);
				batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffSet1[i],
						Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);
				batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight()/2 + enemyOffSet2[i],
						Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36 , Gdx.graphics.getHeight()/2 + enemyOffSet[i] +
								Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 50);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36 , Gdx.graphics.getHeight()/2 + enemyOffSet1[i] +
								Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 50);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36 , Gdx.graphics.getHeight()/2 + enemyOffSet2[i] +
						Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 50);

			}


			if(newModEnabled && Gdx.input.isTouched()){
				velocity -= 0.00092592592*Gdx.graphics.getHeight();
				if (velocity < - 0.01666666666*Gdx.graphics.getHeight()){
					velocity = (float) (-0.01666666666*Gdx.graphics.getHeight());
				}
			}

			if (!newModEnabled &&Gdx.input.justTouched()){

				velocity -= 0.00925925925*Gdx.graphics.getHeight();
				if (velocity < - 0.01666666666*Gdx.graphics.getHeight()){
					velocity = (float) (-0.01666666666*Gdx.graphics.getHeight());
				}
			}


			if(birdY > 0 && birdY < Gdx.graphics.getHeight()){
				velocity = velocity + gravity;
				birdY -= velocity;
			} else {
				if(soundEnabled){
					deadSound.play();
				}
				gameState = 2;
			}



		} else if (gameState == 0) {

			if(soundEnabled) {
				backgroundMusic.play();
			}
			batch.draw(survivorBird, (float) (Gdx.graphics.getWidth()*0.27204585537), (float) (Gdx.graphics.getHeight()*0.68518518518));
			bestScoreFont.draw(batch,"Best Score: "+String.valueOf(bestScore), (float) (Gdx.graphics.getWidth()*0.3897707231), (float) (Gdx.graphics.getHeight()*0.68518518518));

			playButton.setVisible(true);
			soundButton.setVisible(true);
			modeButton.setVisible(true);
			exitButton.setVisible(true);

			againButton.setVisible(false);
			homeButton.setVisible(false);

			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();


		} else if (gameState == 2) {
			if(score>=bestScore){
				bestScore = score;
				scoreSettings.setBestScore(bestScore);
				batch.draw(newBestScore, (float) (Gdx.graphics.getWidth() * 0.24514991181), (float) (Gdx.graphics.getHeight() * 0.68518518518)
						, (float) (0.5291005291 * Gdx.graphics.getWidth()), (float) (0.18518518518*Gdx.graphics.getHeight()));

			} else {
				// Game Over ekranı
				batch.draw(gameOver, (float) (Gdx.graphics.getWidth()*0.37742504409), (float) (Gdx.graphics.getHeight()*0.59259259259)
						, (float) (0.22045855379*Gdx.graphics.getWidth()), (float) (0.46296296296*Gdx.graphics.getHeight()));
			}
			yourScoreFont.draw(batch,"Your Score: "+String.valueOf(score), (float) (Gdx.graphics.getWidth()*0.37742504409)
					, (float) (Gdx.graphics.getHeight()*0.63888888888));


            if(soundEnabled) {
              	playMusic.stop();
            }

			againButton.setVisible(true);
			homeButton.setVisible(true);

			playButton.setVisible(false);
			soundButton.setVisible(false);
			modeButton.setVisible(false);
			exitButton.setVisible(false);
			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();

			birdY = Gdx.graphics.getHeight() / 2;

				for (int i = 0; i < numberOfEnemies; i++) {
					enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

					generateEnemyOffSet(i);

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}
				velocity = 0;
				scoredEnemy = 0;


		}



		batch.end();
		birdCircle.set(birdX + Gdx.graphics.getWidth()/36,birdY +Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/40);


		for (int i = 0; i< numberOfEnemies; i++){

			if(Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) ||
			Intersector.overlaps(birdCircle,enemyCircles3[i])){
				if(soundEnabled){
				deadSound.play();
				}
				gameState = 2;
			}

		}



	}


	@Override
	public void dispose () {

	}
	private void buttons(){
		TextureRegionDrawable playButtonDrawable;
		TextureRegionDrawable exitButtonDrawable;
		TextureRegionDrawable againButtonDrawable;
		TextureRegionDrawable soundButtonDrawable;
		TextureRegionDrawable scoresButtonDrawable;
		TextureRegionDrawable homeButtonDrawable;
		TextureRegionDrawable modeButtonDrawable;

		TextureRegion playButtonRegion;
		TextureRegion exitButtonRegion;
		TextureRegion againButtonRegion;
		TextureRegion soundButtonRegion;
		TextureRegion scoresButtonRegion;
		TextureRegion homeButtonRegion;
		TextureRegion modeButtonRegion;
		

		buttonAtlas = new TextureAtlas("buttons/buttons.atlas");

		playButtonRegion = buttonAtlas.findRegion("play");
		playButtonDrawable = new TextureRegionDrawable(playButtonRegion);
		playButton = new ImageButton(playButtonDrawable);
		playButton.setPosition((float) Gdx.graphics.getWidth() * 0.37742504409f, (float) Gdx.graphics.getHeight() * 0.36111111111f);
		playButton.setSize((float) Gdx.graphics.getWidth() * 0.06607929515f, (float) Gdx.graphics.getHeight() * 0.13888888888f);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				score = 0;
				gameState = 1;
			}
		});
		stage.addActor(playButton);

		exitButtonRegion = buttonAtlas.findRegion("exit");
		exitButtonDrawable = new TextureRegionDrawable(exitButtonRegion);
		exitButton = new ImageButton(exitButtonDrawable);
		exitButton.setPosition((float) Gdx.graphics.getWidth() * 0.91139240506f, (float) Gdx.graphics.getHeight() * 0.02777777777f);
		exitButton.setSize((float) Gdx.graphics.getWidth() * 0.06607929515f, (float) Gdx.graphics.getHeight() * 0.13888888888f);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Exit butonuna tıklanıldığında yapılacak işlemler
				Gdx.app.exit();
			}
		});
		stage.addActor(exitButton);

		againButtonRegion = buttonAtlas.findRegion("again");
		againButtonDrawable = new TextureRegionDrawable(againButtonRegion);
		againButton = new ImageButton(againButtonDrawable);
		againButton.setPosition((float) Gdx.graphics.getWidth() * 0.37742504409f, (float) Gdx.graphics.getHeight() * 0.36111111111f);
		againButton.setSize((float) Gdx.graphics.getWidth() * 0.06607929515f, (float) Gdx.graphics.getHeight() * 0.13888888888f);
		againButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Again butonuna tıklanıldığında yapılacak işlemler
				score = 0;
				gameState = 1;
			}
		});
		stage.addActor(againButton);

		soundButtonRegion = buttonAtlas.findRegion("sound");
		soundButtonDrawable = new TextureRegionDrawable(soundButtonRegion);
		soundButton = new ImageButton(soundButtonDrawable);
		soundButton.setPosition((float) Gdx.graphics.getWidth() * 0.91150442477f, (float) Gdx.graphics.getHeight() * 0.81481481481f);
		soundButton.setSize((float) Gdx.graphics.getWidth() * 0.06607929515f, (float) Gdx.graphics.getHeight() * 0.13888888888f);
		SoundSettings soundSettings = new SoundSettings();

		soundEnabled = soundSettings.isSoundEnabled();

		if(!soundEnabled) {
			soundButtonRegion.setRegion(buttonAtlas.findRegion("soundLocked"));
		}
		if(soundEnabled) {
			soundButtonRegion.setRegion(buttonAtlas.findRegion("soundClicked"));
		}
		soundButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (soundEnabled) {
					// Sesler açıksa, sesleri kapat
					soundButtonRegion.setRegion(buttonAtlas.findRegion("soundLocked"));

					soundSettings.setSoundEnabled(false);
					soundEnabled = false;
					backgroundMusic.stop();
				} else {
					// Sesler kapalıysa, sesleri aç
					soundButtonRegion.setRegion(buttonAtlas.findRegion("soundClicked"));

					soundSettings.setSoundEnabled(true);
					soundEnabled = true;
					//System.out.println("Sound is enabled.");

				}

			}
		});
		stage.addActor(soundButton);


		modeButtonRegion = buttonAtlas.findRegion("hold");
		modeButtonDrawable = new TextureRegionDrawable(modeButtonRegion);
		modeButton = new ImageButton(modeButtonDrawable);
		modeButton.setPosition((float) Gdx.graphics.getWidth() * 0.48460176991f, (float) Gdx.graphics.getHeight() * 0.29166666666f);
		modeButton.setSize((float) Gdx.graphics.getWidth() * 0.1321585903f, (float) Gdx.graphics.getHeight() * 0.27777777777f);


		GameModSettings gameModSettings = new GameModSettings();

		newModEnabled = gameModSettings.isNewModeEnabled();

		if(!newModEnabled) {
			modeButtonRegion.setRegion(buttonAtlas.findRegion("touch"));
		}
		if(newModEnabled) {
			modeButtonRegion.setRegion(buttonAtlas.findRegion("holdClicked"));
		}

		modeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Scores butonuna tıklanıldığında yapılacak işlemler
				if (newModEnabled) {
					// Yeni mod açıksa kapat
					modeButtonRegion.setRegion(buttonAtlas.findRegion("touch"));

					gameModSettings.setNewModeEnabled(false);
					newModEnabled = false;
				} else {
					modeButtonRegion.setRegion(buttonAtlas.findRegion("holdClicked"));

					gameModSettings.setNewModeEnabled(true);
					newModEnabled = true;

				}

			}
		});
		stage.addActor(modeButton);

		homeButtonRegion = buttonAtlas.findRegion("home");
		homeButtonDrawable = new TextureRegionDrawable(homeButtonRegion);
		homeButton = new ImageButton(homeButtonDrawable);
		homeButton.setPosition(Gdx.graphics.getWidth()*0.54409171075f, Gdx.graphics.getHeight()*0.36111111111f);
		homeButton.setSize(150, 150);
		homeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Home butonuna tıklanıldığında yapılacak işlemler
				gameState=0;
			}
		});
		stage.addActor(homeButton);

	}

	private void initialize() {
		background = new Texture[2];
		background[0] = new Texture("background.png");
		background[1] = new Texture("background.png");
		mountains = new Texture[2];
		mountains[0] = new Texture("daglar.png");
		mountains[1] = new Texture("daglar.png");

		gameOver = new Texture("gameover.png");
		newBestScore = new Texture("newBestScore.png");
		survivorBird = new Texture("survivorbird.png");
		sky = new Texture[2];
		sky[0] = new Texture("gokyuzu.png");
		sky[1] = new Texture("gokyuzu.png");

		bird = new Texture("bird.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");
		random = new Random();

		distance = Gdx.graphics.getWidth() / 2;
		backgroundVelocity = (float) (Gdx.graphics.getWidth()*0.00264550265);
		mountainVelocity = backgroundVelocity/2;
		skyVelocity = mountainVelocity/3;

		enemyVelocity = (float) (0.00440917107*Gdx.graphics.getWidth());
		System.out.println(backgroundVelocity+" "+mountainVelocity+" "+skyVelocity+" +" + enemyVelocity);

		backgroundX = 0;
		mountainX = 0;
		skyX = 0;

		gravity =(float) 0.00046296296*Gdx.graphics.getHeight();

		birdX = Gdx.graphics.getWidth() / 4;
		birdY = Gdx.graphics.getHeight() / 2;

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.WHITE);
		scoreFont.getData().setScale(4);

		bestScoreFont = new BitmapFont();
		bestScoreFont.setColor(Color.BLACK);
		bestScoreFont.getData().setScale(5);

		yourScoreFont = new BitmapFont();
		yourScoreFont.setColor((Color.BLACK));
		yourScoreFont.getData().setScale(5);

		survivorBirdFont = new BitmapFont();
		survivorBirdFont.setColor(Color.WHITE);
		survivorBirdFont.getData().setScale(6);

		// Enemy initialize etme
		for (int i = 0; i < numberOfEnemies; i++) {
			enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

			generateEnemyOffSet(i);

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}

	}

	private void generateEnemyOffSet(int i){

		enemyOffSet[i] = (float) ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815 ));
		enemyOffSet1[i] =  (float) ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815 ));
		while (Math.abs(enemyOffSet1[i] - enemyOffSet[i]) < Gdx.graphics.getWidth()*0.09259259259) {
			enemyOffSet1[i] =  (float) ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815 ));
		}

		enemyOffSet2[i] =  (float) ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815 ));
		while (Math.abs(enemyOffSet2[i] - enemyOffSet[i]) < Gdx.graphics.getWidth()*0.09259259259 || Math.abs(enemyOffSet2[i] - enemyOffSet1[i]) < Gdx.graphics.getHeight()/10.2) {
			enemyOffSet2[i] =(float) ((random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() * 0.815 ));
		}

	}


}
