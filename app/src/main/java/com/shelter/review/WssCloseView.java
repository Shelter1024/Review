package com.shelter.review;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author: Shelter
 * Create time: 2022/2/11, 14:08.
 */
public class WssCloseView extends androidx.appcompat.widget.AppCompatImageView {
    private int reduceArea = -1;

    public WssCloseView(Context context) {
        super(context);
    }

    public WssCloseView(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public WssCloseView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setReduceArea(int reduceArea) {
        this.reduceArea = reduceArea;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (reduceArea == -1) {
            return super.onTouchEvent(event);
        }
        int action = event.getAction();
        if (action == event.getAction()) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float halfReduceWidth = measuredWidth * reduceArea / 100f / 2;
            float halfReduceHeight = measuredHeight * reduceArea / 100f / 2;
            float x = event.getX();
            float y = event.getY();
            float right = measuredWidth - halfReduceWidth;
            float bottom = measuredHeight - halfReduceHeight;
            Log.d("Shelter", "WssCloseView onTouchEvent() x = " + x + ", y = " + y + ", right = " + right + ", bottom = " + bottom + ", halfReduceWidth = " + halfReduceWidth + ", halfReduceHeight = " + halfReduceHeight);
            if (x > halfReduceWidth && x < right && y > halfReduceHeight && y < bottom) {
                Log.d("Shelter", "WssCloseView onTouchEvent() 点击区域内");
                return super.onTouchEvent(event);
            } else {
                Log.d("Shelter", "WssCloseView onTouchEvent() 点击区域外");
                return false;
            }
        }
        return super.onTouchEvent(event);
    }
}