package com.daydayup.mydemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.consts.Consts;
import com.daydayup.mydemo.util.ArcSlideHelper;
import com.wuyr.ArcSlidingHelper;

/**
 * 旋转的控件容器
 *
 * Created by conan on 2018/7/18.
 */

public class FanLayout extends ViewGroup implements ArcSlidingHelper.OnSlidingListener, ArcSlideHelper.OnSlidingListener {

    private static final int BEARING_TYPE_COLOR = 0;
    private static final int BEARING_TYPE_VIEW = 1;

    private static final int LEFT_TOP = 0;
    private static final int CENTERHORIZONTAL_TOP = 1;
    private static final int RIGHT_TOP = 2;
    private static final int LEFT_CENTERVERTICAL = 3;
    private static final int CENTER = 4;
    private static final int RIGHT_CENTERVERTICAL = 5;
    private static final int LEFT_BOTTOM = 6;
    private static final int CENTERHORIZONTAL_BOTTOM = 7;
    private static final int RIGHT_BOTTOM = 8;

    private int mPivotX;
    private int mPivotY;
    private ArcSlideHelper mArcSlideHelper;
    private boolean mIsEnabled = true;
    private int mBearingType;
    private int mBearingColor;
    private boolean mCanBearingRoll;
    private float mBearingRadius;
    private int mBearingPosition;
    private int mBearingViewId;
    private float mItemOffset;
    private Paint mBearingColorPaint;
    private boolean mIsBearingViewForeground;

    public FanLayout(Context context) {
        this(context, null);
    }

    public FanLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FanLayout, defStyleAttr, 0);
        mBearingType = typedArray.getInteger(R.styleable.FanLayout_bearingType, BEARING_TYPE_COLOR);
        mBearingColor = typedArray.getColor(R.styleable.FanLayout_bearingColor, Color.TRANSPARENT);
        mCanBearingRoll = typedArray.getBoolean(R.styleable.FanLayout_bearingCanRoll, false);
        mBearingRadius = typedArray.getDimension(R.styleable.FanLayout_bearingRadius, 20);
        mBearingPosition = typedArray.getInteger(R.styleable.FanLayout_bearingPosition, 4);
        mBearingViewId = typedArray.getResourceId(R.styleable.FanLayout_bearingLayout, R.mipmap.ic_launcher_round);
        mItemOffset = typedArray.getDimension(R.styleable.FanLayout_itemOffset, 0);
        mIsBearingViewForeground = typedArray.getBoolean(R.styleable.FanLayout_isViewForeground, false);
        typedArray.recycle();

        mBearingColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBearingColorPaint.setColor(mBearingColor);

        //获取系统任务的最小滑动和点击之间的误操距离
        mMinClickDistance = ViewConfiguration.get(context).getScaledTouchSlop();

        //设置回调onDraw方法
        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBearingType == BEARING_TYPE_COLOR && !mIsBearingViewForeground){
            canvas.drawCircle(mPivotX,mPivotY,mBearingRadius,mBearingColorPaint);
        }
    }

    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);
        if (mBearingType == BEARING_TYPE_COLOR && mIsBearingViewForeground)
            canvas.drawCircle(mPivotX,mPivotY,mBearingRadius,mBearingColorPaint);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.v(Consts.LOG_TAG,this.getClass().getSimpleName() + "---onLayout");
        int childCount = getChildCount();
        float angle = 360f / childCount;

        setPivotValue();
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

    private void setPivotValue() {
        switch (mBearingPosition){
            case LEFT_TOP:
                mPivotX = 0;
                mPivotY = 0;
                break;
            case CENTERHORIZONTAL_TOP:
                mPivotX = getWidth() / 2;
                mPivotY = 0;
                break;
            case RIGHT_TOP:
                mPivotX = getWidth();
                mPivotY = 0;
                break;
            case LEFT_CENTERVERTICAL:
                mPivotX = 0;
                mPivotY = getHeight() / 2;
                break;
            case CENTER:
                mPivotX = getWidth() / 2;
                mPivotY = getHeight() / 2;
                break;
            case RIGHT_CENTERVERTICAL:
                mPivotX = getWidth();
                mPivotY = getHeight() / 2;
                break;
            case LEFT_BOTTOM:
                mPivotX = 0;
                mPivotY = getHeight();
                break;
            case CENTERHORIZONTAL_BOTTOM:
                mPivotX = getWidth() / 2;
                mPivotY = getHeight();
                break;
            case RIGHT_BOTTOM:
                mPivotX = getWidth();
                mPivotY = getHeight();
                break;
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
                mArcSlideHelper.abortAnimation();
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
