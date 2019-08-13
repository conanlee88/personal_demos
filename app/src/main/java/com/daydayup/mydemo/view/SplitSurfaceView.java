package com.daydayup.mydemo.view;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.activity.view.splitview.SplitElement;
import com.daydayup.mydemo.util.NumberUtils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by conan on 19-8-13
 * Describe:
 */
public class SplitSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "SplitSurfaceView";

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private List<SplitElement> mSplitElements = new ArrayList<>();
    private Bitmap mBitmap;
    private int mElementRadius = 5;
    private ValueAnimator mValueAnimator;
    private Thread mDrawingThread;

    public SplitSurfaceView(Context context) {
        this(context, null);
    }

    public SplitSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SplitSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.d(TAG, "init: ");
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.puzzle05, options);
        initElements();
        initAnimation();
        mDrawingThread = new DrawThread();
    }

    private void initAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setDuration(2000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "onAnimationUpdate: currentThread = " + Thread.currentThread().getName());
                updateElements();
                updateView();
            }
        });
    }

    private void updateView() {
        Log.d(TAG, "updateView: ");
        if (mDrawingThread != null) {
            mDrawingThread.run();
        } else {
            Log.d(TAG, "updateView: mDrawingThread is null");
        }
    }

    private void initElements() {
        Log.d(TAG, "initElements: ");
        for (int i = 0; i < mBitmap.getWidth() / mElementRadius; i++) {
            for (int j = 0; j < mBitmap.getHeight() / mElementRadius; j++) {
                SplitElement splitElement = new SplitElement(i, j);
                splitElement.color = mBitmap.getPixel(i * mElementRadius + mElementRadius / 2, j * mElementRadius + mElementRadius / 2);
                splitElement.vX = NumberUtils.rangeFloat(-20, 20);
                splitElement.vY = NumberUtils.rangeInt(-15, 35);
                splitElement.aX = 0;
                splitElement.aY = 0.98f;
                mSplitElements.add(splitElement);
            }
        }
    }

    private void updateElements() {
        Log.d(TAG, "updateElements: size = " + mSplitElements.size());
        for (SplitElement splitElement : mSplitElements) {
            splitElement.x += splitElement.vX;
            splitElement.y += splitElement.vY;
            splitElement.vX += splitElement.aX;
            splitElement.vY += splitElement.aY;
//            Log.d(TAG, splitElement.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "onTouchEvent: action_down");
            if (mValueAnimator != null && !mValueAnimator.isRunning()) {
                mValueAnimator.start();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: ");
        updateView();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed: ");
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
    }

    private class DrawThread extends Thread {
        @Override
        public void run() {
            Canvas canvas = null;
            try {
                canvas = mHolder.lockCanvas();
                mPaint.setColor(Color.WHITE);
                int width = getWidth();
                int height = getHeight();
                canvas.drawRect(0, 0, width, height, mPaint);
                canvas.translate((width - mBitmap.getWidth()) / 2f, (height - mBitmap.getHeight()) / 2f);
                for (SplitElement splitElement : mSplitElements) {
                    mPaint.setColor(splitElement.color);
                    canvas.drawCircle(splitElement.x * mElementRadius + mElementRadius / 2f, splitElement.y * mElementRadius + mElementRadius / 2f,
                        mElementRadius, mPaint);
                }
            } catch (Exception e) {
                Log.e(TAG, "run: ", e);
            } finally {
                mHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
