package com.daydayup.mydemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.daydayup.mydemo.util.ArcSlideHelper;
import com.wuyr.ArcSlidingHelper;

/**
 * Created by conan on 2018/7/18.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class FanLayout extends ViewGroup implements ArcSlidingHelper.OnSlidingListener, ArcSlideHelper.OnSlidingListener {

    private int mPivotX;
    private int mPivotY;
    private ArcSlideHelper mArcSlideHelper;
//    private ArcSlidingHelper mArcSlidingHelper;

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
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        float angle = 360f / childCount;
        mPivotX = getWidth() / 2;
        mPivotY = getHeight() / 2;
        for (int i = 0; i < childCount;i ++){
            View child = getChildAt(i);
            int layoutWidth = child.getMeasuredWidth();
            int layoutHeight = child.getMeasuredHeight() / 2;

            child.layout(mPivotX,mPivotY - layoutHeight,mPivotX + layoutWidth,mPivotY + layoutHeight);
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
        if (mArcSlideHelper == null){
//            mArcSlidingHelper = ArcSlidingHelper.create(this,this);
//            mArcSlidingHelper.enableInertialSliding(true);
            mArcSlideHelper = ArcSlideHelper.create(this,this);
            mArcSlideHelper.setInertialSlidingEnable(true);
            mArcSlideHelper.setSelfSliding(true);
        }else {
//            mArcSlidingHelper.updatePivotX(w / 2);
//            mArcSlidingHelper.updatePivotY(h / 2);
            mArcSlideHelper.updatePivotX(w / 2);
            mArcSlideHelper.updatePivotY(h / 2);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mArcSlidingHelper.handleMovement(event);
        mArcSlideHelper.handleMovement(event);
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mArcSlideHelper != null){
            mArcSlideHelper.release();
            mArcSlideHelper = null;
        }
    }

    @Override
    public void onSliding(float angle) {
        for (int i = 0;i < getChildCount();i ++){
            View view = getChildAt(i);
            view.setRotation(view.getRotation() + angle);
        }
    }
}
