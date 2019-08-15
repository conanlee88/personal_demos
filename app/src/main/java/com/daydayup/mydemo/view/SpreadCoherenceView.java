package com.daydayup.mydemo.view;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.util.ToastUtils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by conan on 19-8-15
 * Describe: 旋转后向外扩散再内聚的动画
 */
public class SpreadCoherenceView extends View {

    private static final String TAG = "SpreadCoherenceView";

    private Paint mPaint;
    private int[] mBallColors;
    private int mBallRadius = 18;
    private float mCenterX;
    private float mCenterY;
    private int mInitDistance = 90;
    private int mDrawState = STATE_ROTATE;
    private Bitmap mContentBitmap;

    private AnimationState mRotateAnimation;
    private AnimationState mSpreadCoherenceAnimation;
    private AnimationState mWaterWaveAnimation;

    public SpreadCoherenceView(Context context) {
        this(context, null);
    }

    public SpreadCoherenceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpreadCoherenceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBallColors = new int[]{0xFF0AD4E0, 0xFF00C800, 0xFFFF534D, 0xFFFFBA00, 0xFF409EFF, 0xFF909399};
        mRotateAnimation = new RotateAnimation();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 3;
        mContentBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spread_coherence_content, options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = getMeasuredWidth() / 2f;
        mCenterY = getMeasuredHeight() / 2f;
    }

    private static final int STATE_ROTATE = 0;
    private static final int STATE_SPREAD_COHERENCE = 1;
    private static final int STATE_WATER_WAVE = 2;

    @Override
    protected void onDraw(Canvas canvas) {
        switch (mDrawState) {
            case STATE_ROTATE:
                mRotateAnimation.draw(canvas);
                break;
            case STATE_SPREAD_COHERENCE:
                mSpreadCoherenceAnimation.draw(canvas);
                break;
            case STATE_WATER_WAVE:
                mWaterWaveAnimation.draw(canvas);
                break;
        }
    }

    class RotateAnimation implements AnimationState {

        private ValueAnimator mValueAnimator;
        private float mRotateAngle;

        RotateAnimation() {
            mValueAnimator = ValueAnimator.ofFloat(0, 360);
            mValueAnimator.setDuration(1200);
//            mValueAnimator.setRepeatCount(2);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mRotateAngle = (float) animation.getAnimatedValue();
//                    Log.d(TAG, "onAnimationUpdate: value = " + animation.getAnimatedValue());
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ToastUtils.makeToast("旋转动画结束,开始扩散内聚动画");
                    mSpreadCoherenceAnimation = new SpreadCoherenceAnimation();
                    mDrawState = STATE_SPREAD_COHERENCE;
                }
            });
            mValueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            // FIXME: 19-8-15 不通过旋转画布来绘制球,而是直接计算球的圆心绘制
            canvas.rotate(mRotateAngle, mCenterX, mCenterY);
            canvas.translate(mCenterX, mCenterY);
            for (int ballColor : mBallColors) {
                mPaint.setColor(ballColor);
                canvas.drawCircle(mInitDistance, 0, mBallRadius, mPaint);
                canvas.rotate((float) (360 / mBallColors.length));
            }
        }
    }

    class SpreadCoherenceAnimation implements AnimationState {

        private ValueAnimator mValueAnimator;
        private float mDistance;

        SpreadCoherenceAnimation() {
            mValueAnimator = ValueAnimator.ofFloat(mBallRadius, mInitDistance);
            mValueAnimator.setDuration(1200);
            mValueAnimator.setInterpolator(new OvershootInterpolator(10f));
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mDistance = (float) animation.getAnimatedValue();
//                    Log.d(TAG, "onAnimationUpdate: value = " + animation.getAnimatedValue());
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ToastUtils.makeToast("扩散内聚动画结束,开始水波纹动画");
                    mWaterWaveAnimation = new WaterWaveAnimation();
                    mDrawState = STATE_WATER_WAVE;
                }
            });
            mValueAnimator.reverse();
        }

        @Override
        public void draw(Canvas canvas) {
            // FIXME: 19-8-15 不通过旋转画布来绘制球,而是直接计算球的圆心绘制
            canvas.translate(mCenterX, mCenterY);
            for (int ballColor : mBallColors) {
                mPaint.setColor(ballColor);
                canvas.drawCircle(mDistance, 0, mBallRadius, mPaint);
                canvas.rotate((float) (360 / mBallColors.length));
            }
        }
    }

    class WaterWaveAnimation implements AnimationState {

        private final ValueAnimator mValueAnimator;
        private float mWaveRadius;
        private PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        WaterWaveAnimation() {
            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
            double sqrt = Math.sqrt(width * width + height * height);
            Log.d(TAG, "WaterWaveAnimation: width = " + width);
            Log.d(TAG, "WaterWaveAnimation: height = " + height);
            Log.d(TAG, "WaterWaveAnimation: sqrt = " + sqrt);
            mValueAnimator = ValueAnimator.ofFloat(mBallRadius * 2, (float) sqrt / 2);
            mValueAnimator.setInterpolator(new LinearInterpolator());
            mValueAnimator.setDuration(1200);
            mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mWaveRadius = (float) animation.getAnimatedValue();
                    invalidate();
                }
            });
            mValueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ToastUtils.makeToast("动画结束");
                }
            });
            mValueAnimator.start();
        }

        @Override
        public void draw(Canvas canvas) {
//            int layer = canvas.saveLayer(mCenterX - mWaveRadius, mCenterY - mWaveRadius, mCenterX + mWaveRadius, mCenterY + mWaveRadius, mPaint);
            int layer = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
            //先绘制的是dst,而后才是src
            canvas.drawCircle(mCenterX, mCenterY, mWaveRadius, mPaint);
            mPaint.setXfermode(mXfermode);
            canvas.drawBitmap(mContentBitmap, 0, 0, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(layer);
        }
    }

    private interface AnimationState {
        void draw(Canvas canvas);
    }
}
