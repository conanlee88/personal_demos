package com.daydayup.mydemo.activity.view.splitview;

/**
 * Created by conan on 19-8-9
 * Describe:
 */
public class SplitElement {

    public int x;
    public int y;
    public int color;

    public float vX;
    public float vY;
    public float aX;
    public float aY;

    public SplitElement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "SplitElement{" +
            "x=" + x +
            ", y=" + y +
            ", color=" + color +
            ", vX=" + vX +
            ", vY=" + vY +
            ", aX=" + aX +
            ", aY=" + aY +
            '}';
    }
}
