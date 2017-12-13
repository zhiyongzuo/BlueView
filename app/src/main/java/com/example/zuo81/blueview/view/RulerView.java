package com.example.zuo81.blueview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.UNSPECIFIED;

/**
 * Created by zuo81 on 2017/12/11.
 */

public class RulerView extends View {
    private int width, height;
    private int rulerRight = 0;
    private int scaleGap = 50;
    private int rulerHeight = 250;
    private int rulerCount = 10;
    private float currentX;
    private float downX, moveX, upX, lastMoveX;
    private Paint mBgPaint;
    private Paint mRulerPaint;
    private Paint mResultPaint;

    // if delete constructor below
    //Caused by: java.lang.NoSuchMethodException: <init> [class android.content.Context, interface android.util.AttributeSet]
    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch(heightMode) {
            case AT_MOST:
                height = rulerHeight + 400;
                break;
            case EXACTLY:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;
            case UNSPECIFIED:
                height = heightSize + getPaddingTop() + getPaddingBottom();
                break;
        }
        width = 400 + getPaddingLeft() + getPaddingRight();
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = currentX - downX + lastMoveX;
                Log.d("AAA", moveX + "moveX");
                break;
            case MotionEvent.ACTION_UP:
                lastMoveX = moveX;
                upX = event.getX();
                break;
        }
        invalidate();
        return true;
    }

    private void drawRuler(Canvas canvas) {
        canvas.translate(0, height / 2);
        int num1;
        int num2;
        num1 = (int)moveX / scaleGap;
        num2 = (int)moveX % scaleGap;
        canvas.save();//应该是防止坐标转换太多不知道到底在哪了
        canvas.translate(-num2, 0);//not -num2 but num2  ...error
        rulerRight = 0;
        while(rulerRight < width) {
            if(num1 % rulerCount == 0) {
                canvas.drawLine(0, 0, 0, rulerHeight / 2 - 15, mRulerPaint);
                canvas.drawText(num1 / rulerCount + 666 + "",  0, rulerHeight / 2 + 10, mRulerPaint);
            } else {
                canvas.drawLine(0, 0, 0, rulerHeight / 2 -105, mRulerPaint);
            }
            ++num1;
            rulerRight += scaleGap;
            canvas.translate(scaleGap, 0);
        }
        canvas.restore();
        canvas.drawLine(width / 2, 0, width / 2, rulerHeight / 2 -15, mResultPaint);
    }

    private void drawResultText(Canvas canvas) {

    }

    @TargetApi(21)
    private void drawBg(Canvas canvas) {
        RectF rect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rect, 50, 50, mBgPaint);
    }

    private void init() {
        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(Color.WHITE);
        mBgPaint.setStyle(Paint.Style.FILL);

        mRulerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRulerPaint.setColor(Color.BLACK);
        mRulerPaint.setStrokeWidth(10);
        mRulerPaint.setStrokeCap(Paint.Cap.ROUND);
        mRulerPaint.setTextSize(20);

        mResultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mResultPaint.setColor(Color.RED);
        mResultPaint.setStrokeWidth(15);
        mResultPaint.setStyle(Paint.Style.FILL);
        mResultPaint.setStrokeCap(Paint.Cap.ROUND);
        mResultPaint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //why cannot be roundRect
        drawBg(canvas);
        drawRuler(canvas);
        drawResultText(canvas);
    }
}
