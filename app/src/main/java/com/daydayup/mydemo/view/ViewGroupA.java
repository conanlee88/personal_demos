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

public class ViewGroupA extends ViewGroup {


    public ViewGroupA(Context context) {
        super(context);
    }

    public ViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
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
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0;i < getChildCount(); i ++){
            View childAt = getChildAt(i);
//            childAt.layout(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom());
            childAt.layout(l, t, r, b);
        }
    }
}
