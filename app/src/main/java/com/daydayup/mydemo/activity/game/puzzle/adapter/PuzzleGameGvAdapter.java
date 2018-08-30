package com.daydayup.mydemo.activity.game.puzzle.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.daydayup.mydemo.util.ScreenUtils;

import java.util.List;
import java.util.Random;

import static com.daydayup.mydemo.consts.Consts.LOG_TAG;

/**
 * $desc$
 * Created by conan on 2018/8/30.
 */

public class PuzzleGameGvAdapter extends BaseAdapter{

    private List<Bitmap> mBitmaps;
    private Context mContext;
    private final int mScreenDensity;
    private int mSize;
    private final int mBlankItem;

    public PuzzleGameGvAdapter(Context context, List<Bitmap> bitmaps,int columnNum){
        this.mContext = context;
        this.mBitmaps = bitmaps;
        mScreenDensity = (int) ScreenUtils.getScreenDensity();
        this.mSize = 300 / columnNum;
        Random random = new Random();
        mBlankItem = random.nextInt(columnNum * columnNum);
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---mBlankItem = " + mBlankItem);
    }

    @Override
    public int getCount() {
        return mBitmaps == null ? 0 : mBitmaps.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(mSize * mScreenDensity,mSize * mScreenDensity));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            imageView.setPadding(2 * mScreenDensity,2 * mScreenDensity,2 * mScreenDensity,2 * mScreenDensity);
        }else imageView = (ImageView) convertView;
        imageView.setImageBitmap(mBitmaps.get(mBitmaps.size() - position - 1));
//        if (position != mBlankItem){
//            imageView.setImageAlpha(255);
//        }else {
//            imageView.setImageAlpha(0);
//        }
        return imageView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
