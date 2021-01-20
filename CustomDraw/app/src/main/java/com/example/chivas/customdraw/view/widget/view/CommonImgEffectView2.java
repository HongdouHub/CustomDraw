package com.example.chivas.customdraw.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.utils.ViewWatcher;

import static com.example.chivas.customdraw.constants.LogConstants.DRAW_VIEW_TAG;

public class CommonImgEffectView2 extends View {

    private Bitmap mBitmap;
    private int mWidth, mHeight;

    private Bitmap mControlBitmap;
    private int mControlWidth, mControlHeight;

    private Paint mPaint;
    private ViewWatcher mViewWatcher;

    public CommonImgEffectView2(Context context) {
        this(context, null);
    }

    public CommonImgEffectView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonImgEffectView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        initAttr(context, attrs);
        initWatcher();
    }

    private void initWatcher() {
        mViewWatcher = new ViewWatcher.Builder()
                .setViewSize(mWidth, mHeight)
                .setControlSize(mControlWidth, mControlHeight)
                .setWatchEnable(false)
                .setDrawRectEnable(false)
                .setDrawFrameEnable(false)
                .setDrawControllerEnable(false)
                .build();
    }

    private void initAttr(Context context, AttributeSet attrs) {
        mControlBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.control);
        mControlWidth = mControlBitmap.getWidth();
        mControlHeight = mControlBitmap.getHeight();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonImgEffectView);
        try {

            Drawable drawable = a.getDrawable(R.styleable.CommonImgEffectView_imageBitmap);
            parseDrawable(context, drawable);
        } catch (Exception e) {
            Log.e(DRAW_VIEW_TAG, "CommonImgEffectView: " + e);
        } finally {
            a.recycle();
        }
    }

    private void parseDrawable(Context context, Drawable drawable) {
        if (drawable != null) {
            mBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.bird);
        }

        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        Log.i(DRAW_VIEW_TAG, "parseDrawable(drawable is null ? ->" + (drawable == null) + ")" +
                "mWidth: " + mWidth + ", mHeight: " + mHeight);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(DRAW_VIEW_TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewWatcher.watchOnTouchEvent(event)) {
            invalidate(); //重绘
        }
        super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景,以便测试矩形的映射
        mViewWatcher.drawRect(canvas);

        // 绘制主图片
        canvas.drawBitmap(mBitmap, mViewWatcher.getMatrix(), mPaint);

        // 绘制边框,以便测试点的映射
        mViewWatcher.drawFrame(canvas);

        // 绘制控制点图片
        mViewWatcher.drawController(canvas, mControlBitmap, mPaint);
    }
}
