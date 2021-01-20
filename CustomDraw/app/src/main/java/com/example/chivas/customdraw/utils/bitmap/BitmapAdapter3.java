package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BitmapAdapter3 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(100);

        // 圆角线-圆形线冒
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(300, 500, 700, 500, mPaint);

        // 无线冒
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setColor(Color.BLUE);
        canvas.drawLine(300, 800, 700, 800, mPaint);

        // 方角线-方形线冒
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mPaint.setColor(Color.RED);
        canvas.drawLine(300, 1100, 700, 1100, mPaint);
    }
}
