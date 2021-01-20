package com.example.chivas.customdraw.view.act.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.view.widget.view.BitmapDrawView;

/**
 * Created by Administrator on 016 1月16日.
 */
public class BitmapDrawActivity extends Activity {

    private LinearLayout llContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_draw);

        llContainer = findViewById(R.id.llContainer);

        for (int i = 16; i > 0; i--) {
            final BitmapDrawView bitmapDrawView = new BitmapDrawView(this);
            bitmapDrawView.setAdapterType(i);
//            bitmapDrawView.setBitmapDrawable();
            bitmapDrawView.postInvalidate();
            bitmapDrawView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(BitmapDrawActivity.this, "触发点击事件", Toast.LENGTH_SHORT).show();
                    bitmapDrawView.postInvalidate();
                }
            });

            llContainer.addView(bitmapDrawView);
        }
    }
}
