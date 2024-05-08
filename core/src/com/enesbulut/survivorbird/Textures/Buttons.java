package com.enesbulut.survivorbird.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.enesbulut.survivorbird.Generators.GenerateButton;
import com.enesbulut.survivorbird.Info;
import com.enesbulut.survivorbird.ScreenStatus;
import com.enesbulut.survivorbird.Settings.GameModSettings;
import com.enesbulut.survivorbird.Settings.SoundSettings;

import java.util.ArrayList;

public class Buttons {
    private GenerateButton soundButton;
    private GenerateButton playButton;
    private GenerateButton exitButton;
    private GenerateButton modeButton;
    private GenerateButton homeButton;
    private GenerateButton againButton;
    private boolean soundEnabled = true;
    private boolean newModEnabled = true;

    public Buttons(Stage stage) {
        buttons(stage);
    }


    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public boolean isNewModEnabled() {
        return newModEnabled;
    }

    public void setVisibilities(){
        switch (Info.getScreenStatus()){
            case MAIN:
                playButton.getGeneratedButton().setVisible(true);
                soundButton.getGeneratedButton().setVisible(true);
                modeButton.getGeneratedButton().setVisible(true);
                exitButton.getGeneratedButton().setVisible(true);
                againButton.getGeneratedButton().setVisible(false);
                homeButton.getGeneratedButton().setVisible(false);
                break;
            case PLAY:
                playButton.getGeneratedButton().setVisible(false);
                soundButton.getGeneratedButton().setVisible(false);
                modeButton.getGeneratedButton().setVisible(false);
                exitButton.getGeneratedButton().setVisible(false);
                againButton.getGeneratedButton().setVisible(false);
                homeButton.getGeneratedButton().setVisible(false);
                break;
            case OVER:
                againButton.getGeneratedButton().setVisible(true);
                homeButton.getGeneratedButton().setVisible(true);
                playButton.getGeneratedButton().setVisible(false);
                soundButton.getGeneratedButton().setVisible(true);
                modeButton.getGeneratedButton().setVisible(false);
                exitButton.getGeneratedButton().setVisible(true);
                break;

        }
    }

    private void buttons(Stage stage){

        ArrayList<Float> playButtonPositionAndSize = new ArrayList<>();
        playButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.37742504409f);
        playButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.36111111111f);
        playButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.06607929515f);
        playButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.13888888888f);

        playButton = new GenerateButton("play",playButtonPositionAndSize);
        playButton.getGeneratedButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Info.setScreenStatus(ScreenStatus.PLAY);
                Info.setScore(0);

            }
        });
        stage.addActor(playButton.getGeneratedButton());

        ArrayList<Float> exitButtonPositionAndSize = new ArrayList<>();
        exitButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.91139240506f);
        exitButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.02777777777f);
        exitButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.06607929515f);
        exitButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.13888888888f);

        exitButton = new GenerateButton("exit",exitButtonPositionAndSize);
        exitButton.getGeneratedButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Exit butonuna tıklanıldığında yapılacak işlemler
                Gdx.app.exit();
            }
        });
        stage.addActor(exitButton.getGeneratedButton());

        ArrayList<Float> againButtonPositionAndSize = new ArrayList<>();
        againButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.37742504409f);
        againButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.36111111111f);
        againButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.06607929515f);
        againButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.13888888888f);
        againButton = new GenerateButton("again",againButtonPositionAndSize);
        againButton.getGeneratedButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Again butonuna tıklanıldığında yapılacak işlemler
                Info.setScreenStatus(ScreenStatus.PLAY);
                Info.setScore(0);
            }
        });
        stage.addActor(againButton.getGeneratedButton());

        ArrayList<Float> soundButtonPositionAndSize = new ArrayList<>();
        soundButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.91150442477f);
        soundButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.81481481481f);
        soundButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.06607929515f);
        soundButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.13888888888f);

        soundButton = new GenerateButton("sound",soundButtonPositionAndSize);

        SoundSettings soundSettings = new SoundSettings();
        soundEnabled = soundSettings.isSoundEnabled();

        if(!soundEnabled) {
            soundButton.setFile("soundLocked");
        }
        if(soundEnabled) {
            soundButton.setFile("soundClicked");
        }
        soundButton.getGeneratedButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEnabled) {
                    // Sesler açıksa, sesleri kapat
                    soundButton.setFile("soundLocked");

                    soundSettings.setSoundEnabled(false);
                    soundEnabled = false;
                } else {
                    // Sesler kapalıysa, sesleri aç
                    soundButton.setFile("soundClicked");

                    soundSettings.setSoundEnabled(true);
                    soundEnabled = true;
                }
            }
        });
        stage.addActor(soundButton.getGeneratedButton());


        ArrayList<Float> modeButtonPositionAndSize = new ArrayList<>();
        modeButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.48460176991f);
        modeButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.29166666666f);
        modeButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.1321585903f);
        modeButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.27777777777f);

        modeButton = new GenerateButton("hold", modeButtonPositionAndSize);

        GameModSettings gameModSettings = new GameModSettings();
        newModEnabled = gameModSettings.isNewModeEnabled();

        if(!newModEnabled) {
            modeButton.setFile("touch");
        }
        if(newModEnabled) {
            modeButton.setFile("holdClicked");
        }

        modeButton.getGeneratedButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Scores butonuna tıklanıldığında yapılacak işlemler
                if (newModEnabled) {
                    // Yeni mod açıksa kapat
                    modeButton.setFile("touch");
                    gameModSettings.setNewModeEnabled(false);
                    newModEnabled = false;
                } else {
                    modeButton.setFile("holdClicked");
                    gameModSettings.setNewModeEnabled(true);
                    newModEnabled = true;

                }

            }
        });
        stage.addActor(modeButton.getGeneratedButton());

        ArrayList<Float> homeButtonPositionAndSize = new ArrayList<>();
        homeButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.54409171075f);
        homeButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.36111111111f);
        homeButtonPositionAndSize.add(Gdx.graphics.getWidth() * 0.06607929515f);
        homeButtonPositionAndSize.add(Gdx.graphics.getHeight() * 0.13888888888f);

        homeButton = new GenerateButton("home",homeButtonPositionAndSize);
        homeButton.getGeneratedButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Home butonuna tıklanıldığında yapılacak işlemler
                Info.setScreenStatus(ScreenStatus.MAIN);
            }
        });
        stage.addActor(homeButton.getGeneratedButton());
    }

}
