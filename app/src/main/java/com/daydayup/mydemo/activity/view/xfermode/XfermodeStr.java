package com.daydayup.mydemo.activity.view.xfermode;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by conan on 19-8-16
 * Describe:
 */
public enum XfermodeStr {

    CLEAR("CLEAR", new PorterDuffXfermode(PorterDuff.Mode.CLEAR)),
    SRC("SRC", new PorterDuffXfermode(PorterDuff.Mode.SRC)),
    DST("DST", new PorterDuffXfermode(PorterDuff.Mode.DST)),
    SRC_OVER("SRC_OVER", new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)),
    DST_OVER("DST_OVER", new PorterDuffXfermode(PorterDuff.Mode.DST_OVER)),
    SRC_IN("SRC_IN", new PorterDuffXfermode(PorterDuff.Mode.SRC_IN)),
    DST_IN("DST_IN", new PorterDuffXfermode(PorterDuff.Mode.DST_IN)),
    SRC_OUT("SRC_OUT", new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)),
    DST_OUT("DST_OUT", new PorterDuffXfermode(PorterDuff.Mode.DST_OUT)),
    SRC_ATOP("SRC_ATOP", new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)),
    DST_ATOP("DST_ATOP", new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP)),
    XOR("XOR", new PorterDuffXfermode(PorterDuff.Mode.XOR)),
    DARKEN("DARKEN", new PorterDuffXfermode(PorterDuff.Mode.DARKEN)),
    LIGHTEN("LIGHTEN", new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN)),
    MULTIPLY("MULTIPLY", new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY)),
    SCREEN("SCREEN", new PorterDuffXfermode(PorterDuff.Mode.SCREEN)),
    ADD("ADD", new PorterDuffXfermode(PorterDuff.Mode.ADD)),
    OVERLAY("OVERLAY", new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));

    private String name;
    private PorterDuffXfermode mXfermode;

    XfermodeStr(String name, PorterDuffXfermode xfermode) {
        this.name = name;
        this.mXfermode = xfermode;
    }

    public String getName() {
        return name;
    }

    public PorterDuffXfermode getXfermode() {
        return mXfermode;
    }

    private static Map<String, PorterDuffXfermode> sDuffXfermodeMap;

    static {
        sDuffXfermodeMap = new HashMap<>();
        sDuffXfermodeMap.put(CLEAR.name, CLEAR.mXfermode);
        sDuffXfermodeMap.put(SRC.name, SRC.mXfermode);
        sDuffXfermodeMap.put(DST.name, DST.mXfermode);
        sDuffXfermodeMap.put(SRC_OVER.name, SRC_OVER.mXfermode);
        sDuffXfermodeMap.put(DST_OVER.name, DST_OVER.mXfermode);
        sDuffXfermodeMap.put(SRC_IN.name, SRC_IN.mXfermode);
        sDuffXfermodeMap.put(DST_IN.name, DST_IN.mXfermode);
        sDuffXfermodeMap.put(SRC_OUT.name, SRC_OUT.mXfermode);
        sDuffXfermodeMap.put(DST_OUT.name, DST_OUT.mXfermode);
        sDuffXfermodeMap.put(SRC_ATOP.name, SRC_ATOP.mXfermode);
        sDuffXfermodeMap.put(DST_ATOP.name, DST_ATOP.mXfermode);
        sDuffXfermodeMap.put(XOR.name, XOR.mXfermode);
        sDuffXfermodeMap.put(DARKEN.name, DARKEN.mXfermode);
        sDuffXfermodeMap.put(LIGHTEN.name, LIGHTEN.mXfermode);
        sDuffXfermodeMap.put(MULTIPLY.name, MULTIPLY.mXfermode);
        sDuffXfermodeMap.put(SCREEN.name, SCREEN.mXfermode);
        sDuffXfermodeMap.put(ADD.name, ADD.mXfermode);
        sDuffXfermodeMap.put(OVERLAY.name, OVERLAY.mXfermode);
    }

    public static PorterDuffXfermode getXfermode(String modeName) {
        return sDuffXfermodeMap.get(modeName);
    }

}
