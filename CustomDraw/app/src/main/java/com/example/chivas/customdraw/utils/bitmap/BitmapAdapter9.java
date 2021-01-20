package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class BitmapAdapter9 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        canvas.drawColor(Color.GRAY);

        // 画圆-白色
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
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
        canvas.save();

        // 画矩形-黄色
        canvas.clipRect(new Rect(200, 200, 300, 300));
        canvas.drawColor(Color.YELLOW);
        canvas.save();

        // 将黄色矩形改为蓝色
        canvas.restore();
        canvas.drawColor(Color.parseColor("#aa0000ff"));

        // 将黑色矩形改为蓝色
        canvas.restore();
        canvas.drawColor(Color.parseColor("#aa0000ff"));

        // 此处会将红色背景矩形改为蓝色
//        canvas.restore();
//        canvas.drawColor(Color.parseColor("#aa0000ff"));

//        canvas.restore();
//        canvas.drawColor(Color.parseColor("#aa0000ff"));
    }
}
