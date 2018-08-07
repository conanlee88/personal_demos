package com.daydayup.mydemo.util;

/**
 * Created by conan on 2017/12/12.
 *
 * @des ${TODO}
 */

public class ArrayUtils {

    /**
     * 获取数组中最大值的第一个索引（可能有多个索引的值都是最大值）
     * @param arr 要获取的最大值索引的数组
     * @return 数组中最大值的第一个索引
     */
    public static int getArrMaxValueFirstIndex(float[] arr){
        if (arr == null || arr.length == 0) return -1;
        float maxValue = arr[0];
        int maxValueIndex = 0;
        for (int i = 0;i < arr.length;i ++){
            if (arr[i] > maxValue) maxValueIndex = i;
        }
        return maxValueIndex;
    }
}
