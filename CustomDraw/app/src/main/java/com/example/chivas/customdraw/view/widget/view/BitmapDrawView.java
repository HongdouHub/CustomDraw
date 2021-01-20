package com.example.chivas.customdraw.view.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.utils.DpUtils;
import com.example.chivas.customdraw.utils.bitmap.AbstractAdapter;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter1;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter10;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter11;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter12;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter13;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter14;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter15;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter16;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter2;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter3;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter4;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter5;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter6;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter7;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter8;
import com.example.chivas.customdraw.utils.bitmap.BitmapAdapter9;

import java.util.HashMap;
import java.util.Map;

import static com.example.chivas.customdraw.constants.LogConstants.DRAW_VIEW_TAG;

/**
 * Created by Administrator on 016 1月16日.
 */
public class BitmapDrawView extends View {

    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private int mAdapterType;

    private static final Map<Integer, AbstractAdapter> sMap = new HashMap<Integer, AbstractAdapter>() {{
        put(1, new BitmapAdapter1());
        put(2, new BitmapAdapter2());
        put(3, new BitmapAdapter3());
        put(4, new BitmapAdapter4());
        put(5, new BitmapAdapter5());
        put(6, new BitmapAdapter6());
        put(7, new BitmapAdapter7());
        put(8, new BitmapAdapter8());
        put(9, new BitmapAdapter9());
        put(10, new BitmapAdapter10());
        put(11, new BitmapAdapter11());
        put(12, new BitmapAdapter12());
        put(13, new BitmapAdapter13());
        put(14, new BitmapAdapter14());
        put(15, new BitmapAdapter15());
        put(16, new BitmapAdapter16());
    }};

    public void setAdapterType(int adapterType) {
        this.mAdapterType = adapterType;
    }

    public void setBitmapDrawable(@DrawableRes int drawableRes) {
        Drawable drawable = getContext().getResources().getDrawable(drawableRes);
        parseDrawable(getContext(), drawable);
    }

    public void setBitmapDrawable(Drawable drawable) {
        parseDrawable(getContext(), drawable);
    }

    public BitmapDrawView(Context context) {
        this(context, null);
    }

    public BitmapDrawView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapDrawView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BitmapDrawView);
        try {
            mAdapterType = a.getInteger(R.styleable.BitmapDrawView_adapter, 1);

            Drawable drawable = a.getDrawable(R.styleable.BitmapDrawView_bitmap);
            parseDrawable(context, drawable);
        } catch (Exception e) {
            Log.e(DRAW_VIEW_TAG, "BitmapDrawView: " + e);
        } finally {
            a.recycle();
        }
    }

    private void parseDrawable(Context context, Drawable drawable) {
        if (drawable != null) {
            mBitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.girl);
        }

        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        Log.i(DRAW_VIEW_TAG, "parseDrawable(drawable is null ? ->" + (drawable == null) + ")" +
                "mWidth: " + mWidth + ", mHeight: " + mHeight);
    }

//    private void parseDrawable(Context context, Drawable drawable) {
//        if (drawable != null) {
//            int w = drawable.getIntrinsicWidth();
//            int h = drawable.getIntrinsicHeight();
//
//            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
//                    Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
//
//            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
//            // 注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
//            Canvas canvas = new Canvas(bitmap);
//            drawable.setBounds(0, 0, w, h);
//            drawable.draw(canvas);
//            mBitmap = bitmap;
//        } else {
//            mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.girl);
//        }
//
//        mWidth = mBitmap.getWidth();
//        mHeight = mBitmap.getHeight();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED){
            setMeasuredDimension(widthSize, 1000);
        }
    }

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setTextSize(DpUtils.dp2px(12));
        paint.setColor(Color.BLACK);
        canvas.drawText("当前为第" + mAdapterType + "个界面", 50, 50, paint);

        AbstractAdapter adapter = sMap.get(mAdapterType);
        if (adapter == null) {
            adapter = new BitmapAdapter1();
        }
        adapter.parse(canvas, mBitmap);
    }

}
