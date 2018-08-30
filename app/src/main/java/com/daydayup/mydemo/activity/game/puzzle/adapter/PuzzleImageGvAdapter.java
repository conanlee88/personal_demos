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

import static com.daydayup.mydemo.consts.Consts.LOG_TAG;

/**
 * $desc$
 * Created by conan on 2018/8/30.
 */

public class PuzzleImageGvAdapter extends BaseAdapter{

    private Context mContext;
    private List<Bitmap> mImages;

    public PuzzleImageGvAdapter(Context context, List<Bitmap> images){
        this.mContext = context;
        this.mImages = images;
    }

    @Override
    public int getCount() {
        return mImages == null ? 0 : mImages.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null){
            imageView = new ImageView(mContext);
            int screenDensity = (int) ScreenUtils.getScreenDensity();
            GridView.LayoutParams layoutParams = new GridView.LayoutParams(80 * screenDensity, 85 * screenDensity);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,5 * screenDensity);
        }else {
            imageView = (ImageView) convertView;
        }
        Bitmap bitmap = mImages.get(position);
        Log.v(LOG_TAG,this.getClass().getSimpleName() + "---bitmap = " + bitmap.getWidth());
        imageView.setImageBitmap(bitmap);
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
