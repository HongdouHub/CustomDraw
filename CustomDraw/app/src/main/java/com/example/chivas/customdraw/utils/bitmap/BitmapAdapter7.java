package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class BitmapAdapter7 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        canvas.drawColor(Color.GRAY);

        mPaint.setAntiAlias(true);

        // 黑色矩形
        canvas.translate(100, 100);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

        // 小蓝色矩形
        canvas.translate(0, 100);
        canvas.scale(0.5f, 0.5f);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

        // 45度小蓝色矩形
        canvas.rotate(45);
        canvas.translate(100, 200);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

        // 大蓝色矩形
        canvas.rotate(-45);
        canvas.scale(2f, 2f);
        canvas.translate(400, 100);
        canvas.skew(-0.707f, 0.707f);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);

        // 大蓝色矩形内一小块矩形
        mPaint.setColor(Color.parseColor("#8800ffff"));
        canvas.drawRect(new Rect(0, 0, 50, 50), mPaint);
    }
}
