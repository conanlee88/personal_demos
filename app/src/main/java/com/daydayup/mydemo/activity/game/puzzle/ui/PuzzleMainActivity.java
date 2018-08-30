package com.daydayup.mydemo.activity.game.puzzle.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.daydayup.mydemo.R;
import com.daydayup.mydemo.activity.game.puzzle.adapter.PuzzleImageGvAdapter;
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
    private int mDifficulty = 2;
    private int[] mPuzzleImageIds;
    private List<Bitmap> mPuzzleImages;

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

        mPuzzleImageIds = new int[]{
                R.drawable.puzzle01,
                R.drawable.puzzle02,
                R.drawable.puzzle03,
                R.drawable.puzzle04,
                R.drawable.puzzle05,
                R.drawable.puzzle06,
                R.drawable.puzzle07,
                R.drawable.puzzle08,
                R.drawable.puzzle09,
                R.drawable.puzzle10,
                R.drawable.puzzle11,
                R.drawable.puzzle12,
                R.drawable.puzzle13,
                R.drawable.puzzle14,
                R.drawable.puzzle15,
                R.drawable.puzzle16,
                R.drawable.puzzle17,
                R.drawable.puzzle18,
                R.drawable.puzzle19,
                R.drawable.puzzle20,
                R.drawable.puzzle21,
                R.drawable.puzzle22,
                R.drawable.puzzle23,
                R.drawable.puzzle24,
                R.drawable.puzzle25,
                R.drawable.puzzle26,
                R.drawable.puzzle27,
                R.drawable.puzzle28,
                R.drawable.puzzle29,
                R.drawable.puzzle30,
                R.drawable.puzzle31,
                R.drawable.puzzle32,
                R.drawable.puzzle33,
                R.drawable.puzzle34,
                R.drawable.puzzle35,
                R.drawable.puzzle36,
                R.drawable.puzzle37,
                R.drawable.puzzle38,
                R.drawable.puzzle39,
                R.drawable.puzzle40,
                R.drawable.puzzle41
        };

        mPuzzleImages = new ArrayList<>();
        for (int imageId : mPuzzleImageIds){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inScaled = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageId, options);
            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 200, 200);
            mPuzzleImages.add(thumbnail);
        }
    }

    private void initView() {
        mPuzzleMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_puzzle_main);
        PuzzleImageGvAdapter puzzleImageGvAdapter = new PuzzleImageGvAdapter(PuzzleMainActivity.this,mPuzzleImages);
        mPuzzleMainBinding.imageGridView.setAdapter(puzzleImageGvAdapter);
    }

    private void initEvent() {
        mPuzzleMainBinding.puzzleDifficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float screenDensity = ScreenUtils.getScreenDensity();
                List<Object> objects = PopFactory.createPop(PuzzleMainActivity.this,
                        (int) (200 * screenDensity),
                        0,
                        "请选择难度",
                        mDifficultyTitle);
                final PopupWindow difficultyPop = (PopupWindow) objects.get(0);
                difficultyPop.showAtLocation(v, Gravity.CENTER, 0, 0);

                ListView popListView = (ListView) objects.get(1);
                popListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mPuzzleMainBinding.puzzleDifficulty.setText(String.format("难度选择：%s",mDifficultyTitle.get(position)));
                        difficultyPop.dismiss();
                        //记录下几阶
                        mDifficulty = position + 2;
                    }
                });
            }
        });

        mPuzzleMainBinding.imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PuzzleMainActivity.this, PuzzleGameActivity.class);
                intent.putExtra("difficulty",mDifficulty);
                intent.putExtra("imageId",mPuzzleImageIds[position]);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(
                                PuzzleMainActivity.this,
                                view,
                                "share"
                        ).toBundle());
            }
        });
    }
}
