package com.example.chivas.customdraw.constants;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenConstants {

    private ScreenConstants() {
        //
    }

    public static void init(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = windowManager.getDefaultDisplay();
        defaultDisplay.getMetrics(displayMetrics);

        SCREEN_WIDTH = displayMetrics.widthPixels;
        SCREEN_HEIGHT = displayMetrics.heightPixels;
        DENSITY = displayMetrics.density;
    }

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static float DENSITY;

}
