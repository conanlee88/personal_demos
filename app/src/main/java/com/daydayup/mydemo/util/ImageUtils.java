package com.daydayup.mydemo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * $desc$
 * Created by conan on 2018/8/17.
 */

public class ImageUtils {

    /**
     * 处理图片的色度，饱和度及明度
     * @param bitmap 要处理的图片
     * @param hue 色度
     * @param saturation 饱和度
     * @param brightness 明度
     * @return 处理后的图片
     */
    public static Bitmap handleImageEffect(Bitmap bitmap,float hue,float saturation,float brightness){
        Bitmap bt = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bt);
        Paint paint = new Paint();

        ColorMatrix hueMatrix = new ColorMatrix();
        hueMatrix.setRotate(0,hue);
        hueMatrix.setRotate(1,hue);
        hueMatrix.setRotate(2,hue);

        ColorMatrix saturationMatrix = new ColorMatrix();
        saturationMatrix.setSaturation(saturation);

        ColorMatrix brightnessMatrix = new ColorMatrix();
        brightnessMatrix.setScale(brightness,brightness,brightness,1);

        ColorMatrix imageMatrix = new ColorMatrix();
        imageMatrix.postConcat(hueMatrix);
        imageMatrix.postConcat(saturationMatrix);
        imageMatrix.postConcat(brightnessMatrix);

        paint.setColorFilter(new ColorMatrixColorFilter(imageMatrix));
        canvas.drawBitmap(bitmap,0,0,paint);
        return bt;
    }

    /**
     * 把图片分割成 n * n 等份
     * @param context 上下文对象
     * @param image 要分割的图片
     * @param columnNum 要分割成n等份
     * @return 分割后的图片集合
     */
    public static List<Bitmap> splitPic2Part(Context context,Bitmap image,int columnNum){
//        if (columnNum == 0) columnNum = 3;
        List<Bitmap> bitmaps = new ArrayList<>();
        int itemWidth = image.getWidth() / columnNum;
        int itemHeight = image.getHeight() / columnNum;
        for (int i = 0; i < columnNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                Bitmap bitmap = Bitmap.createBitmap(image,
                        j * itemWidth,
                        i * itemHeight,
                        itemWidth,
                        itemHeight);
                bitmaps.add(bitmap);
            }
        }
        return bitmaps;
    }
}
