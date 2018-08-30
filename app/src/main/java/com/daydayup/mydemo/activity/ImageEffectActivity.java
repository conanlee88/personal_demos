package com.daydayup.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityImageEffectBinding;
import com.daydayup.mydemo.util.ImageUtils;

/**
 * $desc$
 * Created by conan on 2018/8/17.
 */

public class ImageEffectActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final int MID_VALUE = 50;
    private ActivityImageEffectBinding mImageEffectBinding;
    private float mHub = 0f;
    private float mBrightness = 1f;
    private float mSaturation = 1f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageEffectBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_effect);
        initEvent();
    }

    private void initEvent() {
        mImageEffectBinding.hue.setOnSeekBarChangeListener(this);
        mImageEffectBinding.saturability.setOnSeekBarChangeListener(this);
        mImageEffectBinding.brightness.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.hue:
                mHub = (progress - MID_VALUE) * 1f / MID_VALUE * 180;
                break;
            case R.id.saturability:
                mSaturation = progress * 1f / MID_VALUE;
                break;
            case R.id.brightness:
                mBrightness = progress * 1f / MID_VALUE;
                break;
        }
        showImageEffect();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
//        showImageEffect();
    }

    private void showImageEffect() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.timg);
        Bitmap imageEffect = ImageUtils.handleImageEffect(bitmap, mHub, mSaturation, mBrightness);
        mImageEffectBinding.imageView.setImageBitmap(imageEffect);
    }
}
