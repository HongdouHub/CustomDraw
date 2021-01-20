package com.example.chivas.customdraw.view.act.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.chivas.customdraw.view.widget.view.MatrixView;

public class MatrixActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MatrixView(this));
    }
}
