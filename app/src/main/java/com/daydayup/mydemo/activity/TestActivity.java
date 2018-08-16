package com.daydayup.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityTestBinding;

/**
 * $desc$
 * Created by conan on 2018/8/14.
 */

public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBinding testBinding = DataBindingUtil.setContentView(this,R.layout.activity_test);
//        testBinding.myView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}
