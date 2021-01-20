package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static com.example.chivas.customdraw.constants.LogConstants.DRAW_VIEW_TAG;

public class BitmapAdapter13 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        mPaint.setStyle(Paint.Style.STROKE);

        // 画圆
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(200, 200, 100, mPaint);
        canvas.drawCircle(500, 200, 100, mPaint);
        canvas.drawCircle(200, 500, 100, mPaint);
        canvas.drawCircle(500, 500, 100, mPaint);

        // 画线
        mPaint.setStrokeWidth(1);
        canvas.drawLine(100, 200, 600, 200, mPaint);
        canvas.drawLine(100, 500, 600, 500, mPaint);
        canvas.drawLine(200, 100, 200, 600, mPaint);
        canvas.drawLine(500, 100, 500, 600, mPaint);

//        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);
        Rect rect = new Rect();
        mPaint.getTextBounds("aajaa1", 0, 6, rect);
        float offset = (rect.top + rect.bottom) / 2;

        Log.d(DRAW_VIEW_TAG, "BitmapAdapter13: offset = " + offset);

//        canvas.drawText("abjtg", 200, 200, mPaint);
        canvas.drawText("aajaa1", 200, 200 - offset, mPaint);

        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        float v = fontMetrics.ascent + fontMetrics.descent;
        canvas.drawText("aajaa2", 500, 200+fontMetrics.descent, mPaint);

        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("aajaa3", 200, 500-v/2, mPaint);
        canvas.drawText("aajaa4", 500, 500 - mPaint.getFontSpacing(), mPaint);
    }
}
