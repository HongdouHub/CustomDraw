package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

public class BitmapAdapter6 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(8);

        // 1 第一列，划线
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#99ff0000"));
        mPaint.setShadowLayer(5, 5, 5, Color.BLUE); // 阴影效果：蓝色

        // 1.1 画圆
        canvas.drawCircle(150, 100, 50, mPaint);
        // 1.2 画矩形
        canvas.drawRect(new Rect(100, 200, 200, 300), mPaint);
        // 1.3 画矩形
        canvas.drawRect(new Rect(100, 350, 200, 400), mPaint);
        // 1.4 画椭圆
        canvas.drawOval(new RectF(100, 450, 200, 500),mPaint);

        // 1.5 画矩形圆
        mPaint.setPathEffect(new CornerPathEffect(50));
        canvas.drawRect(new Rect(100, 550, 200, 650), mPaint);
        mPaint.setPathEffect(null);

        // 1.6 画圆角矩形
        canvas.drawRoundRect(new RectF(100, 700, 200, 800), 20, 5, mPaint);

        // 1.7 画三角形
        Path path = new Path();
        path.moveTo(150, 850);
        path.lineTo(200, 935);
        path.lineTo(100, 935);
        path.close();
        canvas.drawPath(path, mPaint);

        // 2 第二列，画填充
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);

        // 2.1 画圆
        mPaint.setShader(new LinearGradient(250, 200, 350, 300, Color.GRAY, Color.RED, Shader.TileMode.MIRROR));
        canvas.drawCircle(300, 100, 50, mPaint);
        mPaint.setShader(null);

        // 2.2 画矩形
        canvas.drawRect(250, 200, 350, 300, mPaint);

        // 2.3 画矩形
        canvas.drawRect(new Rect(250, 350, 350, 400), mPaint);

        // 2.4 画椭圆
        mPaint.setPathEffect(new CornerPathEffect(25));
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawOval(new RectF(250, 450, 350, 500), mPaint);
        mPaint.setPathEffect(null);
        mPaint.setStyle(Paint.Style.FILL);

        // 2.5 画矩形
        mPaint.setPathEffect(new CornerPathEffect(25));
        canvas.drawRect(new Rect(250, 550, 350, 650), mPaint);
        mPaint.setPathEffect(null);

        // 2.6 画圆角矩形
        canvas.drawRoundRect(new RectF(250, 700, 350, 800), 20, 5, mPaint);
        // 2.6右 画圆
        canvas.drawOval(new RectF(400, 700, 500, 800), mPaint);

        // 2.7 画三角形
        path = new Path();
        path.moveTo(300, 850);
        path.lineTo(250, 935);
        path.lineTo(350, 935);
        canvas.drawPath(path, mPaint);
        mPaint.setShader(null);
    }
}
