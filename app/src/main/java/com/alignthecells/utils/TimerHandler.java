package com.alignthecells.utils;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;

import com.alignthecells.R;

/**
 * Created by Sergey on 8/28/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class TimerHandler {

    private static long deltaTime;

//    private TextView mTimeLabel;
    private Handler mHandler = new Handler();
    private long lastTime;

    private boolean isRunning = false;

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long t = SystemClock.elapsedRealtime();
            deltaTime += t - lastTime;
            lastTime = t;
            //        mTimeLabel.setText();
            mHandler.postDelayed(this, 1000);
        }
    };

 /*   public TimerHandler(TextView v) {
        mTimeLabel = v;
    }*/

    public void start() {
        deltaTime = 0;
        isRunning = true;
        lastTime = SystemClock.elapsedRealtime();
        mHandler.removeCallbacks(mUpdateTimeTask);
        mHandler.postDelayed(mUpdateTimeTask, 0);
    }

    public void stop() {
        mHandler.removeCallbacks(mUpdateTimeTask);
        isRunning = false;
    }

    public void reset() {
        stop();
        //       mTimeLabel.setText(R.string.starting_timer_value);
    }

    public void pause() {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    public void resume() {
        lastTime = SystemClock.elapsedRealtime();
        if (isRunning) {
            mHandler.removeCallbacks(mUpdateTimeTask);
            mHandler.postDelayed(mUpdateTimeTask, 0);
        }
    }

    public String getText(Context context) {
        int seconds = (int) deltaTime / 1000;
        return Integer.toString(seconds) + context.getString(R.string.sec);
    }
}
