package com.example.chivas.customdraw.view.act.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.chivas.customdraw.view.widget.view.CommonImgEffectView2;

public class CommonImgEffectActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bitmap_draw1);
        setContentView(new CommonImgEffectView2(this));
    }
}
