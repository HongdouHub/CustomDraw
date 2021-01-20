package com.example.chivas.customdraw.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class MultiTextUtils {

    private MultiTextUtils() {
        //
    }

    public static void printText(String text, Canvas canvas, TextPaint paint, int startX, int startY) {

        String[] strings;
        if (text.contains("\n")) {
            strings = text.split("\n");
        } else {
            strings = new String[] {text};
        }

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float currentX = startX;
        float currentY = startY;
        float offsetY = fontMetrics.descent - fontMetrics.ascent;

        for (String s : strings) {
            canvas.drawText(s, currentX, currentY, paint);
            currentY += offsetY;
        }
    }

    public static void printText(String text, Canvas canvas, TextPaint paint, int startX, int startY, int width) {
        StaticLayout staticLayout = new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL,
                1f, 0f, true);
        // API 23以上
//        StaticLayout staticLayout = StaticLayout.Builder.obtain(text, 0, text.length(), mTextPaint, 13).build();
        canvas.save();
        canvas.translate(startX, startY);
        staticLayout.draw(canvas);
        canvas.restore();
    }

}
