package com.alignthecells.utils;

import android.view.MotionEvent;
import android.view.View;

import com.alignthecells.logic.Shift;
import com.alignthecells.views.BoardView;

import static java.lang.Math.abs;

/**
 * Created by Sergey on 8/22/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class BoardViewTouchListener implements View.OnTouchListener {

    private int firstX = -1;
    private int firstY = -1;

    private BoardView boardView;

    public BoardViewTouchListener(BoardView boardView) {
        super();
        this.boardView = boardView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //Log.d("SQUARE", "Touch detected...");
                firstX = (int) event.getX();  //Remember the first coordinate of the touch
                firstY = (int) event.getY();
            }
            break;
            case MotionEvent.ACTION_UP: {
                int lastX = (int) event.getX();
                int lastY = (int) event.getY();
                //Log.d("SQUARE","Touch was "+firstX+ " "+ firstY + " to " + lastX +" "+ lastY);
                respondToTouchUp(lastX, lastY);
            }
            break;
            default:
        }
        return true;
    }

    public void respondToTouchUp(int lastX, int lastY) {
        if (!((firstX == lastX) && (firstY == lastY))) {
            int row = boardView.getRowUnderPointer(firstY);
            int col = boardView.getColumnUnderPointer(firstX);
            int x = lastX - firstX;
            int y = lastY - firstY;
            //
            {
                Shift.DIRECTION dir;
                if (abs(x) >= abs(y)) {
                    if (x >= 0) dir = Shift.DIRECTION.FORWARD;
                    else dir = Shift.DIRECTION.BACKWARD;
                    Shift initShift = new Shift(Shift.TYPE.ROW, row, dir);
                    boardView.shift(initShift);
                } else {
                    if (y >= 0) dir = Shift.DIRECTION.FORWARD;
                    else dir = Shift.DIRECTION.BACKWARD;
                    Shift initShift = new Shift(Shift.TYPE.COLUMN, col, dir);
                    boardView.shift(initShift);
                }
                SoundManager.playClickSound();
            }
        }
    }
}
