package com.daydayup.mydemo.activity.game.puzzle.bean;

import android.graphics.Bitmap;

/**
 * $desc$
 * Created by conan on 2018/8/30.
 */

public class PuzzleItemBean {

    private int itemPosition;

    private int bitmapPosition;

    private Bitmap itemBitmap;

    public PuzzleItemBean(){

    }

    public PuzzleItemBean(int itemPosition,int bitmapPosition,Bitmap itemBitmap){
        this.itemPosition = itemPosition;
        this.bitmapPosition = bitmapPosition;
        this.itemBitmap = itemBitmap;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public int getBitmapPosition() {
        return bitmapPosition;
    }

    public void setBitmapPosition(int bitmapPosition) {
        this.bitmapPosition = bitmapPosition;
    }

    public Bitmap getItemBitmap() {
        return itemBitmap;
    }

    public void setItemBitmap(Bitmap itemBitmap) {
        this.itemBitmap = itemBitmap;
    }
}
