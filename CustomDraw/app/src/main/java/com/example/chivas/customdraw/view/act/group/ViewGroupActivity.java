package com.example.chivas.customdraw.view.act.group;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.chivas.customdraw.R;

public class ViewGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);

        (findViewById(R.id.btnFlowLayout1)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FlowLayoutActivity1.class);
            }
        });

        (findViewById(R.id.btnFlowLayout2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FlowLayoutActivity2.class);
            }
        });

        (findViewById(R.id.btnFlowLayout3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(FlowLayoutActivity3.class);
            }
        });
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        super.startActivity(intent);
    }
}