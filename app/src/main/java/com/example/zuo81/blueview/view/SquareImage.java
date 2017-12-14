package com.example.zuo81.blueview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zuo81 on 2017/12/14.
 */

public class SquareImage extends View {
    private Paint paint;

    public SquareImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        if( w >= h) {
            w = h;
        } else {
            h = w;
        }
        Rect rect = new Rect(0, 0, w, h);
        canvas.drawRect(rect, paint);
    }
}
