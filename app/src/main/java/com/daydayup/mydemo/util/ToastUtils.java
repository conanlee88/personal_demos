package com.daydayup.mydemo.util;

import android.widget.Toast;

import com.daydayup.mydemo.MyDemoApplication;

/**
 * Created by conan on 2018/7/17.
 * © 2017 Dafy Inc All Rights Reserved
 */

public class ToastUtils {

    public static void makeToast(String content) {
        Toast.makeText(MyDemoApplication.getMyDemoApplication(),
                content, Toast.LENGTH_SHORT).show();
    }
}
