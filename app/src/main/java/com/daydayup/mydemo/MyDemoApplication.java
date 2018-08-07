package com.daydayup.mydemo;

import android.app.Application;
import android.content.Context;

/**
 *
 * Created by conan on 2018/7/17.
 */

public class MyDemoApplication extends Application {

    private static MyDemoApplication sMyDemoApplication;

    public static MyDemoApplication getMyDemoApplication(){
        return sMyDemoApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sMyDemoApplication = this;
    }

    public static Context getContext(){
        return getMyDemoApplication().getApplicationContext();
    }
}
