package com.daydayup.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daydayup.mydemo.R;

/**
 * Created by conan on 2018/7/18.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class FanLayoutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        DataBindingUtil.setContentView(FanLayoutActivity.this, R.layout.fan_layout_activity);
    }
}
