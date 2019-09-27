package com.daydayup.mydemo.activity.view.loading;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class CircleLoadingView extends View {

    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private Path mCirclePath;
    private Path mDst;
    private float mCircleRadius;
    private float mFraction;
    private float mCircleLength;
    private Path mSuccessPath;
    private Path mFailureFirstPath;
    private Path mFailureSecondPath;
    private boolean mShowFailureFirst;
    private float mFailureFirstFraction;
    private boolean mShowLoadComplete;
    private boolean mShowLoading = true;
    private float mLoadCompleteFraction;
    private boolean mShowFailureSecond;
    private float mFailureSecondFraction;

    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPathMeasure = new PathMeasure();
        mCirclePath = new Path();
        mFailureFirstPath = new Path();
        mFailureSecondPath = new Path();
        mSuccessPath = new Path();
        mDst = new Path();
        mCircleRadius = 100;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShowLoading = false;
                mShowLoadComplete = true;
                startLoadCompleteAnimation();
            }
        });
        valueAnimator.setRepeatCount(1);
        valueAnimator.start();
    }

    private void startLoadCompleteAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1500);
        valueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLoadCompleteFraction = animation.getAnimatedFraction();
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShowLoadComplete = false;
                mShowFailureFirst = true;
                startFailureAnimation(true);
            }
        });
        valueAnimator.start();
    }

    private void startFailureAnimation(final boolean isFirst) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);
        valueAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (isFirst) {
                    mFailureFirstFraction = animation.getAnimatedFraction();
                } else {
                    mFailureSecondFraction = animation.getAnimatedFraction();
                }
                invalidate();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isFirst) {
                    mShowFailureFirst = false;
                    mShowFailureSecond = true;
                    startFailureAnimation(false);
                } else {
                    mShowFailureSecond = false;
                }
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float centerX = w / 2f;
        float centerY = h / 2f;
        //重新设置圆形，并获取其周长
        mCirclePath.reset();
        mCirclePath.addCircle(centerX, centerY, mCircleRadius, Path.Direction.CCW);
        mPathMeasure.setPath(mCirclePath, true);
        mCircleLength = mPathMeasure.getLength();
        //设置加载失败的路径
        mFailureFirstPath.reset();
        int offset = 40;
        mFailureFirstPath.moveTo(centerX - offset, centerY - offset);
        mFailureFirstPath.lineTo(centerX + offset, centerY + offset);
        mFailureSecondPath.moveTo(centerX + offset, centerY - offset);
        mFailureSecondPath.lineTo(centerX - offset, centerY + offset);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDst.reset();
        if (mShowLoading) {
            float endDistance = mCircleLength * mFraction;
            float startDistance = mFraction > 0.5f ? 2 * (endDistance - mCircleLength * 0.5f) : 0;
            mPathMeasure.getSegment(startDistance, endDistance, mDst, true);
            canvas.drawPath(mDst, mPaint);
        } else if (mShowLoadComplete) {
            float endDistance = mCircleLength * mLoadCompleteFraction;
            float startDistance = 0;
            mPathMeasure.getSegment(startDistance, endDistance, mDst, true);
            canvas.drawPath(mDst, mPaint);
        } else if (mShowFailureFirst) {
            canvas.drawPath(mCirclePath, mPaint);
            mPathMeasure.setPath(mFailureFirstPath, false);
            float endDistance = mPathMeasure.getLength() * mFailureFirstFraction;
            mPathMeasure.getSegment(0, endDistance, mDst, true);
            canvas.drawPath(mDst, mPaint);
        } else if (mShowFailureSecond) {
            canvas.drawPath(mCirclePath, mPaint);
            canvas.drawPath(mFailureFirstPath, mPaint);
            mPathMeasure.setPath(mFailureSecondPath, false);
            float endDistance = mPathMeasure.getLength() * mFailureSecondFraction;
            mPathMeasure.getSegment(0, endDistance, mDst, true);
            canvas.drawPath(mDst, mPaint);
        } else {
            canvas.drawPath(mCirclePath, mPaint);
            canvas.drawPath(mFailureFirstPath, mPaint);
            canvas.drawPath(mFailureSecondPath, mPaint);
        }
    }
}
