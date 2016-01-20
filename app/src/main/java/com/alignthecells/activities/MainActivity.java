package com.alignthecells.activities;

import android.app.Activity;
import android.os.Bundle;

import com.alignthecells.utils.GamePreferences;
import com.alignthecells.utils.SoundManager;

/**
 * Created by Sergey on 8/19/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GamePreferences.updateValues(getApplicationContext());
        SoundManager.initialize(getApplicationContext(), GamePreferences.soundEnabled);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
