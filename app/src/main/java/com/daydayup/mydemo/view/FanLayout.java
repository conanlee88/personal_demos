package com.daydayup.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.daydayup.mydemo.util.ArcSlideHelper;
import com.wuyr.ArcSlidingHelper;

import javax.crypto.Mac;

/**
 * Created by conan on 2018/7/18.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class FanLayout extends ViewGroup implements ArcSlidingHelper.OnSlidingListener, ArcSlideHelper.OnSlidingListener {

    private int mPivotX;
    private int mPivotY;
    private ArcSlideHelper mArcSlideHelper;
    private boolean mIsEnabled = true;

    public FanLayout(Context context) {
        this(context, null);
    }

    public FanLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取系统任务的最小滑动和点击之间的误操距离
        mMinClickDistance = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        float angle = 360f / childCount;
        mPivotX = getWidth() / 2;
        mPivotY = getHeight() / 2;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int layoutWidth = child.getMeasuredWidth();
            int layoutHeight = child.getMeasuredHeight() / 2;

            child.layout(mPivotX, mPivotY - layoutHeight, mPivotX + layoutWidth, mPivotY + layoutHeight);
            child.setPivotX(0);
            child.setPivotY(layoutHeight);
            child.setRotation(i * angle);
        }
    }

    /**
     * 在onSizeChanged方法中初始化ArcSlideHelper的原因是：
     * 这个方法回调时，getWidth()和getHeight()方法以及获取到了正确的宽高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mArcSlideHelper == null) {
            mArcSlideHelper = ArcSlideHelper.create(this, this);
            mArcSlideHelper.setInertialSlidingEnable(true);
            mArcSlideHelper.setSelfSliding(true);
        } else {
            mArcSlideHelper.updatePivotX(w / 2);
            mArcSlideHelper.updatePivotY(h / 2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mArcSlideHelper.handleMovement(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                mIsScrolled = false;
                break;
        }
        return true;
    }

    private float mStartX, mStartY;
    private float mMinClickDistance;
    private boolean mIsScrolled;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果已经开始滑动就拦截
        if ((ev.getAction() == MotionEvent.ACTION_MOVE && mIsScrolled) ||
                super.onInterceptTouchEvent(ev)) {
            return true;
        }
        //如果不允许拦截就返回false
        if (!mIsEnabled) {
            return false;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mArcSlideHelper.abortAnimatinon();
                mStartX = ev.getX();
                mStartY = ev.getY();
                //手指按下的时候更新一下坐标
                mArcSlideHelper.updateMovement(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = ev.getX() - mStartX;
                float deltaY = ev.getY() - mStartY;
                if (Math.abs(deltaX) > mMinClickDistance || Math.abs(deltaY) > mMinClickDistance){
                    //做拦截的时候更新一下坐标
                    mArcSlideHelper.updateMovement(ev);
                    mIsScrolled = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                mIsScrolled = false;
                break;
        }
        return mIsScrolled;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mArcSlideHelper != null) {
            mArcSlideHelper.release();
            mArcSlideHelper = null;
        }
    }

    @Override
    public void onSliding(float angle) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setRotation(view.getRotation() + angle);
        }
    }
}
