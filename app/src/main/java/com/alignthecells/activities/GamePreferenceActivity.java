package com.alignthecells.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.alignthecells.R;
import com.alignthecells.utils.GamePreferences;

/**
 * Created by Sergey on 8/22/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class GamePreferenceActivity extends PreferenceActivity {

    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new
            SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                                      String key) {
                    SquareBoardGameActivity activity = SquareBoardGameActivity.instance;
                    if ((activity != null)&&(!(key.equals("sound_enabled")))) {
                        activity.recreate();
                        GamePreferences.updateValues(sharedPreferences);
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceFragment mp = new MyPreferenceFragment();
        getFragmentManager().beginTransaction().replace(android.R.id.content, mp).commit();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sp.registerOnSharedPreferenceChangeListener(spChanged);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

}
