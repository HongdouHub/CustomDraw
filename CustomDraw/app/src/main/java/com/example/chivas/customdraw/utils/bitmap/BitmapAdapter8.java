package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BitmapAdapter8 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        canvas.drawColor(Color.GRAY);

        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);

        // 画圆-白色
        canvas.drawCircle(350, 350, 50, mPaint);
        canvas.save();

        // 画矩形-红色
        canvas.clipRect(new Rect(100, 100, 600, 600));
        canvas.drawColor(Color.parseColor("#99ff0000"));

        // 画矩形-蓝色
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(110, 110, 200, 200), mPaint);
        canvas.save();

        // 画矩形-黑色
        canvas.clipRect(new Rect(150, 150, 350, 350));
        canvas.drawColor(Color.parseColor("#aa000000"));
    }
}
