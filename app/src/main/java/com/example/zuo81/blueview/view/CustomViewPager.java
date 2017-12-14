package com.example.zuo81.blueview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by zuo81 on 2017/12/14.
 */

public class CustomViewPager extends ViewPager {

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }*/

    //如果上面两个方法都false，则不能滑动，RulerView正常
    //如果只是注掉onTouchEvent() 则可以滑动，而且RulerView也不会有问题
}
