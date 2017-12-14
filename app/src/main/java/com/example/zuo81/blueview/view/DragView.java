package com.example.zuo81.blueview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.zuo81.blueview.R;
import com.example.zuo81.blueview.utils.ConvertToPixelUtil;

/**
 * Created by zuo81 on 2017/12/12.
 */

public class DragView extends LinearLayout {
    private SeekBar mSeekBarWidth;
    private SeekBar mSeekBarHeight;
    private FrameLayout mFrameLayout;
    private int width;
    private int height;

    public DragView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(R.styleable.DragView);
        mTypedArray.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //width =
        mFrameLayout = findViewById(R.id.frame_layout_drag_view);
        mSeekBarWidth = findViewById(R.id.seekBar_width_drag_view);
        mSeekBarHeight = findViewById(R.id.seekBar_height_drag_view);
        mSeekBarWidth.setOnSeekBarChangeListener(onSeekBarChangeListener);
        mSeekBarHeight.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            LayoutParams params = (LayoutParams) mFrameLayout.getLayoutParams();
            int minWidth = (int)ConvertToPixelUtil.convertToPixel(100);
            int minHeight = (int)ConvertToPixelUtil.convertToPixel(100);
            params.width = minWidth + (DragView.this.getWidth() - minWidth) * mSeekBarWidth.getProgress() / 100;
            params.height = minHeight + (DragView.this.getHeight() - minHeight) * mSeekBarHeight.getProgress() / 100;
            mFrameLayout.setLayoutParams(params);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
