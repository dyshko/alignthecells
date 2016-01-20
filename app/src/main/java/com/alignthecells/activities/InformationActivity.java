package com.alignthecells.activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.alignthecells.R;

/**
 * Created by Sergey on 8/19/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class InformationActivity extends MainActivity {

    public LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_layout);
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
