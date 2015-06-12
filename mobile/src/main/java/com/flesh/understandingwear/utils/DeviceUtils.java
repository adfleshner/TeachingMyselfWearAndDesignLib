package com.flesh.understandingwear.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

/**
 * Created by afleshner on 6/11/2015.
 */
public class DeviceUtils {


    /**
     * Takes in the context and checks its rotation if at its normal rotation the width is greater then the height then it is a tablet ( works for most newer devices 4.0 and up)
     * Returns false by default.
     * @param cxt
     * @return
     */
    public static boolean isTablet(Context cxt) {
        Display display = ((WindowManager) cxt.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int rotation = display.getRotation();
        switch (rotation) {
            //at 0 and 90 a tablets width is greater then its height
            //at 180 and 270 a tablets width is less then its height
            case Surface.ROTATION_0:
                if (width < height) {
                    return false;
                } else {
                    return true;
                }
            case Surface.ROTATION_90:
                if (width < height) {
                    return true;
                } else {
                    return false;
                }
            case Surface.ROTATION_180:
                if (width < height) {
                    return false;
                } else {
                    return true;
                }
            case Surface.ROTATION_270:
                if (width < height) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }


    }


}
