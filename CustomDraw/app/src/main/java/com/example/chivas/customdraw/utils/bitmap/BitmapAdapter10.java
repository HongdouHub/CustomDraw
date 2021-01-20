package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import com.example.chivas.customdraw.utils.DpUtils;

public class BitmapAdapter10 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
//        canvas.drawText("sdfsfd", 100, 100, mPaint);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPaint.setColor(Color.GREEN);
        Path path = new Path();
        path.moveTo(200, 200);
        path.lineTo(500, 500);
        path.lineTo(200, 800);
        mPaint.setPathEffect(new CornerPathEffect(30));
        canvas.drawPath(path, mPaint);
        mPaint.setPathEffect(null);

        mPaint.setTextSize(DpUtils.dp2px(12));
        mPaint.setColor(Color.BLACK);
        /**
         * hOffset 其实位置
         * vOffset 离线条高度位置
         */
        canvas.drawTextOnPath("we are family! 是的123!! afdads", path, 100, -10, mPaint);

        mPaint.setColor(Color.GREEN);
        path = new Path();
        path.moveTo(400, 850);
        path.lineTo(1500, 850);
        canvas.drawPath(path, mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(DpUtils.dp2px(12));
        canvas.drawTextOnPath("32sfdsf!", path, 0, 15, mPaint);


        canvas.drawText("abc11", 0,4, 550, 100, mPaint );
        canvas.save();

        mPaint.setShadowLayer(5, 10, 10, Color.BLUE);
        mPaint.setTextScaleX(1.5f);
        canvas.translate(800, 500);
        canvas.rotate(90);
        canvas.drawText("abc22", 0,4, 0, 0, mPaint);


        mPaint.setColor(Color.RED);
        canvas.save();
        canvas.drawRect(new Rect(0, 50, 100, 100), mPaint);
        canvas.restore();
        canvas.drawText("abc33", 0,4, 0, 100, mPaint);
    }
}
