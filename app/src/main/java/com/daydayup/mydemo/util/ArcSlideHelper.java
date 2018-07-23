package com.daydayup.mydemo.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.FloatRange;
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
    private int mPivotX,mPivotY;
    private OnSlidingListener mListener;
    private Scroller mScroller;
    private float mStartX,mStartY;
    private boolean mIsSelfSliding;
    private boolean mIsInertialSlidingEnable;
    private boolean mIsClockwiseScrolling;
    private boolean mIsRecycled;

    private Handler mHandler;
    private boolean mIsShouldBeGetY;
    private float mLastScrollOffset;
    private float mScrollAvailabilityRatio;

    /**
     * VelocityTracker的惯性滚动利用率
     * 数值越大，惯性滚动的动画时间越长
     * @param ratio 滚动利用率(范围 0-1)
     */
    public void setScrollAvailabilityRatio(@FloatRange(from = 0,to = 1) float ratio){
        mScrollAvailabilityRatio = ratio;
    }

    private ArcSlideHelper(Context context, int pivotX, int pivotY, OnSlidingListener onSlidingListener) {
        this.mPivotX = pivotX;
        this.mPivotY = pivotY;
        this.mListener = onSlidingListener;
        this.mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mScrollAvailabilityRatio = 0.3f;
        mHandler = new InertialSlidingHandler(this);
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

    /**
     * 设置自身滑动
     * @param isSelfSliding 是否view自身滑动
     */
    public void setSelfSliding(boolean isSelfSliding){
        mIsSelfSliding = isSelfSliding;
    }

    /**
     * 设置惯性滑动
     * @param isInertialSlidingEnable 是否要惯性滑动
     */
    public void setInertialSlidingEnable(boolean isInertialSlidingEnable){
        mIsInertialSlidingEnable = isInertialSlidingEnable;
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
            y += getAbsoluteY((View) parent);
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

    /**
     * 打断动画
     */
    public void abortAnimation(){
        checkIsRecycled();
        if (!mScroller.isFinished()){
            mScroller.abortAnimation();
        }
    }

    private void checkIsRecycled() {
        if (mIsRecycled){
            throw new RuntimeException("ArcSlideHelper is already recycled");
        }
    }

    /**
     * 更新当前手指触摸的坐标
     * 在ViewGroup中的onInterceptTouchEvent中使用
     * @param event 触摸事件
     */
    public void updateMovement(MotionEvent event){
        checkIsRecycled();
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE){
            if (mIsSelfSliding){
                mStartX = event.getRawX();
                mStartY = event.getRawY();
            }else {
                mStartX = event.getX();
                mStartY = event.getY();
            }
        }
    }

    /**
     * 更新圆心坐标X
     * @param pivotX 新的圆心坐标x
     */
    public void updatePivotX(int pivotX){
        checkIsRecycled();
        mPivotX = pivotX;
    }

    /**
     * 更新圆心坐标Y
     * @param pivotY 新的圆心坐标Y
     */
    public void updatePivotY(int pivotY){
        checkIsRecycled();
        mPivotY = pivotY;
    }

    /**
     * 释放资源
     */
    public void release(){
        checkIsRecycled();
        mScroller = null;
        mVelocityTracker.recycle();
        mVelocityTracker = null;
        mListener = null;
        mHandler = null;
        mIsRecycled = true;
    }

    private void handleActionMove(float x, float y) {

        float lineA = (float) Math.sqrt(Math.pow(Math.abs(mStartX - mPivotX), 2) +
                Math.pow(Math.abs(mStartY - mPivotY), 2));
        float lineB = (float) Math.sqrt(Math.pow(Math.abs(x- mPivotX),2) +
                Math.pow(Math.abs(y - mPivotY),2));
        float lineC = (float) Math.sqrt(Math.pow(Math.abs(x- mStartX),2) +
                Math.pow(Math.abs(y - mStartY),2));

        if (lineA > 0 && lineB > 0 && lineC > 0){
            double degrees = Math.toDegrees(Math.acos(
                    (Math.pow(lineA, 2) + Math.pow(lineB, 2) - Math.pow(lineC, 2)) / (2 * lineA * lineB)));
            float angle = fixAngle(degrees);
            if (!Float.isNaN(angle)){
                // 滑动回调事件
                mIsClockwiseScrolling = isClockwise(x,y);
                mListener.onSliding(mIsClockwiseScrolling ? angle : -angle);
            }
        }
    }

    /**
     * 判断是否是顺时针方向
     * @param x 当前x位置
     * @param y 当前y位置
     * @return  是否是顺时针
     */
    private boolean isClockwise(float x, float y) {
        float deltaX = Math.abs(x - mStartX);
        float deltaY = Math.abs(y - mStartY);
        mIsShouldBeGetY = deltaY > deltaX;
        if (deltaX < deltaY){
            //竖向滑动
            return x < mPivotX != y > mStartY;
        }else {
            //横向滑动
            return y < mPivotY == x > mStartX;
        }
    }

    /**
     * 调整角度，使其在0~360之间
     * @param degrees 要调整的调度
     * @return 调整后的角度
     */
    private float fixAngle(double degrees) {
        if (degrees < 0){
            degrees += 360;
        }
        if (degrees > 360){
            degrees %= 360;
        }
        return (float) degrees;
    }

    /**
     * 开始惯性滑动
     */
    private void startFling() {
        mHandler.sendEmptyMessage(0);
    }

    /**
     * 处理惯性滚动
     */
    private void computeInertialSliding() {
        checkIsRecycled();
        if (mScroller.computeScrollOffset()){
            float y = (mIsShouldBeGetY ? mScroller.getCurrY() : mScroller.getCurrX()) * mScrollAvailabilityRatio;
            if (mLastScrollOffset != 0){
                float offset = fixAngle(Math.abs(y - mLastScrollOffset));
                mListener.onSliding(mIsClockwiseScrolling ? offset : -offset);
            }
            mLastScrollOffset = y;
            startFling();
        }else if (mScroller.isFinished()){
            mLastScrollOffset = 0;
        }
    }

    /**
     * 惯性滚动分发器
     */
    private static class InertialSlidingHandler extends Handler{

        ArcSlideHelper mHelper;

        InertialSlidingHandler(ArcSlideHelper arcSlideHelper){
            mHelper = arcSlideHelper;
        }
        @Override
        public void handleMessage(Message msg) {
            mHelper.computeInertialSliding();
        }

    }

    /**
     * 开始滑动监听器
     */
    public interface OnSlidingListener{

        void onSliding(float angle);
    }

    /**
     * 滑动结束监听
     */
    public interface OnSlideFinishListener{

        void onSlideFinished();
    }
}
