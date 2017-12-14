package com.example.zuo81.blueview.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by zuo81 on 2017/12/14.
 */

public class ConvertToPixelUtil {
    public static float convertToPixel(int i) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.density * i;
    }
}
