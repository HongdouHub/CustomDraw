package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;

public class BitmapAdapter5 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setPathEffect(new CornerPathEffect(10));
        mPaint.setShadowLayer(10, 20, 20, Color.RED);

        Path path = new Path();
        for( int i = 1; i <= 50; i++ ) {
            path.lineTo( 20 * i, (float) (( Math.random() * 50 * i) % 200));
        }
        canvas.translate(10, 200);
        canvas.drawPath(path, mPaint);
    }
}
