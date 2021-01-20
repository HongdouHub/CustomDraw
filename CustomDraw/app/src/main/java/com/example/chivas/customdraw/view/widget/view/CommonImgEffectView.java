package com.example.chivas.customdraw.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.chivas.customdraw.R;

import static com.example.chivas.customdraw.constants.LogConstants.DRAW_VIEW_TAG;

public class CommonImgEffectView extends View {

    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private float[] srcPosition, dstPosition;
    private RectF srcRect, dstRect;
    private Matrix matrix;
    private Point prePivot, lastPivot;

    private Bitmap mControlBitmap;
    private int mControlWidth, mControlHeight;

    private Paint mPaint, mRectPaint, mFramePaint;

    public static final int OPERATE_DEFAULT = -1;   // 默认
    public static final int OPERATE_TRANSLATE = 0;  // 移动
    public static final int OPERATE_SCALE = 1;      // 缩放
    public static final int OPERATE_ROTATE = 2;     // 旋转
    public static final int OPERATE_SELECTED = 3;   // 选择
    private int lastOperateType = OPERATE_DEFAULT;  // 最后一次的操作类型
    private PointF lastPoint;                       // 最后点击位置
    private PointF symmetricPoint;                  // 旋转中心

    private float translateX = 0, translateY = 0;   // 平移量
    private float scaleValue = 1;                   // 缩放量
    private float preDegree, lastDegree;            // 旋转量

    /*
     * 图片控制点
     * 0---1---2
     * |       |
     * 7   8   3
     * |       |
     * 6---5---4
     */
    public static final int CTR_NONE = -1;
    public static final int CTR_LEFT_TOP = 0;
    public static final int CTR_MID_TOP = 1;
    public static final int CTR_RIGHT_TOP = 2;
    public static final int CTR_RIGHT_MID = 3;
    public static final int CTR_RIGHT_BOTTOM = 4;
    public static final int CTR_MID_BOTTOM = 5;
    public static final int CTR_LEFT_BOTTOM = 6;
    public static final int CTR_LEFT_MID = 7;
    public static final int CTR_MID_MID = 8;
    public int currentCtr = CTR_NONE;

    public CommonImgEffectView(Context context) {
        this(context, null);
    }

    public CommonImgEffectView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonImgEffectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs);
        initLocation();
        initPaint();
        initMatrix();
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

    private void initLocation() {
        // 计算九个顶点坐标（按照顺时针遍历顺序）
        srcPosition = new float[]{
                0, 0,
                mWidth / 2f, 0,
                mWidth, 0,

                mWidth, mHeight / 2f,
                mWidth, mHeight,
                mWidth / 2f, mHeight,

                0, mHeight,
                0, mHeight / 2f,
                mWidth / 2f, mHeight / 2f
        };
        dstPosition = srcPosition.clone();

        srcRect = new RectF(0, 0, mWidth, mHeight);
        dstRect = new RectF();

        prePivot = new Point(mWidth / 2, mHeight / 2);
        lastPivot = new Point(mWidth / 2, mHeight / 2);

        lastPoint = new PointF(0, 0);
        symmetricPoint = new PointF();
    }

    private void initPaint() {
        mPaint = new Paint();

        // 背景色
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(Color.RED);
        mRectPaint.setAlpha(100);

        // 画布颜色
        mFramePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFramePaint.setColor(Color.GREEN);
    }

    private void initMatrix() {
        matrix = new Matrix();
        setMatrix(OPERATE_DEFAULT);
    }

    /**
     * 矩阵变换，达到图形平移的目的
     */
    private void setMatrix(int type) {

        // set  : 设置，会覆盖掉之前的数值，导致之前的操作失效
        // pre  : 前乘，相当于矩阵的右乘， M' = M * S（S为特殊矩阵）
        // post : 后乘，相当于矩阵的左乘， M' = S * M（S为特殊矩阵）
        switch (type) {
            case OPERATE_TRANSLATE: // 平移
                matrix.postTranslate(translateX, translateY);
                break;
            case OPERATE_SCALE:     // 缩放
                matrix.postScale(scaleValue, scaleValue, symmetricPoint.x, symmetricPoint.y);
                break;
            case OPERATE_ROTATE:    // 旋转
                matrix.postRotate(preDegree - lastDegree,
                        dstPosition[16], dstPosition[17]);
                break;
            default: // OPERATE_DEFAULT或OPERATE_SELECTED
        }

        // 计算一组基于当前Matrix变换后的位置，src作为参数传入原始数值， 计算结果存入到dst中，src不变
        matrix.mapPoints(dstPosition, srcPosition);
        matrix.mapRect(dstRect, srcRect);
    }

    /**
     * 判断点所在的控制点
     */
    private int isOnCtrPosition(float eventX, float eventY) {
        RectF rectF = new RectF(eventX - mControlWidth / 2f, eventY - mControlHeight / 2f,
                eventX + mControlWidth / 2f, eventY + mControlHeight / 2f);

        int controlId = 0;
        for (int i = 0; i < dstPosition.length; i += 2) {
            if (rectF.contains(dstPosition[i], dstPosition[i + 1])) {
                return controlId;
            }
            controlId++;
        }
        return CTR_NONE;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        Log.i(DRAW_VIEW_TAG, "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        int operateType = lastOperateType;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentCtr = isOnCtrPosition(eventX, eventY);
                Log.i(DRAW_VIEW_TAG, "isOnCtrPosition: " + currentCtr);
                if (currentCtr != CTR_NONE || dstRect.contains(eventX, eventY)) {
                    operateType = OPERATE_SELECTED;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 除了中点，其余均为缩放，即 [0, 7]
                if (currentCtr > CTR_NONE && currentCtr < CTR_MID_MID) {
                    operateType = OPERATE_SCALE;
                } else if (currentCtr == CTR_MID_MID) {
                    // 中点为旋转
                    operateType = OPERATE_ROTATE;
                } else if (lastOperateType == OPERATE_SELECTED) {
                    // 其余为平移
                    operateType = OPERATE_TRANSLATE;
                }
                break;
            case MotionEvent.ACTION_UP:
                operateType = OPERATE_SELECTED;
                break;
            default:
                operateType = lastOperateType;
        }
        Log.i(DRAW_VIEW_TAG, "onTouchEvent operateType: " + operateType);

        switch (operateType) {
            case OPERATE_TRANSLATE:
                translate(eventX, eventY);
                break;
            case OPERATE_SCALE:
                scale(eventX, eventY);
                break;
            case OPERATE_ROTATE:
                rotate(event);
                break;
            default:
        }

        lastPoint.x = eventX;
        lastPoint.y = eventY;

        lastOperateType = operateType;
        invalidate(); //重绘

        super.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背景,以便测试矩形的映射
        canvas.drawRect(dstRect, mRectPaint);

        // 绘制主图片
        canvas.drawBitmap(mBitmap, matrix, mPaint);

        // 绘制边框,以便测试点的映射
        canvas.drawLine(dstPosition[0], dstPosition[1], dstPosition[4], dstPosition[5], mFramePaint);
        canvas.drawLine(dstPosition[4], dstPosition[5], dstPosition[8], dstPosition[9], mFramePaint);
        canvas.drawLine(dstPosition[8], dstPosition[9], dstPosition[12], dstPosition[13], mFramePaint);
        canvas.drawLine(dstPosition[0], dstPosition[1], dstPosition[12], dstPosition[13], mFramePaint);
        canvas.drawPoint(dstPosition[16], dstPosition[17], mFramePaint);

        // 绘制控制点图片
        for (int i = 0; i < dstPosition.length; i += 2) {
            canvas.drawBitmap(mControlBitmap,
                    dstPosition[i] - mControlWidth / 2f, dstPosition[i + 1] - mControlHeight / 2f,
                    mPaint);
        }

    }

    // 平移
    private void translate(float eventX, float eventY) {
        prePivot.x += eventX - lastPoint.x;
        prePivot.y += eventY - lastPoint.y;

        translateX = prePivot.x - lastPivot.x;
        translateY = prePivot.y - lastPivot.y;

        lastPivot.x = prePivot.x;
        lastPivot.y = prePivot.y;

        Log.i(DRAW_VIEW_TAG, "translate: translateX = " + translateX + ", translateY = " + translateY);

        //设置矩阵
        setMatrix(OPERATE_TRANSLATE);
    }

    // 缩放
    private void scale(float eventX, float eventY) {
        // currentCtr的取值范围是 [0, 7]
        int pointIndex = currentCtr * 2;

        float px = dstPosition[pointIndex];
        float py = dstPosition[pointIndex + 1];

        // 初始化对称点坐标
        // 0->4, 1->5, 2->6, 3->7
        float oppositeX = 0;
        float oppositeY = 0;

        // 计算对称点坐标
        if (0 <= currentCtr && currentCtr < 4) {
            oppositeX = dstPosition[pointIndex + 8];
            oppositeY = dstPosition[pointIndex + 9];
        } else if (4 <= currentCtr && currentCtr < 8) {
            oppositeX = dstPosition[pointIndex - 8];
            oppositeY = dstPosition[pointIndex - 7];
        }

        float moveDistance = calculateTwoPointsDistance(eventX, eventY, oppositeX, oppositeY);
        float pointDistance = calculateTwoPointsDistance(px, py, oppositeX, oppositeY);

        scaleValue = moveDistance / pointDistance;
        symmetricPoint.x = oppositeX;
        symmetricPoint.y = oppositeY;

        Log.i(DRAW_VIEW_TAG, "scale: scaleValue = " + scaleValue);

        //设置矩阵
        setMatrix(OPERATE_SCALE);
    }

    // 旋转图片
    private void rotate(MotionEvent event) {

        // 获取触控点的数量,返回1可能是因为一个手指按压了屏幕
        if (event.getPointerCount() == 1) {
            preDegree = computeDegree(event.getX(), event.getY(), dstPosition[16], dstPosition[17]);
        } else {
            preDegree = computeDegree(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
        }

        Log.i(DRAW_VIEW_TAG, "rotate: preDegree = " + preDegree + ", lastDegree = " + lastDegree);

        //设置矩阵
        setMatrix(OPERATE_ROTATE);
        lastDegree = preDegree;
    }

    /**
     * 计算两个点之间的距离
     */
    private float calculateTwoPointsDistance(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 计算两点与垂直方向夹角
     */
    public float computeDegree(float x1, float y1, float x2, float y2) {
        float dx = x1 - x2;
        float dy = y1 - y2;

        float degree = 0.0f;
        float angle = (float) (Math.asin(dx / Math.sqrt(dx * dx + dy * dy)) * 180 / Math.PI);
        if (!Float.isNaN(angle)) {
            if (dx >= 0 && dy <= 0) {//第一象限
                degree = angle;
            } else if (dx <= 0 && dy <= 0) {//第二象限
                degree = angle;
            } else if (dx <= 0 && dy >= 0) {//第三象限
                degree = -180 - angle;
            } else if (dx >= 0 && dy >= 0) {//第四象限
                degree = 180 - angle;
            }
        }
        return degree;
    }
}
