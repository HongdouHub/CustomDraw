package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import static android.graphics.Canvas.ALL_SAVE_FLAG;
import static com.example.chivas.customdraw.constants.XferModeConstants.MODE_MAP;

public class BitmapAdapter14 extends AbstractAdapter {

    private Bitmap smallBitmap;

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        canvas.drawColor(Color.GRAY);
        int sc = canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, ALL_SAVE_FLAG);

        mPaint.setAntiAlias(true);

        smallBitmap = BitmapUtils.resizeBitmap(800, 500, bitmap);
        int smallWidth = smallBitmap.getWidth();
        int smallHeight = smallBitmap.getHeight();

        // 方案一
        Path path = new Path();
        path.addOval(new RectF(80, 10, smallWidth - 80, smallHeight - 80), Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap(smallBitmap, 0, 0, mPaint);

        // 方案二
        canvas.restoreToCount(sc);
        sc = canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, ALL_SAVE_FLAG);
        path.reset();
        path.addCircle(200, 550, 160, Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawColor(Color.GREEN);

        path.reset();
        path.addCircle(200, 550, 150, Path.Direction.CCW);
        canvas.clipPath(path);
//        canvas.clipPath(path, Region.Op.REPLACE);
        canvas.drawBitmap(smallBitmap, 0, 350, mPaint);

        // 方案三
        canvas.restoreToCount(sc);
        sc = canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), null, ALL_SAVE_FLAG);
        canvas.drawBitmap(processRoundBitmap(smallBitmap, smallWidth, smallHeight, 400, 150, 150),
                300, 30, mPaint);

        // 方案四
        canvas.restoreToCount(sc);
//        canvas.drawRoundRect(new RectF(400, 450, 400 + smallWidth / 2, 450 + smallHeight / 2), 200, 200, mPaint);
//        mPaint.setXfermode(MODE_MAP.get("SrcIn"));
        canvas.drawBitmap(smallBitmap, 400, 450, mPaint);
//        mPaint.setXfermode(null);
    }

    /**
     * 裁剪图片
     * @param bitmap 原图
     * @param width  原图宽度
     * @param height 原图高度
     * @param roundX 裁剪成圆，圆点坐标x
     * @param roundY 裁剪成圆，圆点坐标y
     * @param radius 裁剪成圆，圆半径
     */
    private Bitmap processRoundBitmap(Bitmap bitmap, int width, int height,
                                      float roundX, float roundY, float radius) {
        Bitmap bitmapEmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmapEmp);

        Paint paint = new Paint();

        canvas2.drawCircle(roundX, roundY, radius, paint);
//        canvas2.drawOval(new RectF(200, 500, 200 + bitmapEmp.getWidth() / 2, 500 + bitmapEmp.getHeight() / 2), p);
//        canvas2.drawOval(new RectF(200, 500, 200+width/2, 500+height/2), p);

        paint.setAntiAlias(true);
        paint.setXfermode(MODE_MAP.get("SrcIn"));
        canvas2.drawBitmap(bitmap, 0, 0, paint);
        return bitmapEmp;
    }
}
