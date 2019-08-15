package com.daydayup.mydemo.activity;

import com.daydayup.mydemo.view.SpreadCoherenceView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by conan on 19-8-15
 * Describe:
 */
public class SpreadCoherenceActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, SpreadCoherenceActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SpreadCoherenceView(this));
    }
}
