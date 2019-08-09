package com.daydayup.mydemo.activity;

import com.daydayup.mydemo.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by conan on 19-8-9
 * Describe:
 */
public class SplitViewActivity extends Activity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SplitViewActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_view);
    }
}
