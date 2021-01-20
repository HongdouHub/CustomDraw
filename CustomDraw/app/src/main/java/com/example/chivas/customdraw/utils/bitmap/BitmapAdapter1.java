package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class BitmapAdapter1 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        canvas.drawColor(Color.GRAY);

        mPaint.setColor(Color.YELLOW);
        canvas.translate(100, 100);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.translate(0, 300);
        canvas.scale(0.5f, 0.5f);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);

        mPaint.setColor(Color.RED);
        canvas.translate(0, 0);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
//        canvas.restore();

        canvas.translate(700, 300);
        canvas.rotate(30);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
    }
}
