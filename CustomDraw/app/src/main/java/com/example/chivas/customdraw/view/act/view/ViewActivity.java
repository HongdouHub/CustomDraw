package com.example.chivas.customdraw.view.act.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.chivas.customdraw.R;
import com.example.chivas.customdraw.constants.ScreenConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        ScreenConstants.init(this);

        ListView listView = (ListView) findViewById(R.id.listView);
        initListView(listView);
    }

    private void initListView(ListView listView) {
        final String[] titleList = getResources().getStringArray(R.array.function_title_list);
        final String[] textList = getResources().getStringArray(R.array.function_text_list);

        final Class<?>[] activityList = new Class<?>[]{
                CanvasDrawActivity.class,           // 1 canvas基本图形的绘制
                XferModeActivity.class,             // 2 XferMode层级叠加策略
                CanvasTransformationActivity.class, // 3 canvas变换
                CanvasStackActivity.class,          // 4 Canvas保存与回滚
                CanvasComposeActivity.class,        // 5 Canvas合成
                BitmapDrawActivity.class,           // 6 绘制圆形头像
                MatrixActivity.class,               // 7 Matrix矩阵
                SelfDrawableActivity.class,         // 8 自定义drawable
                CommonImgEffectActivity.class       // 9 浏览图片demo
        };

        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < titleList.length; i++) {
            final int index = i;
            data.add(new HashMap<String, Object>() {{
                put("Title", titleList[index]);
                put("Text", textList[index]);
            }});
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[]{"Title", "Text"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (activityList.length > position) {
                    startActivity(activityList[position]);
                }
            }
        });
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        super.startActivity(intent);
    }
}