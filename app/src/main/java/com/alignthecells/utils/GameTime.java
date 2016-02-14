package com.alignthecells.utils;

import android.os.SystemClock;

/**
 * Created by Sergey on 9/2/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class GameTime {

    public static long getTime() {
        return SystemClock.elapsedRealtime();
    }

}
