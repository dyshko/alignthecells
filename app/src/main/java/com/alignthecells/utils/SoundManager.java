package com.alignthecells.utils;

import android.content.Context;
import android.media.MediaPlayer;

import com.alignthecells.R;

/**
 * Created by Sergey on 8/25/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class SoundManager {

    private static MediaPlayer clickSound = null;

    private static MediaPlayer solvedSound = null;

    private static SoundManager instance = null;

    private SoundManager(Context context) {
        clickSound = MediaPlayer.create(context, R.raw.click);
        solvedSound = MediaPlayer.create(context, R.raw.solved);
    }

    public static void initialize(Context context) {
        if (instance == null)
            instance = new SoundManager(context);
    }

    public static void playClickSound() {
        if (GamePreferences.soundEnabled && (instance != null)) {
            clickSound.start();
        }
    }

    public static void playSolvedSound() {
        if (GamePreferences.soundEnabled && (instance != null)) {
            solvedSound.start();
        }
    }
}
