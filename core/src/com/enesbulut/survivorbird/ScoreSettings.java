package com.enesbulut.survivorbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class ScoreSettings {
    private static final String PREF_NAME = "ScoreSettings";
    private static final String BEST_SCORE_KEY = "BestScore";

    private Preferences preferences;

    public ScoreSettings() {
        preferences = Gdx.app.getPreferences(PREF_NAME);
    }

    public int getBestScore() {
        return preferences.getInteger(BEST_SCORE_KEY, 0); // Varsayılan olarak 0 ayarla
    }

    public void setBestScore(int bestScore) {
        preferences.putInteger(BEST_SCORE_KEY, bestScore);
        preferences.flush(); // Değişiklikleri kaydet
    }
}
