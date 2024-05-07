package com.enesbulut.survivorbird.Settings;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SoundSettings {
    private static final String PREF_NAME = "SoundSettings";
    private static final String SOUND_ENABLED_KEY = "SoundEnabled";

    private Preferences preferences;

    public SoundSettings() {
        preferences = Gdx.app.getPreferences(PREF_NAME);
    }

    public boolean isSoundEnabled() {
        return preferences.getBoolean(SOUND_ENABLED_KEY, true); // Varsayılan olarak true ayarla
    }

    public void setSoundEnabled(boolean enabled) {
        preferences.putBoolean(SOUND_ENABLED_KEY, enabled);
        preferences.flush(); // Değişiklikleri kaydet
    }
}
