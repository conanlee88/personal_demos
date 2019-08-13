package com.daydayup.mydemo.view;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.activity.view.splitview.SplitElement;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by conan on 19-8-9
 * Describe:
 */
public class SplitView extends View {

    private static final String TAG = "SplitView";
    private Paint mPaint;
    private Bitmap mBitmap;
    private List<SplitElement> mSplitElements = new ArrayList<>();
    private ValueAnimator mValueAnimator;
    private boolean mIsStart;
    private int scale = 5;
    private int centerOffset = scale / 2;

    public SplitView(Context context) {
        this(context, null);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inSampleSize = 3;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puzzle05, ops);
        Log.d(TAG, "init: width = " + mBitmap.getWidth());
        Log.d(TAG, "init: height = " + mBitmap.getHeight());
        for (int i = 0; i < mBitmap.getWidth() / scale; i++) {
            for (int j = 0; j < mBitmap.getHeight() / scale; j++) {
                SplitElement splitElement = new SplitElement(i, j);
                splitElement.color = mBitmap.getPixel(i * scale + centerOffset, j * scale + centerOffset);
                //速度 (-20,20)
                splitElement.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                splitElement.vY = rangInt(-15, 35);
                splitElement.aX = 0;
                splitElement.aY = 0.98f;
                mSplitElements.add(splitElement);
            }
        }
        Log.d(TAG, "init: mSplitElements = " + mSplitElements);
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "value = " + animation.getAnimatedValue());
                updateSplitElements();
                invalidate();
            }
        });
    }

    private int rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        //在0到(max - min)范围内变化，取大于x的最小整数 再随机
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    private void updateSplitElements() {
        for (SplitElement splitElement : mSplitElements) {
            splitElement.x += splitElement.vX;
            splitElement.y += splitElement.vY;
            splitElement.vX += splitElement.aX;
            splitElement.vY += splitElement.aY;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float startX = (measuredWidth - mBitmap.getWidth()) / 2f;
        float startY = (measuredHeight - mBitmap.getHeight()) / 2f;
        canvas.translate(startX, startY);
        for (SplitElement splitElement : mSplitElements) {
            mPaint.setColor(splitElement.color);
            canvas.drawRect(splitElement.x * scale - centerOffset, splitElement.y * scale - centerOffset, splitElement.x * scale + scale - centerOffset, splitElement.y * scale + scale - centerOffset, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!mIsStart) {
                mValueAnimator.start();
                mIsStart = true;
            }
        }
        return super.onTouchEvent(event);
    }
}
