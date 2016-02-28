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

    public static boolean hintVisible;
    public static int boardSize;
    public static int gameModeInt;
    public static Cell.IMAGE_TYPE imageType;

    public static int boardSizePosition;
    public static boolean soundEnabled;
    public static boolean firstRun;

    public static void updateValues(Context context) {
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        updateValues(SP);
    }

    public static void updateValues(SharedPreferences SP) {
        hintVisible = SP.getBoolean("hint_visible",true);

        firstRun = SP.getBoolean("firstrun", true);

        boardSizePosition = Integer.valueOf(SP.getString("board_size", "4")) - 3;
        boardSize = boardSizePosition + 3;

        soundEnabled = SP.getBoolean("sound_endabled",true);

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

    public static void setSoundEnabled(Context context, boolean value){
        soundEnabled = value;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SP.edit().putBoolean("sound_enabled", value).apply();
    }


    public static void setHintVisibility(Context context, boolean value) {
        hintVisible = value;
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(context);
        SP.edit().putBoolean("hint_visible", value).apply();
    }
}
