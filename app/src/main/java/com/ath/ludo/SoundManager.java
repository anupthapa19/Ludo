package com.ath.ludo;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private MediaPlayer backgroundMusic;
    private boolean isSoundEnabled = true;

    public SoundManager(Context context) {
        // Load your background music
        backgroundMusic = MediaPlayer.create(context, R.raw.game_sound);
        backgroundMusic.setLooping(true);
    }

    public void playBackgroundMusic() {
        if (isSoundEnabled && !backgroundMusic.isPlaying()) {
            backgroundMusic.start();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusic.isPlaying()) {
            backgroundMusic.stop();
            backgroundMusic.prepareAsync();
        }
    }

    public void setSoundEnabled(boolean isEnabled) {
        isSoundEnabled = isEnabled;
        if (!isEnabled) {
            stopBackgroundMusic();
        } else {
            playBackgroundMusic();
        }
    }
}

