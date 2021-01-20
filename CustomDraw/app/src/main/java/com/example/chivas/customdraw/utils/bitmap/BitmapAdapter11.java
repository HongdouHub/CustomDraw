package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

public class BitmapAdapter11 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {

        // 画波浪线
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        Path path = new Path();
        path.moveTo(100, 300);
        path.cubicTo(100, 50, 500, 400, 700, 150);
        canvas.drawPath(path, mPaint);

        // 画跟随波浪线的文字
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextScaleX(1.5f);
        mPaint.setTextSize(50);
        canvas.drawTextOnPath("我爱你塞北的雪！", path, 0, 15, mPaint);
    }
}
