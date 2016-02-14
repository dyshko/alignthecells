package com.alignthecells.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alignthecells.logic.Cell;
import com.alignthecells.logic.SquareBoard;

/**
 * Created by Sergey on 8/27/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class GamePreferences {

    public static final int NORMAL_ANIMATION_DURATION = 200;
    public static SquareBoard.GAME_MODE mode;

    //  public static boolean animationType2;
    public static boolean hintVisible;
    public static int boardSize;
    public static int gameModeInt;
    public static Cell.IMAGE_TYPE imageType;
    public static int animationDuration;

    public static boolean animationEnabled;
    public static int boardSizePosition;
    public static boolean soundEnabled;
    public static boolean firstRun;

    public static int highscore;

    public static void updateValues(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        updateValues(SP);
    }

    public static void updateValues(SharedPreferences SP) {
        //
        hintVisible = SP.getBoolean("hint_visible",true);

        firstRun = SP.getBoolean("firstrun", true);

        soundEnabled = SP.getBoolean("sound_enabled", true);
        SoundManager.setSound(soundEnabled);

        //  animationType2 = SP.getBoolean("animation_type", true);

        boardSizePosition = Integer.valueOf(SP.getString("board_size", "4")) - 3;
        boardSize = boardSizePosition + 3;

        animationEnabled = SP.getBoolean("animation_enabled", true);

        if (animationEnabled) animationDuration = NORMAL_ANIMATION_DURATION;
        else animationDuration = 1;

        int imgType = Integer.valueOf(SP.getString("cell_image", "1"));
        switch (imgType) {
            case 0:
                imageType = Cell.IMAGE_TYPE.SQUARE;
                break;
            case 1:
                imageType = Cell.IMAGE_TYPE.SQUARE_ROUNDED;
                break;
            case 2:
                imageType = Cell.IMAGE_TYPE.CIRCLE;
                break;
            default:
        }

        gameModeInt = Integer.valueOf(SP.getString("game_type", "0"));

        switch (gameModeInt) {
            case 0:
                mode = SquareBoard.GAME_MODE.TOR;
                break;
            case 1:
                mode = SquareBoard.GAME_MODE.BOTTLE;
                break;
            case 2:
                mode = SquareBoard.GAME_MODE.SPHERE;
                break;
            case 3:
                mode = SquareBoard.GAME_MODE.REAL_PROJECTIVE_PLANE;
                break;
            case 4:
                mode = SquareBoard.GAME_MODE.BOTTLE_NR;
                break;
            case 5:
                mode = SquareBoard.GAME_MODE.SPHERE_NR;
                break;
            case 6:
                mode = SquareBoard.GAME_MODE.DOUBLE_DIRECT;
                break;
            case 7:
                mode = SquareBoard.GAME_MODE.DOUBLE_REVERSED;
                break;
            case 8:
                mode = SquareBoard.GAME_MODE.TRIPLE_DIRECT;
                break;
            case 9:
                mode = SquareBoard.GAME_MODE.TRIPLE_REVERSED;
                break;
            default:
                mode = SquareBoard.GAME_MODE.TOR;
        }
    }

    public static void setFirstRun(Context context, boolean value) {
        firstRun = value;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SP.edit().putBoolean("firstrun", value).apply();
    }

    public static void setHintVisibility(Context context, boolean value) {
        hintVisible = value;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SP.edit().putBoolean("hint_visible", value).apply();
    }

/*    public static int getHighScore(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.valueOf(SP.getString("highscore_"+gameModeInt+"_"+boardSize, "0"));
    }

    public static void setHighScore(Context context, int value) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        highscore = value;
        SP.edit().putString("highscore_" + gameModeInt + "_" + boardSize, String.valueOf(value)).apply();
    }*/

    public static int getBestMoves(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.valueOf(SP.getString("bestmoves_" + gameModeInt + "_" + boardSize, "0"));
    }

    public static void setBestMoves(Context context, int value) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        highscore = value;
        SP.edit().putString("bestmoves_" + gameModeInt + "_" + boardSize, String.valueOf(value)).apply();
    }

    public static int getBestTime(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        return Integer.valueOf(SP.getString("besttime_" + gameModeInt + "_" + boardSize, "0"));
    }

    public static void setBestTime(Context context, int value) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        highscore = value;
        SP.edit().putString("besttime_" + gameModeInt + "_" + boardSize, String.valueOf(value)).apply();
    }
}
