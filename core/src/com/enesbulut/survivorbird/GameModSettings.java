package com.enesbulut.survivorbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameModSettings {
    private static final String PREF_NAME = "GameModSettings";
    private static final String NEW_MODE_KEY = "NewMode";

    private Preferences preferences;

    public GameModSettings() {
        preferences = Gdx.app.getPreferences(PREF_NAME);
    }

    public boolean isNewModeEnabled() {
        return preferences.getBoolean(NEW_MODE_KEY, true);
    }

    public void setNewModeEnabled(boolean enabled) {
        preferences.putBoolean(NEW_MODE_KEY, enabled);
        preferences.flush(); // Değişiklikleri kaydet
    }
}
