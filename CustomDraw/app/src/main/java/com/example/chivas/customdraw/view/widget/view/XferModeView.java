package com.example.chivas.customdraw.view.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.chivas.customdraw.utils.DpUtils;

import static android.graphics.Canvas.ALL_SAVE_FLAG;
import static com.example.chivas.customdraw.constants.XferModeConstants.MODE_MAP;
import static com.example.chivas.customdraw.constants.XferModeConstants.MODE_TYPE;

/**
 * Created by Administrator on 016 1月16日.
 */
public class XferModeView extends View {

    private static final int W = 64 * 3;
    private static final int H = 64 * 3;
    private static final int ROW_MAX = 4;   // number of samples per row

    private static Bitmap sSrcB; // 天蓝色矩形
    private static Bitmap sDstB; // 浅黄色圆形

    private Shader mBG;     // background checker-board pattern
    private Paint mDrawablePaint;
    private Paint mLabelPaint;

    public XferModeView(Context context) {
        this(context, null);
    }

    public XferModeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XferModeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setTextSize(DpUtils.dp2px(12));
        mLabelPaint.setTextAlign(Paint.Align.CENTER);

        mDrawablePaint = new Paint();
        mDrawablePaint.setFilterBitmap(false);

        sSrcB = makeSrc(W, H);
        sDstB = makeDst(W, H);
        initBackground();
    }

    private void initBackground() {
        // make a checker board pattern
        Bitmap bm = Bitmap.createBitmap(new int[]{0xFFFFFF, 0xCCCCCC, 0xCCCCCC, 0xFFFFFF},
                2, 2, Bitmap.Config.RGB_565);

        mBG = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);

        Matrix m = new Matrix();
        m.setScale(6, 6);
        mBG.setLocalMatrix(m);
    }

    // 画圆形
    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44); // 浅黄色
        c.drawOval(new RectF(0, 0, w * 3 / 4, h * 3 / 4), p);
        return bm;
    }

    // 画矩形
    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF); // 天蓝色
        c.drawRect(w / 3, h / 3, w * 19 / 20, h * 19 / 20, p);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        canvas.translate(15, 90);
        
        int curX = 0;
        int curY = 0;
        int size = MODE_MAP.size();
        for (int i = 0; i < size; i++) {
            // 1. 画文字
            canvas.drawText(MODE_TYPE[i],
                    curX + W / 2,
                    curY - mLabelPaint.getTextSize() / 2,
                    mLabelPaint);

            // 2. 画图
            mDrawablePaint.setXfermode(null);

            // 2.1 画线
            mDrawablePaint.setStyle(Paint.Style.STROKE);
            mDrawablePaint.setShader(null);
            canvas.drawRect(
                    curX - 0.5f,
                    curY - 0.5f,
                    curX + W + 0.5f,
                    curY + H + 0.5f,
                    mDrawablePaint);

            // 2.2 画棋盘形图案
            mDrawablePaint.setStyle(Paint.Style.FILL);
            mDrawablePaint.setShader(mBG);
            canvas.drawRect(
                    curX,
                    curY,
                    curX + W,
                    curY + H,
                    mDrawablePaint
            );

            // 2.3 将src/dst示例绘制到我们的屏幕外位图中
            // savelayer 意思就是新建一个图层，之后的操作都在这个图层上进行
            int sc = canvas.saveLayer(new RectF(curX, curY, curX + W, curY + H), null, ALL_SAVE_FLAG);

            // 2.4 平移图片
            canvas.translate(curX, curY);

            // 2.5 画底图
            /**
             * （1）girlBitmap： 图片Bitmap对象
             * （2）girlDesRect = new Rect(20, 50, 20+girlBitWidth, 50+girlBitHeight);
             *      表示图片的左边所在的位置为20个像素；
             *      图片的顶部在View上的位置为50个像素；
             *      图片右边在view上的位置为（20+girlBitWidth ）即距离图片的左边的距离是：[（20+girlBitWidth ）- 20]
             *      底部一样的道理。
             * （3）girlSrcRect ：表示我们要画图的部分。
             *      girlDesRect ：表示我们要绘图的位置。
             */
//            canvas.drawBitmap(girlBitmap, girlSrcRect, girlDesRect, null);

            canvas.drawBitmap(sDstB, 0, 0, mDrawablePaint);
            mDrawablePaint.setXfermode(MODE_MAP.get(MODE_TYPE[i]));
            canvas.drawBitmap(sSrcB, 0, 0, mDrawablePaint);

            /**
             * canvas.save()                保存Canvas的状态（Matrix等属性）
             * canvas.restoreToCount(sc);   回复Canvas的状态（Matrix等属性）
             */
            canvas.restoreToCount(sc);

            // 3. 修改坐标位置
            curX += W + 10;
            if ((i % ROW_MAX) == ROW_MAX - 1) {
                curX = 0;
                curY += H + 90;
            }
        }
    }
}
