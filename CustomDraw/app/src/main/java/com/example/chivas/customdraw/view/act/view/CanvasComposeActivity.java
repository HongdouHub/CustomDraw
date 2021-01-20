package com.example.chivas.customdraw.view.act.view;

import android.app.Activity;
import android.os.Bundle;

import com.example.chivas.customdraw.view.widget.view.CanvasComposeView;

/**
 * Created by Administrator on 016 1月16日.
 */
public class CanvasComposeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CanvasComposeView(this));
    }
}
