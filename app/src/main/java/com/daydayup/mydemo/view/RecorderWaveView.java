package com.daydayup.mydemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.util.ArrayUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by conan on 2017/12/12.
 *
 * @des ${TODO}
 */

public class RecorderWaveView extends View {

    private Paint mPaint;

    private int mWaveWidth = 4;//柱形宽度
    private int mWaveInterval = 8;//柱形间隔
    private float[] mCurrentHeight;//柱形的高度
    private boolean[] mIsIncrease;//柱形高度是递增还是递减

    private float mMaxHeight = 0;
    private float mMinHeight = 16;
    private Timer mTimer;
    private int mWaveCount;
    private float mHeightChangeRate = 1 / 12f;
    private int mHalfWavePeriod;

    public RecorderWaveView(Context context) {
        this(context, null);
    }

    public RecorderWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecorderWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
    }

    public void startRecorder() {
        if (mTimer == null) mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //间隔20ms刷新一次，并延后50ms执行
                postInvalidate();
            }
        }, 20, 20);
    }

    public void stopRecorder() {
        mTimer.cancel();
        mTimer = null;
    }

    public void resetMaxHeight() {
        this.mMaxHeight = 40;
    }

    public void setMaxHeight(float maxHeightOffset) {
        stopRecorder();
        this.mMaxHeight *= 3;
        int arrMaxValueFirstIndex = ArrayUtils.getArrMaxValueFirstIndex(mCurrentHeight);
        initHeight(0);
        startRecorder();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMaxHeight == 0) {
            int widthSize = MeasureSpec.getSize(widthMeasureSpec);
            //获取柱形个数
            mWaveCount = widthSize / (mWaveWidth + mWaveInterval) + 1;
            //半个周期的柱形个数（取周期个数为2）
            mHalfWavePeriod = mWaveCount / 4;
            //初始化波浪高度,并控制其不小于50
//            mMaxHeight = MeasureSpec.getSize(heightMeasureSpec) / 4f;
            if (mMaxHeight < 120) mMaxHeight = 120;
            mCurrentHeight = new float[mWaveCount];
            mIsIncrease = new boolean[mWaveCount];
            initHeight(0);
        }
    }

    /**
     * 初始化各个柱形的初始高度，及其递增递减状态
     * @param startIndex 第一个柱形的最大高度的索引
     */
    private void initHeight(int startIndex) {
        //高度变化度
        float heightOffset = (mMaxHeight - mMinHeight) / mHalfWavePeriod;
        for (int index = 0; index < mWaveCount; index++) {
            if ((index >= 0 && index < startIndex - mHalfWavePeriod)
                    ||(index >= startIndex && index < mHalfWavePeriod + startIndex)
                    || (index >= mHalfWavePeriod * 2 + startIndex && index < mHalfWavePeriod * 3 + startIndex)
                    || index >= mHalfWavePeriod * 4 + startIndex) {
                mCurrentHeight[index] = mMaxHeight - (index % mHalfWavePeriod) * heightOffset;
                mIsIncrease[index] = true;
            } else {
                mCurrentHeight[index] = (index % mHalfWavePeriod) * heightOffset + mMinHeight;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float viewMiddleHeight = getHeight() / 2f;
        float offsetHeight = (mMaxHeight - mMinHeight) * mHeightChangeRate;
        for (int index = 0; index < mWaveCount; index++) {
            int startHorizontalPosition = index * (mWaveWidth + mWaveInterval);
            canvas.drawRect(startHorizontalPosition + mWaveInterval / 2,
                    viewMiddleHeight - mCurrentHeight[index] / 2,
                    startHorizontalPosition + mWaveWidth + mWaveInterval / 2,
                    viewMiddleHeight + mCurrentHeight[index] / 2, mPaint);
            //todo 做正弦变化
            if (mIsIncrease[index]) {
                mCurrentHeight[index] += offsetHeight;
            } else {
                mCurrentHeight[index] -= offsetHeight;
            }
            if (mCurrentHeight[index] > mMaxHeight) {
                mIsIncrease[index] = false;
            } else if (mCurrentHeight[index] < mMinHeight) {
                mIsIncrease[index] = true;
            }
        }
    }
}
