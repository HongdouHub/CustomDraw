package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class BitmapAdapter12 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        // 画扇形
        canvas.drawArc(new RectF(100, 100, 300, 300),
                0, 120, true, mPaint);
    }
}
