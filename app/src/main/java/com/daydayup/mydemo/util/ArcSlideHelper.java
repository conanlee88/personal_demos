package com.daydayup.mydemo.util;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import android.widget.Scroller;

/**
 * Created by conan on 2018/7/18.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class ArcSlideHelper {

    private VelocityTracker mVelocityTracker;
    private int mPivotX;
    private int mPivotY;
    private OnSlidingListener mListener;
    private Scroller mScroller;
    private float mStartX;
    private float mStartY;
    private boolean mIsSelfSliding;
    private boolean mIsInertialSlidingEnable;

    private ArcSlideHelper(Context context, int pivotX, int pivotY, OnSlidingListener onSlidingListener) {
        this.mPivotX = pivotX;
        this.mPivotY = pivotY;
        this.mListener = onSlidingListener;
        this.mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }

    public static ArcSlideHelper create(View targetView, OnSlidingListener onSlidingListener) {
        int width = targetView.getWidth();
        int height = targetView.getHeight();
        if (width == 0){
            // TODO: 2018/7/18 如果宽度为0，提示宽度无效，需要调用updatePivotX方法来设置x轴的旋转基点
        }
        if (height == 0){
            // TODO: 2018/7/18 如果高度为0，提示高度无效，需要调用updatePivotY方法来设置y轴的旋转基点
        }
        width /= 2;
        height /= 2;
        int absoluteX = getAbsoluteX(targetView);
        int absoluteY = getAbsoluteY(targetView);
        return new ArcSlideHelper(targetView.getContext(), width + absoluteX, height + absoluteY, onSlidingListener);
    }

    private static int getAbsoluteX(View view) {
        float x = view.getX();
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof View){
            x += getAbsoluteX((View) parent);
        }
        return (int) x;
    }

    private static int getAbsoluteY(View view) {
        float y = view.getY();
        ViewParent parent = view.getParent();
        if (parent != null && parent instanceof View){
            y += getAbsoluteX((View) parent);
        }
        return (int) y;
    }

    public void handleMovement(MotionEvent event){
        checkIsRecycled();
        float x,y;
        if (mIsSelfSliding){
            x = event.getRawX();
            y = event.getRawY();
        }else {
            x = event.getX();
            y = event.getY();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(x,y);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (mIsInertialSlidingEnable){
                    mVelocityTracker.computeCurrentVelocity(1000);
                    mScroller.fling(0,0,(int)mVelocityTracker.getXVelocity(), (int) mVelocityTracker.getYVelocity(),
                            Integer.MIN_VALUE,Integer.MAX_VALUE,Integer.MIN_VALUE,Integer.MAX_VALUE);
                    startFling();
                }
                break;
        }
        mStartX = x;
        mStartY = y;
    }

    private void checkIsRecycled() {

    }

    private void handleActionMove(float x, float y) {
        float lineA = (float) Math.sqrt(Math.pow(Math.abs(mStartX - mPivotX), 2) + Math.pow(Math.abs(mStartY - mPivotY), 2));
        float lineB = (float) Math.sqrt(Math.pow(Math.abs(x- mPivotX),2) + Math.pow(Math.abs(y - mPivotY),2));
        float lineC = (float) Math.sqrt(Math.pow(Math.abs(x- mStartX),2) + Math.pow(Math.abs(y - mStartY),2));
        double degrees = Math.toDegrees(Math.acos((Math.pow(lineA, 2) + Math.pow(lineB, 2) + Math.pow(lineC, 2)) / 2 * lineA * lineB));
        float angle = fixAngle(degrees);
        if (!Float.isNaN(angle)){
            // TODO: 2018/7/18 滑动回调事件
//            mListener.onSliding(mIsInertialSlidingEnable == );
        }
    }

    private float fixAngle(double degrees) {
        if (degrees < 0){
            degrees += 360;
        }
        if (degrees > 360){
            degrees %= 360;
        }
        return (float) degrees;
    }

    private void startFling() {

    }

    private interface OnSlidingListener{

        void onSliding(float angle);
    }
}
