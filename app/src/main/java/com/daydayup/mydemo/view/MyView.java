package com.daydayup.mydemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import static com.daydayup.mydemo.consts.Consts.LOG_TAG;

/**
 * $desc$
 * Created by conan on 2018/8/14.
 */

public class MyView extends View {

    private float mStartX;
    private float mStartY;
    private float mStartRawX;
    private float mStartRawY;
    private float mPivotX;
    private float mPivotY;
    private int mDRawX;
    private int mDRawY;
    private Scroller mScroller;

    public MyView(Context context) {
        this(context,null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---onTouchEvent");
        float x = event.getX();
        float y = event.getY();
        float rawX = event.getRawX();
        float rawY = event.getRawY();
//        Log.v("conan88",this.getClass().getSimpleName() + "---x = " + x);
//        Log.v("conan88",this.getClass().getSimpleName() + "---y = " + y);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.v(LOG_TAG,this.getClass().getSimpleName() + "---ACTION_DOWN");
                mStartX = x;
                mStartY = y;
                mStartRawX = rawX;
                mStartRawY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v(LOG_TAG,this.getClass().getSimpleName() + "---ACTION_MOVE");
                int dX = (int) (x - mStartX);
                int dY = (int) (y - mStartY);
                mDRawX = (int) (rawX - mStartRawX);
                mDRawY = (int) (rawY - mStartRawY);
                //移动view的方法
                //1.通过layout方法
//                layout(getLeft() + dX,getTop() + dY,getRight() + dX,getBottom() + dY);
                //2.通过offsetLeftAndRight和offsetTopAndBottom方法
//                offsetLeftAndRight(dX);
//                offsetTopAndBottom(dY);
                //3.通过LayoutParams 或 MarginLayoutParams,父布局需支持margin属性，例如LinearLayout和RelativeLayout才可用
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + dX;
//                layoutParams.topMargin = getTop() + dY;
//                =======================================================
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + dX;
//                layoutParams.topMargin = getTop() + dY;
//                setLayoutParams(layoutParams);
                //4.通过scrollTo和scrollBy方法（移动的是屏幕，而不是view的位置）
                View parent = (View) getParent();
//                parent.scrollBy(-dX,-dY);
                //scrollTo要加上之前便宜后的坐标原点
                parent.scrollTo(-mDRawX + (int)mPivotX, -mDRawY + (int)mPivotY);
                break;
            case MotionEvent.ACTION_UP:
                Log.v(LOG_TAG,this.getClass().getSimpleName() + "---ACTION_UP");
                //在使用scrollTo来移动的时候，就要记录坐标的偏移量
//                mPivotX -= mDRawX;
//                mPivotY -= mDRawY;
                //使用scroller来将view复位（与scrollTo和scrollBy一样都是移动屏幕，而不是view的位置）
                mScroller.startScroll(-mDRawX,-mDRawY,mDRawX,mDRawY,500);
                //下面的复位方法是通用的，不用计算偏移距离，直接调用viewGroup的getScrollX和getScrollY方法获取
//                ViewGroup viewGroup = (ViewGroup) getParent();
//                mScroller.startScroll(viewGroup.getScrollX(),viewGroup.getScrollY(),
//                        -viewGroup.getScrollX(),-viewGroup.getScrollY());
                computeScroll();
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
