package com.daydayup.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.mydemo.consts.Consts;

import java.util.Locale;

/**
 * Created by conan on 2018/7/18.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class FanLayout extends ViewGroup {

    private int mPivotX;
    private int mPivotY;

    public FanLayout(Context context) {
        this(context, null);
    }

    public FanLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
        String.format(Locale.getDefault(),"","");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        Log.v(Consts.LOG_TAG,this.getClass().getSimpleName() + "---childCount = " + childCount);
        float angle = 360f / childCount;
        mPivotX = getWidth() / 2;
        mPivotY = getHeight() / 2;
        Log.v(Consts.LOG_TAG,this.getClass().getSimpleName() + "---mPivotX = " + mPivotX);
        Log.v(Consts.LOG_TAG,this.getClass().getSimpleName() + "---mPivotY = " + mPivotY);
        for (int i = 0; i < childCount;i ++){
            View child = getChildAt(i);
            int layoutWidth = child.getMeasuredWidth();
            int layoutHeight = child.getMeasuredHeight() / 2;
            Log.v(Consts.LOG_TAG,this.getClass().getSimpleName() + "---layoutWidth = " + layoutWidth);
            Log.v(Consts.LOG_TAG,this.getClass().getSimpleName() + "---layoutHeight = " + layoutHeight);

            child.layout(mPivotX,mPivotY - layoutHeight,mPivotX + layoutWidth,mPivotY + layoutHeight);
            child.setPivotX(0);
            child.setPivotY(layoutHeight);
            child.setRotation(i * angle);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }
}
