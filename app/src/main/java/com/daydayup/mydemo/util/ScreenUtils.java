package com.daydayup.mydemo.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.daydayup.mydemo.MyDemoApplication;

/**
 * $desc$
 * Created by conan on 2018/8/29.
 */

public class ScreenUtils {

    /**
     * 获取屏幕宽高
     * @return 返回带有屏幕宽高属性的 DisplayMetrics
     */
    public static DisplayMetrics getScreenDisplayMetrics(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) MyDemoApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null){
            Display defaultDisplay = windowManager.getDefaultDisplay();
            defaultDisplay.getMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    /**
     * 获取屏幕的像素与dp的转换比率
     * @return 屏幕的像素与dp的转换比率
     */
    public static float getScreenDensity(){
        return getScreenDisplayMetrics().density;
    }
}
