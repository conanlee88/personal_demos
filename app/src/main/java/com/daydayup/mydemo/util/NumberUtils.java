package com.daydayup.mydemo.util;

/**
 * Created by conan on 19-8-13
 * Describe:
 */
public class NumberUtils {

    public static int rangeInt(int x, int y) {
        int min = Math.min(x, y) - 1;
        int max = Math.max(x, y);
        return (int) (min + (Math.ceil(Math.random() * (max - min))));
    }

    public static float rangeFloat(int x, int y) {
        int min = Math.min(x, y) - 1;
        int max = Math.max(x, y);
        return (float) (min + Math.random() * (max - min));
    }
}
