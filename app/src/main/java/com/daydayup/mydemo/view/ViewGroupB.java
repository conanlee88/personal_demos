package com.daydayup.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static com.daydayup.mydemo.consts.Consts.LOG_TAG;

/**
 * $desc$
 * Created by conan on 2018/8/14.
 */

public class ViewGroupB extends ViewGroup {


    public ViewGroupB(Context context) {
        super(context);
    }

    public ViewGroupB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---onTouchEvent");
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
        for (int i = 0;i < getChildCount(); i ++){
            View childAt = getChildAt(i);
            childAt.layout(l, t, r, b);
        }
    }
}
