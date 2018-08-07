package com.daydayup.mydemo.util;

import android.widget.Toast;

import com.daydayup.mydemo.MyDemoApplication;

/**
 *
 * Created by conan on 2018/7/17.
 */

public class ToastUtils {

    public static void makeToast(String content) {
        Toast.makeText(MyDemoApplication.getContext(),
                content, Toast.LENGTH_SHORT).show();
    }
}
