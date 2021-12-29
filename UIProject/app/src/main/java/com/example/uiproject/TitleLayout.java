package com.example.uiproject;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //from方法构建一个LayoutInflate对象，然后动态加载标题栏布局
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button btn_back = findViewById(R.id.titleBack);
        Button btn_edit = findViewById(R.id.titleEdit);

        //设置返回按钮的点击事件
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //动态获取Activity的实例
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        });

        //
        btn_edit.setOnClickListener(view -> {
            Toast.makeText(context, "编辑", Toast.LENGTH_SHORT).show();
        });
    }
}
