package com.daydayup.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.SlideViewBinding;
import com.daydayup.mydemo.util.ToastUtils;

/**
 * $desc$
 * Created by conan on 2018/8/16.
 */

public class SlideLayoutActivity extends AppCompatActivity {

    private SlideViewBinding mSlideViewBinding;
    private float mStartX;
    private float mLastX;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mSlideViewBinding = DataBindingUtil.setContentView(this, R.layout.slide_view);
        mSlideViewBinding.contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.makeToast("点击了contentView");
            }
        });
    }
}
