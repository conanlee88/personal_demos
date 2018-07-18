package com.daydayup.mydemo;

import android.app.Application;

/**
 * Created by conan on 2018/7/17.
 * © 2017 Dafy Inc All Rights Reserved
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
}