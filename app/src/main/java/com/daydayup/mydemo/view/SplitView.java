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
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

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
        Random random = new Random();
        for (int i = 0; i < mBitmap.getWidth(); i++) {
            for (int j = 0; j < mBitmap.getHeight(); j++) {
                SplitElement splitElement = new SplitElement(i, j);
                splitElement.color = mBitmap.getPixel(i, j);
                splitElement.vX = random.nextInt(20) - 10;
                splitElement.vY = random.nextInt(20) - 10;
//                splitElement.aX = 1;
                splitElement.aY = 10;
                mSplitElements.add(splitElement);
            }
        }
        mValueAnimator = ValueAnimator.ofFloat(1, 2);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                updateSplitElements(value);
                invalidate();
            }
        });
    }

    private void updateSplitElements(float value) {
        for (SplitElement splitElement : mSplitElements) {
            splitElement.x += splitElement.vX * value;
            splitElement.y += splitElement.vY * value;
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
            canvas.drawRect(splitElement.x, splitElement.y, splitElement.x + 1, splitElement.y + 1, mPaint);
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
