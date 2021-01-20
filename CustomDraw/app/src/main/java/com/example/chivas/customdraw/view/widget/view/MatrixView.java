package com.example.chivas.customdraw.view.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.constants.ScreenConstants;
import com.example.chivas.customdraw.utils.DpUtils;
import com.example.chivas.customdraw.utils.MultiTextUtils;

public class MatrixView extends View {

    private Paint mPaint;
    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mBgBitmap;
    private Matrix mMatrix;

    {
        // 三维矩阵含义：
        // MSCALE_X, MSKEW_X, MTRANS_X
        // MSKEW_Y, MSCALE_Y, MTRANS_Y
        // MPERSP_0,MPERSP_1, MPERSP_2

        //缩放（Scale）
        //对应 MSCALE_X 与 MSCALE_Y

        //错切（Skew）
        //对应 MSKEW_X 与 MSKEW_Y

        //位移（Translate）
        //对应 MTRANS_X 与 MTRANS_Y

        //旋转（Rotate）
        //旋转没有专门的数值来计算，Matrix 会通过计算缩放与错切来处理旋转。

        mMatrix = new Matrix();
        // 定义矩阵对象
        // 缩放(0.5,0.5)倍
        // 位移(300,300)
        // 错切(0.1,0.1)
        mMatrix.setValues(new float[]{
                0.5f, 0.1f, 300f,
                0.1f, 0.5f, 300f,
                0.0f, 0.0f, 1.0f
        });
        // 复原，相当于
        // 1, 0, 0,
        // 0, 1, 0
        // 0, 0, 1
        mMatrix.reset();
    }

    public MatrixView(Context context) {
        this(context, null);
    }

    public MatrixView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatrixView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mBgBitmap = ((BitmapDrawable) context.getResources().getDrawable(
                R.mipmap.girl)).getBitmap();

        mTextPaint.setTextSize(DpUtils.dp2px(12));
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    @SuppressWarnings("all")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画红心圆
        mPaint.setColor(Color.RED);
        canvas.drawCircle(ScreenConstants.SCREEN_WIDTH / 2f, 30, 20, mPaint);

        // 第一个图
        mMatrix.reset();
        mMatrix.postScale(0.3f, 0.3f);
        mMatrix.postTranslate(800, 200);

        MultiTextUtils.printText(format(mMatrix), canvas, mTextPaint, 400, 300);
        canvas.drawBitmap(mBgBitmap, mMatrix, mPaint);

        // 第二个图
        mMatrix.reset();
        mMatrix.setValues(new float[] {
                0.3f, 0.05f, 800f,
                0.05f, 0.3f, 700f,
                0.0f, 0.0f, 1.0f
        });

        MultiTextUtils.printText(format(mMatrix), canvas, mTextPaint, 400, 800);
        canvas.drawBitmap(mBgBitmap, mMatrix, mPaint);

        // 第三个图
        mMatrix.reset();
        mMatrix.postRotate(30, 300, 600);
        mMatrix.postScale(0.3f, 0.3f);
        mMatrix.postTranslate(800, 1200);

        MultiTextUtils.printText(format(mMatrix), canvas, mTextPaint, 400, 1300);
        canvas.drawBitmap(mBgBitmap, mMatrix, mPaint);

        // 第四个图
        mMatrix.reset();
        mMatrix.postScale(0.3f, 0.3f);
        mMatrix.postTranslate(800, 600);
        mMatrix.postTranslate(-300, -600);
        mMatrix.postRotate(30);
        mMatrix.postTranslate(300, 1700);

        MultiTextUtils.printText(format(mMatrix), canvas, mTextPaint, 400, 1800);

        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,  //R
                0, 1, 0, 0, 100,//G
                0, 0, 1, 0, 0,  //B
                0, 0, 0, 1, 0,  //A
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(mBgBitmap, mMatrix, mPaint);
    }

    private String format(Matrix matrix) {
        return matrix.toString().replace("[", "\n[")
                .replace("{", "")
                .replace("}", "");
    }
}
