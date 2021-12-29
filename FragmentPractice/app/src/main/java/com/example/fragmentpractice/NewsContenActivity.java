package com.example.fragmentpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 单页面时调用此Activity，然后动态加载NewsContentFragment显示新闻详情页；
 * 双页面则直接使用NewsContentFragment显示
 * */
public class NewsContenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_conten);

//        获取新闻详情
        String title = getIntent().getStringExtra("news_title");
        String content = getIntent().getStringExtra("news_content");
//        刷新界面
        if (title != null && content != null) {
//            复用fragment显示新闻详情
            NewsContentFragment fragment = (NewsContentFragment)
                    getSupportFragmentManager().findFragmentById(R.id.newsContentFrag);
            fragment.refresh(title,content);
        }
    }

    //外部调用，打包数据发送回此Activity
    public static void actionStart(Context context, String title, String content) {
        Intent intent = new Intent(context, NewsContenActivity.class);
        intent.putExtra("news_title", title);
        intent.putExtra("news_content", content);
        context.startActivity(intent);
    }
}