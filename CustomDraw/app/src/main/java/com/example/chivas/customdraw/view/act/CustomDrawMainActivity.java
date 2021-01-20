package com.example.chivas.customdraw.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.view.act.group.ViewGroupActivity;
import com.example.chivas.customdraw.view.act.view.ViewActivity;

public class CustomDrawMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (findViewById(R.id.btnViewGroup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ViewGroupActivity.class);
            }
        });

        (findViewById(R.id.btnView)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ViewActivity.class);
            }
        });
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        super.startActivity(intent);
    }
}