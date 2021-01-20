package com.example.chivas.customdraw.view.act.view;

import android.app.Activity;
import android.os.Bundle;

import com.example.chivas.customdraw.view.widget.view.CanvasTransformationView;

/**
 * 2 canvas变换
 *
 * 通过canvas实现平移、旋转、缩放、仿射变换
 */
public class CanvasTransformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CanvasTransformationView(this));
    }
}
