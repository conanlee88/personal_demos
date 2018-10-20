package com.daydayup.mydemo.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityViewEffectBinding;

/**
 *
 * Created by conan on 2018/7/18.
 */

public class ViewEffectActivity extends AppCompatActivity {

    private ActivityViewEffectBinding mViewEffectBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();

        //测试
        //测试2
        //change master branch
        //change master branch2
        //测试3
        //测试4
        //change master branch3
        //change master branch4

    }

    private void initView() {
        mViewEffectBinding = DataBindingUtil.setContentView(ViewEffectActivity.this,
                R.layout.activity_view_effect);
    }

    private void initEvent() {
        mViewEffectBinding.fanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewEffectActivity.this,FanLayoutActivity.class);
                startActivity(intent);
            }
        });

        mViewEffectBinding.slideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewEffectActivity.this,SlideLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
