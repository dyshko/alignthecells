package com.alignthecells.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.alignthecells.R;

/**
 * Created by Sergey on 8/29/2015.
 * Copyright SERGEY DYSHKO 2015
 */
public class MyTextView extends TextView {

    public MyTextView(Context context) {
        super(context);
        setProperties();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setProperties();
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProperties();
    }

    public void setProperties() {
        setTextColor(getResources().getColor(R.color.text_color));
    }


}
