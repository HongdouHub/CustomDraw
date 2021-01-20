package com.example.chivas.customdraw.constants;

import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;

import java.util.HashMap;
import java.util.Map;

public class XferModeConstants {

    private XferModeConstants() {
        //
    }

    public static final String[] MODE_TYPE = new String[] {
            "Clear",    // 清除
            "Src",      // 保留src
            "Dst",      // 保留dst
            "SrcOver",  // src在上方
            "DstOver",  // dst在上方
            "SrcIn",    // 只显示相交处，并src在上方
            "DstIn",    // 只显示相交处，并dst在上方
            "SrcOut",   // 只显示非src的不相交dst区域
            "DstOut",   // 只显示非dst的不相交src区域
            "SrcATop",  // 显示src不相交区域 + 相交处以dst为准
            "DstATop",  // 显示dst不相交区域 + 相交处以src为准
            "Xor",      // 显示不相交的(src + dst)区域
            "Darken",   // 显示不相交的(src + dst)区域 + 相交处取颜色混合后的样式暗色
            "Lighten",  // 显示不相交的(src + dst)区域 + 相交处取颜色混合后的样式亮色
            "Multiply", // 相交处取颜色混合后的样式
            "Screen"    // 显示不相交的(src + dst)区域 + 相交处取背景颜色
    };

    public static final Map<String, Xfermode> MODE_MAP = new HashMap<String, Xfermode>() {{
        int i = 0;
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.SRC));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.DST));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.XOR));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        put(MODE_TYPE[i++], new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        put(MODE_TYPE[i], new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
    }};

}
