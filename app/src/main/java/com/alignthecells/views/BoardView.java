package com.alignthecells.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.alignthecells.activities.SquareBoardGameActivity;
import com.alignthecells.logic.BoardDrawing;
import com.alignthecells.logic.Shift;
import com.alignthecells.logic.SquareBoard;
import com.alignthecells.utils.BoardViewParams;
import com.alignthecells.utils.BoardViewTouchListener;
import com.alignthecells.utils.GamePreferences;
import com.alignthecells.utils.GameTime;
import com.alignthecells.utils.SoundManager;

import java.util.Random;


/**
 * Created by Sergey on 8/13/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class BoardView extends RelativeLayout {

    public static final int delayBeforeDialog = 0;
    public static final int waitAfterWinDuration = 500;
    public int shifts = 0;
    Rect rectCanvasBounds;
    private BoardViewParams params;
    private BoardDrawing boardDrawing;
    private SquareBoardGameActivity activity;
    private SquareBoard squareBoard;
    private View.OnTouchListener onTouchListener = new BoardViewTouchListener(this);

//    public int score = 0;

    //public int dejaAligned = 0;

    public BoardView(Context context, BoardViewParams params) {
        super(context);
        rectCanvasBounds = new Rect();

        this.activity = SquareBoardGameActivity.instance;
        //this.setBackground(ContextCompat.getDrawable(context, R.drawable.board));
        this.params = params;

        squareBoard = new SquareBoard(params.size, GamePreferences.mode);

        boardDrawing = new BoardDrawing(getContext(), params);

        setOnTouchListener(onTouchListener);

        this.setWillNotDraw(false);
    }

    // ---->X
    // |  0'st row  1 st column
    // |  1'st row
    // V Y
    public int getRowUnderPointer(int Y) {
        return (Y) / (params.cellSideLength + params.cellSpace);
    }

    public int getColumnUnderPointer(int X) {
        return (X) / (params.cellSideLength + params.cellSpace);
    }

    public boolean isSolved() {
        return (getNumberOfAligned() == params.size);
    /*    int[][] a = new int[params.size][params.size];
        for (int i = 0; i < params.size; i++)
            for (int j = 0; j < params.size; j++)
                a[i][j] = boardIndexToColor(squareBoard.getValue(i, j));
        //if rows are aligned
        boolean tempr = true;
        for (int i = 0; i < params.size; i++) {
            for (int j = 0; j < params.size; j++) {
                if (a[i][j] != a[i][0]) {
                    tempr = false;
                    break;
                }
            }
        }
        boolean tempc = true;
        for (int j = 0; j < params.size; j++) {
            for (int i = 0; i < params.size; i++) {
                if (a[i][j] != a[0][j]) {
                    tempc = false;
                    break;
                }
            }
        }
        return tempr || tempc;*/
    }

    private int getNumberOfAligned() {
        int res = 2 * params.size;

        int[][] a = new int[params.size][params.size];
        for (int i = 0; i < params.size; i++)
            for (int j = 0; j < params.size; j++)
                a[i][j] = boardIndexToColor(squareBoard.getValue(i, j));

        //if rows are aligned
        for (int i = 0; i < params.size; i++) {
            for (int j = 0; j < params.size; j++) {
                if (a[i][j] != a[i][0]) {
                    res -= 1;
                    break;
                }
            }
        }
        //if columns are aligned
        for (int j = 0; j < params.size; j++) {
            for (int i = 0; i < params.size; i++) {
                if (a[i][j] != a[0][j]) {
                    res -= 1;
                    break;
                }
            }
        }
        return res;
    }

    public void shift(Shift initShift) {
        int waitTime = GamePreferences.NORMAL_ANIMATION_DURATION;
        shifts += 1;

        //    activity.movesTextView.setText(String.valueOf(shifts));

        squareBoard.shift(initShift);
        boardDrawing.setupShift(squareBoard, GameTime.getTime(), waitTime);
        //      updateScore(SCORE_TYPE.SHIFT);
        int r = getNumberOfAligned();
   /*     if (r>dejaAligned) {
            updateScore(SCORE_TYPE.ALIGN);
            dejaAligned = r;
        }*/
        if (r == params.size) {
            //    updateScore(SCORE_TYPE.SOLVED);
            waitTime += waitAfterWinDuration + delayBeforeDialog + 1;
            activity.stopTimer();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    SoundManager.playSolvedSound();
                    activity.showGameDialog();
                    //activity.showScoreboard();
                }
            }, waitTime);
        }
        disableTouchForTime(waitTime);
    }

    public void solvedAnimation() {
        Random r = new Random();
        boardDrawing.setRandomPosMovementOutside(r, false, GameTime.getTime(), GamePreferences.NORMAL_ANIMATION_DURATION);
        disableTouchForTime(GamePreferences.NORMAL_ANIMATION_DURATION);
    }

    public void shiftBoardRandom() {
        do {
            squareBoard.shiftRandom();
        } while (isSolved());

        shifts = 0;
        //updateScore(SCORE_TYPE.START);

        boardDrawing.setupShift(squareBoard, GameTime.getTime(), GamePreferences.NORMAL_ANIMATION_DURATION);
        disableTouchForTime(GamePreferences.NORMAL_ANIMATION_DURATION);
    }

    public void firstShiftRandom() {
        do {
            squareBoard.shiftRandom();
        } while (isSolved());

        shifts = 0;
        //updateScore(SCORE_TYPE.START);

        Random r = new Random();
        boardDrawing.setRandomPosMovementOutside(r, true, 0, 0);
        boardDrawing.setupShift(squareBoard, GameTime.getTime(), GamePreferences.NORMAL_ANIMATION_DURATION);
        disableTouchForTime(GamePreferences.NORMAL_ANIMATION_DURATION);
    }

    public int boardIndexToColor(int val) {
        return val % params.size;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boardDrawing.draw(canvas, GameTime.getTime());
        invalidate();
    }

    public void disableTouchForTime(int duration) {
        if (activity.buttonTouchEnabled()) {
            disableTouch();
            activity.disableButtonTouch();
            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    activity.enableButtonTouch();
                    enableTouch();
                }
            }, duration);
        }
    }

    public void disableTouch() {
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    public void enableTouch() {
        this.setOnTouchListener(onTouchListener);
    }

 //   public enum SCORE_TYPE {SHIFT, ALIGN, SOLVED, CLEAR, START}
}