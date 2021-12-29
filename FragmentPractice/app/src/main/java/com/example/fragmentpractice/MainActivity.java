package com.example.fragmentpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/**通过限定符，mainActivity根据屏幕宽度加载不同的布局文件activity_main.xml；
 * 不同activity均已静态加载显示新闻标题列表的NewsTitleFragment，其通过recyclerView显示列表，
 * 该碎片自行判断单双页模式，然后通过不同方式跳转到新闻详情页：
 * 一、双页模式，直接将新闻数据传给显示新闻详情页的NewsContentFragment，同页展示；
 * 二、单页模式，启动新的activity，然后在该activity中加载显示新闻详情页的NewsContentFragment，实现换页展示。
 * */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}