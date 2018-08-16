package com.daydayup.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityAudioRecorderBinding;

import java.util.Random;

/**
 * Created by conan on 2017/12/12.
 *
 * @desc ${TODO}
 */

public class AudioRecorderActivity extends AppCompatActivity {

    private ActivityAudioRecorderBinding mAudioRecorderBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        mAudioRecorderBinding = DataBindingUtil.setContentView(AudioRecorderActivity.this, R.layout.activity_audio_recorder);
    }

    private void initEvent() {
        mAudioRecorderBinding.btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioRecorderBinding.recorderWaveView.startRecorder();
            }
        });
        mAudioRecorderBinding.btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioRecorderBinding.recorderWaveView.stopRecorder();
            }
        });
        mAudioRecorderBinding.btnEnhanceVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int voiceIncrease = random.nextInt(50);
                mAudioRecorderBinding.recorderWaveView.setMaxHeight(voiceIncrease);
            }
        });
        mAudioRecorderBinding.btnDecreaseVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int voiceIncrease = random.nextInt(50);
                mAudioRecorderBinding.recorderWaveView.setMaxHeight(-voiceIncrease);
            }
        });
        mAudioRecorderBinding.btnQuiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAudioRecorderBinding.recorderWaveView.resetMaxHeight();
            }
        });
        mAudioRecorderBinding.btnChangeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1000);
                mAudioRecorderBinding.recorderWaveView.setLayoutParams(layoutParams);
            }
        });
    }
}
