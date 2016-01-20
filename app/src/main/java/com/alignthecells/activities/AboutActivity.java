package com.alignthecells.activities;

import android.os.Bundle;

import com.alignthecells.R;

/**
 * Created by Sergey on 8/19/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class AboutActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about_layout);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
