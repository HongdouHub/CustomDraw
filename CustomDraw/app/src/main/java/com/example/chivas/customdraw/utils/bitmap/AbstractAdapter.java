package com.example.chivas.customdraw.utils.bitmap;

import android.graphics.Paint;

public abstract class AbstractAdapter implements IParse {

    protected Paint mPaint;

    public AbstractAdapter() {
        mPaint = new Paint();
    }
}
