package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

class BitmapUtils {

    private BitmapUtils() {
        //
    }

    public static Bitmap resizeBitmap(int maxWidth, int maxHeight, Bitmap bitmap) {
        return resizeBitmap(maxWidth, maxHeight, bitmap, false);
    }

    public static Bitmap resizeBitmap(int maxWidth, int maxHeight, Bitmap bitmap, boolean hasAlpha) {
        if (bitmap == null) {
            return null;
        }

        int outWidth = bitmap.getWidth();
        int outHeight = bitmap.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = calculateInSampleSize(outWidth, outHeight, maxWidth, maxHeight);

        if (!hasAlpha) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] bytes = outputStream.toByteArray();
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

    }

    private static int calculateInSampleSize(int outWidth, int outHeight, int maxWidth, int maxHeight) {
        int inSampleSize = 1;

        if (outWidth > maxWidth && outHeight > maxHeight) {
            inSampleSize = 2;

            while (outWidth / inSampleSize > maxWidth &&
                    outHeight / inSampleSize > maxHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }


}
