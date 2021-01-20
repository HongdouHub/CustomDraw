package com.example.chivas.customdraw.view.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 016 1月16日.
 */
public class CanvasTransformationView extends View {

    private final Rect mRect = new Rect(0, 0, 200, 200);
    private Paint mPaint;

    public CanvasTransformationView(Context context) {
        this(context, null);
    }

    public CanvasTransformationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasTransformationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 整个屏幕填充为灰色
        canvas.drawColor(Color.GRAY);

        mPaint.setColor(Color.YELLOW);

        canvas.translate(500, 500); // 平移
        canvas.drawRect(mRect, mPaint);

        canvas.translate(0, 300);   // 平移
        canvas.scale(0.5f, 0.5f);    // 缩放
        canvas.drawRect(mRect, mPaint);

        canvas.translate(0, 300);   // 平移
        canvas.rotate(45);         // 旋转
        canvas.drawRect(mRect, mPaint);
//
        canvas.translate(0, 300);   // 平移
        canvas.skew(0.5f, 0.5f);     // 扭曲
        canvas.drawRect(mRect, mPaint);
    }
}
