package com.daydayup.mydemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.daydayup.mydemo.activity.view.loading.CircleLoadingView;

/**
 *
 */
public class LoadingViewActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, LoadingViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CircleLoadingView(this));
    }
}
