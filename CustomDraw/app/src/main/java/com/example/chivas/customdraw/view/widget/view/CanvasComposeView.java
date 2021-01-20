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
public class CanvasComposeView extends View {

    private Paint redPaint;
    private Paint greenPaint;
    private Rect mRect = new Rect(0, 0, 400, 220); // 构造一个矩形

    public CanvasComposeView(Context context) {
        this(context, null);
    }

    public CanvasComposeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasComposeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 构造两个画笔，一个红色，一个绿色
        redPaint = generatePaint(Color.GREEN, Paint.Style.FILL, 20);
        greenPaint = generatePaint(Color.RED, Paint.Style.STROKE, 10);
    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 在平移画布前用绿色画下边框
        canvas.drawRect(mRect, greenPaint);

        // 平移画布后,再用红色边框重新画下这个矩形
        canvas.translate(100, 100);
        canvas.drawRect(mRect, redPaint);

    }
}
