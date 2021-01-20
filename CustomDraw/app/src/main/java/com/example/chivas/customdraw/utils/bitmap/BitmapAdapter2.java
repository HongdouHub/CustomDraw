package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BitmapAdapter2 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        mPaint.setAntiAlias(false);

//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);

        mPaint.setStrokeWidth(5);
        mPaint.setARGB(200,0,100,200);
//        mPaint.setPathEffect(new DashPathEffect(new float[] {2f, 2f}, 0f));
//        mPaint.setShader(new LinearGradient(50, 50, 150, 150, Color.GRAY, Color.BLUE, Shader.TileMode.REPEAT));

        mPaint.setShadowLayer(10, 20, 20, Color.GREEN);
        mPaint.setColor(Color.RED);

        // 画圆
        canvas.drawCircle(100, 100, 50, mPaint);
        mPaint.setShadowLayer(10, 20, 20, Color.BLACK);

        // 画线
        canvas.drawLine(100, 300, 500, 300, mPaint);
    }
}
