package com.daydayup.mydemo.activity.game.puzzle;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.databinding.ActivityPuzzleMainBinding;
import com.daydayup.mydemo.factory.PopFactory;
import com.daydayup.mydemo.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc$
 * Created by conan on 2018/8/29.
 */

public class PuzzleMainActivity extends AppCompatActivity{

    private ActivityPuzzleMainBinding mPuzzleMainBinding;
    private List<String> mDifficultyTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        mDifficultyTitle = new ArrayList<>();
        mDifficultyTitle.add("2阶");
        mDifficultyTitle.add("3阶");
        mDifficultyTitle.add("4阶");
    }

    private void initView() {
        mPuzzleMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_puzzle_main);
    }

    private void initEvent() {
        mPuzzleMainBinding.puzzleDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float screenDensity = ScreenUtils.getScreenDensity();
                // TODO: 2018/8/29 无法再匿名内部类的匿名内部类中使用外层变量
                PopupWindow difficultyPop = PopFactory.createPop(PuzzleMainActivity.this,
                        (int) (200 * screenDensity),
                        0,
                        "请选择难度",
                        mDifficultyTitle,
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                mPuzzleMainBinding.puzzleDifficulty.setText(mDifficultyTitle.get(position));
                            }
                        });
                difficultyPop.showAtLocation(v, Gravity.CENTER, 0, 0);
            }
        });
    }
}
