package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = findViewById(R.id.edit_text);
        Button btn = findViewById(R.id.btn);

        WebView webView = findViewById(R.id.webView);
        //设置为支持javaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //当网页跳转时，仍在当前webView显示，而不是打开系统浏览器
        webView.setWebViewClient(new WebViewClient());
        //根据网址字符串跳转网页
        webView.loadUrl("https://www.kuaidi100.com");

        btn.setOnClickListener(view -> {
            StringBuilder builder = new StringBuilder("https://");
            builder.append(editText.getText().toString());
            //根据网址字符串跳转网页
            webView.loadUrl(builder.toString());
        });

        //跳转到HttpURLConnection测试界面
        Button bnt1 = findViewById(R.id.btn1);
        bnt1.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this,HttpURLConnectionTest.class));
        });
    }
}