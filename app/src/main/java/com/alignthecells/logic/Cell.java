package com.alignthecells.logic;

/**
 * Created by Sergey on 9/1/2015.
 * Copyright SERGEY DYSHKO 2015
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;

import com.alignthecells.R;
import com.alignthecells.utils.GamePreferences;


/**
 * Created by Sergey on 8/10/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class Cell {

    int color;
    int value;
    Point pos;
    Point destination;
    Rect rect;
    Rect canonticalRect;
    //WAY FOR 1 MILISECOND
    float speedX = 0;
    float speedY = 0;
    long timeAtDestination = -1;
    private Context context;
    private int sideLength;
    private Bitmap bitmap;
    private Paint paint;

    public Cell(Context context, int color, int value, int sideLength) {
        this.color = color;
        this.value = value;
        this.paint = new Paint();
        this.context = context;
        this.pos = new Point();
        this.destination = new Point(pos);
        this.sideLength = sideLength;
        setColorAndShape();
        rect = new Rect();
        canonticalRect = new Rect(0, 0, sideLength, sideLength);
    }

    //delatTime in miliseconds
    public void setMovement(int destinationX, int destinationY, long currentTime, long deltaTime) {
        //just an instant move
        if (deltaTime <= 0) deltaTime = 1;
        speedX = (destinationX - pos.x) / (float) deltaTime;
        speedY = (destinationY - pos.y) / (float) deltaTime;
        this.destination.set(destinationX, destinationY);
        this.timeAtDestination = currentTime + deltaTime;
    }

    public int getValue() {
        return value;
    }

    private void setColorAndShape() {

        int c;
        switch (color) {
            case 0:
                c = R.color.cell_color_0;
                break;
            case 1:
                c = R.color.cell_color_1;
                break;
            case 2:
                c = R.color.cell_color_2;
                break;
            case 3:
                c = R.color.cell_color_3;
                break;
            case 4:
                c = R.color.cell_color_4;
                break;
            case 5:
                c = R.color.cell_color_5;
                break;
            case 6:
                c = R.color.cell_color_6;
                break;
            case 7:
                c = R.color.cell_color_7;
                break;
            default:
                c = R.color.cell_color_0;
                break;
        }

        paint.setColor(context.getResources().getColor(c));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        ColorFilter filter = new PorterDuffColorFilter(context.getResources().getColor(c), PorterDuff.Mode.MULTIPLY);
        paint.setColorFilter(filter);

        switch (GamePreferences.imageType) {
            case SQUARE_ROUNDED:
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.rounded_square);
                break;
            case CIRCLE:
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.circle);
                break;
            default:
                bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.square);
                break;
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, sideLength, sideLength, true);
    }

    public void setPosition(int x, int y) {
        pos.x = x;
        pos.y = y;
    }

    public void setDestination(int x, int y) {
        destination.x = x;
        destination.y = y;
    }

    public void offset(long currentTime) {
        long deltaTime = timeAtDestination - currentTime;
        if (deltaTime <= 0) {
            if (!isStatic()) {
                pos.set(destination.x, destination.y);
                speedX = 0f;
                speedY = 0f;
                timeAtDestination = -1;
            }
        } else
            pos.set(destination.x - (int) (speedX * deltaTime),
                    destination.y - (int) (speedY * deltaTime));
    }

    protected void draw(Canvas canvas, long currentTime) {
        rect.set(pos.x, pos.y, pos.x + sideLength, pos.y + sideLength);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, canonticalRect, rect, paint);
        }
        offset(currentTime);
    }

    public boolean isStatic() {
        return (speedX == 0) && (speedY == 0);
    }

    public enum IMAGE_TYPE {SQUARE, SQUARE_ROUNDED, CIRCLE}


}

