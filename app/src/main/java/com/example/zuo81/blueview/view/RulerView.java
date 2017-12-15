package com.example.zuo81.blueview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
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

import java.math.BigDecimal;

import static android.view.View.MeasureSpec.AT_MOST;
import static android.view.View.MeasureSpec.EXACTLY;
import static android.view.View.MeasureSpec.UNSPECIFIED;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created by zuo81 on 2017/12/11.
 */

public class RulerView extends View {
    private int width, height;
    private int rulerRight = 0;
    private int minScale = 0;
    private int scaleGap = 50;
    private int rulerHeight = 250;
    private int firstScale = 600;
    private int rulerCount = 10;
    private float currentX;
    private float downX, moveX, upX, lastMoveX;
    private Paint mBgPaint;
    private Paint mRulerPaint;
    private Paint mResultPaint;
    private boolean isUp;
    private Rect numRect;
    private Rect redNumRect;
    private ValueAnimator mValueAnimator;
    private String resultText = String.valueOf(firstScale);

    // if delete constructor below
    //Caused by: java.lang.NoSuchMethodException: <init> [class android.content.Context, interface android.util.AttributeSet]
    public RulerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        height = heightSize;
        width = widthSize - getPaddingLeft() - getPaddingRight();
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        currentX = event.getX();
        isUp = false;
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = currentX - downX + lastMoveX;
                break;
            case MotionEvent.ACTION_UP:
                lastMoveX = moveX;
                upX = event.getX();
                isUp = true;
                break;
        }
        invalidate();
        return true;
    }

    private float getMove(float scale) {
        return width / 2 - rulerCount * scaleGap * (scale - minScale);
    }

    private void drawRuler(Canvas canvas) {
        canvas.translate(0, height / 2);
        if(firstScale != -1) {
            moveX = scaleGap * rulerCount * firstScale;
            lastMoveX = moveX;
            if(mValueAnimator != null && !mValueAnimator.isRunning()) {
                mValueAnimator = ValueAnimator.ofFloat(getMove(minScale), getMove(firstScale));
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        moveX = (Float)animation.getAnimatedValue();
                        lastMoveX = moveX;
                        invalidate();
                    }
                });
                mValueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        firstScale = -1;
                    }
                });
                mValueAnimator.setDuration(300);
                mValueAnimator.start();
            }
        }
        int num1;
        int num2;

        num1 = -(int)moveX / scaleGap;
        num2 = (int)moveX % scaleGap;

        if(isUp) {
            int minMove;
            int positiveNum2 = Math.abs(num2);
            minMove = positiveNum2 >= scaleGap / 2 ? -scaleGap + positiveNum2 : positiveNum2;
            Log.d("MMM", minMove + " minMove");
            if(mValueAnimator != null && !mValueAnimator.isRunning()) {
                mValueAnimator = ValueAnimator.ofFloat(moveX, moveX + minMove);
                mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        Log.d("MMM", animation.getAnimatedValue() + " animatedValue");
                        moveX = (float)animation.getAnimatedValue();
                        lastMoveX = moveX;
                        invalidate();
                    }
                });
                mValueAnimator.setDuration(300);
                mValueAnimator.start();
                isUp = false;
            }
        }

        num1 = -(int)moveX / scaleGap;
        num2 = (int)moveX % scaleGap;

        canvas.save();
        canvas.translate(num2, 0);//not num2 but -num2  ...or error
        rulerRight = 0;
        resultText = String.valueOf(new BigDecimal((width / 2 - moveX) / (scaleGap * rulerCount)).setScale(1, ROUND_HALF_UP).floatValue());
        while(rulerRight < width) {
            Log.d("GG", moveX + " moveX");
            if(num1 % rulerCount == 0) {
                canvas.drawLine(0, 0, 0, rulerHeight / 2 - 15, mRulerPaint);
                mRulerPaint.getTextBounds(num1 / rulerCount + "", 0, (num1 / rulerCount + "").length(), numRect);
                canvas.drawText(num1 / rulerCount + "",  0 - numRect.width() / 2, rulerHeight / 2 + 15, mRulerPaint);
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

    private void drawResultText(Canvas canvas, String resultText) {
        canvas.save();
        canvas.translate(0, 0);
        canvas.translate(width / 2, height / 8);
        //canvas.drawText((int)((-moveX / scaleGap - moveX % scaleGap) / rulerCount) + "", 0, 0, mResultPaint);
        mResultPaint.getTextBounds(resultText, 0, resultText.length(), redNumRect);
        canvas.drawText(resultText, -redNumRect.width() / 2, -height / 6, mResultPaint);
        canvas.restore();
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
        mRulerPaint.setTextSize(30);

        mResultPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mResultPaint.setColor(Color.RED);
        mResultPaint.setStrokeWidth(20);
        mResultPaint.setStyle(Paint.Style.FILL);
        mResultPaint.setStrokeCap(Paint.Cap.ROUND);
        mResultPaint.setTextSize(40);

        numRect = new Rect();
        redNumRect = new Rect();

        mValueAnimator = new ValueAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawRuler(canvas);
        drawResultText(canvas, resultText);
    }
}
