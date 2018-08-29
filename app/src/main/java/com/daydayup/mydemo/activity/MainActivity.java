package com.daydayup.mydemo.activity;

import com.daydayup.mydemo.activity.game.GameHomeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListBaseActivity {

    @Override
    protected List<String> getListData() {
        List<String> demoList = new ArrayList<>();
        demoList.add("CAMERA_VIEW");
        demoList.add("AUDIO_RECORDER");
        demoList.add("VIEW");
        demoList.add("IMAGE_EFFECT");
        demoList.add("GAME");
        demoList.add("TEST");
        return demoList;
    }

    @Override
    protected Map<String, String> getStartParams(int position) {
        return null;
    }

    @Override
    protected Class getWantedStartActivity(int position) {
        switch (position){
            case 0: return MyCameraViewActivity.class;
            case 1: return AudioRecorderActivity.class;
            case 2: return ViewEffectActivity.class;
            case 3: return ImageEffectActivity.class;
            case 4: return GameHomeActivity.class;
            case 5: return TestActivity.class;
        }
        return null;
    }
}
