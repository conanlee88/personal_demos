package com.daydayup.mydemo.activity.game;

import com.daydayup.mydemo.activity.ListBaseActivity;
import com.daydayup.mydemo.activity.game.puzzle.ui.PuzzleMainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * $desc$
 * Created by conan on 2018/8/29.
 */

public class GameHomeActivity extends ListBaseActivity{

    @Override
    protected List<String> getListData() {
        List<String> gameList = new ArrayList<>();
        gameList.add("PUZZLE");
        return gameList;
    }

    @Override
    protected Map<String, String> getStartParams(int position) {
        return null;
    }

    @Override
    protected Class getWantedStartActivity(int position) {
        switch (position){
            case 0:
                return PuzzleMainActivity.class;
        }
        return null;
    }
}
