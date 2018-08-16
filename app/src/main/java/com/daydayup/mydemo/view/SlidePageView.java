package com.daydayup.mydemo.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * $desc$
 * Created by conan on 2018/8/16.
 */

public class SlidePageView extends FrameLayout{

    private View mSlideView;
    private View mContentView;
    private ViewDragHelper mViewDragHelper;
    private int mWidth;
    private Callback mCallback = new Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //控制边界
            if (left > mWidth){
                left = mWidth;
            }else if (left < 0){
                left = 0;
            }
            return left;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {//xvel x轴上的滑动速度；yvel y轴上的滑动速度
            //释放view后的回调方法，可在这写放手后自动滑动方法
            float scrollX = releasedChild.getLeft();
            mViewDragHelper.smoothSlideViewTo(mContentView,scrollX < mWidth / 2f ? 0 : mWidth,0);
            ViewCompat.postInvalidateOnAnimation(SlidePageView.this);
        }
    };

    public SlidePageView(@NonNull Context context) {
        this(context,null);
    }

    public SlidePageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidePageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化viewDragHelper
        mViewDragHelper = ViewDragHelper.create(this, mCallback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //将触摸事件的拦截事件交给viewDragHelper处理
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将触摸事件交给viewDragHelper处理
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSlideView = getChildAt(0);
        mContentView = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mSlideView.getMeasuredWidth();
    }

    @Override
    public void computeScroll() {
        //处理滑动
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }
}
