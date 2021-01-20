package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static com.example.chivas.customdraw.constants.XferModeConstants.MODE_MAP;

public class BitmapAdapter16 extends AbstractAdapter {

    @Override
    public void parse(Canvas canvas, Bitmap bitmap) {
        Bitmap smallBitmap = BitmapUtils.resizeBitmap(800, 500, bitmap);

        int width = smallBitmap.getWidth();
        int height = smallBitmap.getHeight();
        int size = Math.min(width, height);

        int sc = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        Bitmap dst = makeCircle(width, height, size);
        canvas.drawBitmap(dst, 150, 100, mPaint);
        mPaint.setXfermode(MODE_MAP.get("SrcIn"));
        canvas.drawBitmap(smallBitmap, 100, 100, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    private Bitmap makeCircle(int width, int height, float size) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(size / 2, size / 2, size / 2, paint);
        return bitmap;
    }
}
