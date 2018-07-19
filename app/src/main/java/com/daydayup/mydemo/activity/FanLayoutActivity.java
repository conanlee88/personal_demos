package com.daydayup.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.FanLayoutActivityBinding;
import com.daydayup.mydemo.util.ToastUtils;

/**
 * Created by conan on 2018/7/18.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class FanLayoutActivity extends AppCompatActivity{

    private FanLayoutActivityBinding mFanLayoutActivityBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        mFanLayoutActivityBinding = DataBindingUtil.setContentView(FanLayoutActivity.this, R.layout.fan_layout_activity);
    }

    private void initEvent() {
        mFanLayoutActivityBinding.firstView.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.makeToast("点击了firstView");
            }
        });
    }
}
