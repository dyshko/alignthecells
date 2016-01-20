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

    private static boolean soundEnabled = true;

    private static SoundManager instance = null;

    private SoundManager(Context context, boolean soundEnabled) {
        clickSound = MediaPlayer.create(context, R.raw.click);
        solvedSound = MediaPlayer.create(context, R.raw.solved);
        SoundManager.soundEnabled = soundEnabled;
    }

    public static void initialize(Context context, boolean soundEnabled) {
        if (instance == null)
            instance = new SoundManager(context, soundEnabled);
    }

    public static void playClickSound() {
        if (soundEnabled && (instance != null)) {
            clickSound.start();
        }
    }

    public static void playSolvedSound() {
        if (soundEnabled && (instance != null)) {
            solvedSound.start();
        }
    }

    public static void setSound(boolean sound) {
        SoundManager.soundEnabled = sound;
    }
}
