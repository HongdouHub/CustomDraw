package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import com.example.chivas.customdraw.utils.DpUtils;

public class BitmapAdapter4 extends AbstractAdapter {

    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShadowLayer(5, 5, 50, Color.BLUE);

        mTextPaint.setTextSize(DpUtils.dp2px(12));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        // 1. 画文字
        canvas.drawText("拐弯时尖角（默认值）", 250, 300, mTextPaint);
        Path path = new Path();
        path.moveTo(500, 200);
        path.lineTo(800, 200);
        path.lineTo(500, 400);
        canvas.drawPath(path, mPaint);          // 拐弯时尖角（默认值）

        canvas.drawText("拐弯时圆弧角", 250, 600, mTextPaint);
        path = new Path();
        path.moveTo(500, 500);
        path.lineTo(800, 500);
        path.lineTo(500, 700);
//        path.close();
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 拐弯时圆弧角
        canvas.drawPath(path, mPaint);

        // 继续使用会存有之前的属性信息
        canvas.drawText("拐弯时保持宽度不变", 250, 900, mTextPaint);
        path.moveTo(500, 800);
        path.lineTo(800, 800);
        path.lineTo(500, 1000);
        mPaint.setStrokeJoin(Paint.Join.BEVEL); // 拐弯时保持宽度不变（直接断角）
        canvas.drawPath(path, mPaint);

        canvas.drawText("拐弯时尖角（默认值）", 250, 1200, mTextPaint);
        path = new Path();
        path.moveTo(500, 1100);
        path.lineTo(800, 1100);
        path.lineTo(500, 1300);
        mPaint.setStrokeJoin(Paint.Join.MITER); // 拐弯时尖角（默认值）
        canvas.drawPath(path, mPaint);
    }
}
