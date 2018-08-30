package com.daydayup.mydemo.activity.game.puzzle.ui;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.activity.game.puzzle.adapter.PuzzleGameGvAdapter;
import com.daydayup.mydemo.databinding.ActivityPuzzleGameBinding;
import com.daydayup.mydemo.util.ImageUtils;

import java.util.List;

/**
 * $desc$
 * Created by conan on 2018/8/30.
 */

public class PuzzleGameActivity extends AppCompatActivity {

    private int mDifficulty;
    private int mImageId;
    private ActivityPuzzleGameBinding mPuzzleGameBinding;
    private Bitmap mOriginPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mDifficulty = getIntent().getIntExtra("difficulty", 3);
        mImageId = getIntent().getIntExtra("imageId", 0);
    }

    private void initView() {
        mPuzzleGameBinding = DataBindingUtil.setContentView(this, R.layout.activity_puzzle_game);
        if (mImageId != 0){
            mOriginPic = BitmapFactory.decodeResource(getResources(), mImageId);
            if (mOriginPic.getWidth() > 500){
                mOriginPic = ThumbnailUtils.extractThumbnail(mOriginPic,500,500);
            }
            // TODO: 2018/8/30 将图片处理成 1：1
            mPuzzleGameBinding.originPic.setImageBitmap(mOriginPic);
        }
        mPuzzleGameBinding.puzzlePicGrid.setNumColumns(mDifficulty);
        List<Bitmap> bitmaps = ImageUtils.splitPic2Part(PuzzleGameActivity.this, mOriginPic, mDifficulty);
        PuzzleGameGvAdapter puzzleGameGvAdapter = new PuzzleGameGvAdapter(PuzzleGameActivity.this,bitmaps,mDifficulty);
        mPuzzleGameBinding.puzzlePicGrid.setAdapter(puzzleGameGvAdapter);
        mPuzzleGameBinding.puzzlePicGrid.setVisibility(View.GONE);
        initAnimation();
    }

    private void initAnimation() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mPuzzleGameBinding.puzzlePicGrid.setVisibility(View.VISIBLE);
//                mPuzzleGameBinding.originPic.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
