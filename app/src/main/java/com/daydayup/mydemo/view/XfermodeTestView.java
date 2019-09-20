package com.daydayup.mydemo.view;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.activity.view.xfermode.XfermodeStr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by conan on 19-8-15
 * Describe:
 */
public class XfermodeTestView extends View {

    private static final String TAG = "XfermodeTestView";

    private Paint mPaint;
    private Paint mTextPaint;
    private int w = 300;
    private int h = w;
    private int marginTop = 120;
    private int marginLeft = 30;
    private Bitmap mRectBitmap;
    private Bitmap mCircleBitmap;
    private PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
    private String mModeName = XfermodeStr.MULTIPLY.getName();

    public XfermodeTestView(Context context) {
        this(context, null);
    }

    public XfermodeTestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XfermodeTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.WHITE);
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(45);
        mTextPaint.setColor(getResources().getColor(R.color.colorBlack));
        makeRect();
        makeCircle();
    }

    public void setXfermode(String modeName) {
        this.mModeName = modeName;
        this.mXfermode = XfermodeStr.getXfermode(modeName);
        mPaint.reset();
        makeRect();
        makeCircle();
        invalidate();
    }

    private void makeRect() {
        mRectBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mRectBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int rectColor = 0xFF0AD4E0;
        paint.setColor(rectColor);
        canvas.drawRect(w / 3f, h / 3f, w, h, paint);
    }

    private void makeCircle() {
        mCircleBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mCircleBitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int circleColor = 0xFFFF534D;
        paint.setColor(circleColor);
        float radius = w / 3f;
        canvas.drawCircle(w / 3f, h / 3f, radius, paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOriginAndEffect(canvas, false);
        drawOriginAndEffect(canvas, true);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        String msg = "当前模式为: " + mModeName;
        Rect rect = new Rect();
        mTextPaint.getTextBounds(msg, 0, msg.length(), rect);
        canvas.save();
        canvas.translate(marginLeft, -rect.top);
        canvas.drawText(msg, 0, msg.length(), mTextPaint);
        canvas.translate(0, rect.top);
        msg = "应用模式后的效果: ";
        mTextPaint.getTextBounds(msg, 0, msg.length(), rect);
        canvas.translate(0, marginTop + h - rect.top + 30);
        canvas.drawText(msg, 0, msg.length(), mTextPaint);
        canvas.restore();
    }

    /**
     * 1.先绘制的为dst,后绘制的为src
     * 2.设置重叠模式的两张图应该尺寸是一样的,才能获取到正确的模式
     */

    private void drawOriginAndEffect(Canvas canvas, boolean reverse) {
        int width = getMeasuredWidth();
        String src = "SRC";
        String dst = "DST";
        // 绘制应用图层叠加模式前的效果
        canvas.save();
        canvas.translate((reverse ? width * 3 : width) / 4f - w / 2f, marginTop);
        canvas.drawBitmap(reverse ? mRectBitmap : mCircleBitmap, 0, 0, mPaint);
        drawTip(canvas, dst + (reverse ? " --- reverse" : ""));
        canvas.save();
        canvas.drawBitmap(reverse ? mCircleBitmap : mRectBitmap, 0, 0, mPaint);
        canvas.translate(w / 3f, w / 3f);
        drawTip(canvas, src + (reverse ? " --- reverse" : ""));
        canvas.restore();
        // 绘制应用图层叠加模式后的效果
        canvas.translate(0, marginTop + h);
        canvas.drawBitmap(reverse ? mRectBitmap : mCircleBitmap, 0, 0, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(reverse ? mCircleBitmap : mRectBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
//        canvas.translate(w / 3f, w / 3f);
        canvas.restore();
    }

    private void drawTip(Canvas canvas, String tip) {
        canvas.save();
        Rect srcRect = new Rect();
        mTextPaint.getTextBounds(tip, 0, tip.length(), srcRect);
        canvas.translate(w / 6f, h / 6f - srcRect.top);
        canvas.drawText(tip, 0, 3, mTextPaint);
        canvas.restore();
    }
}
