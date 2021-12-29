package com.example.activitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //获取SecondActivity的Intent
        Intent intent = getIntent();
        /*此处接收字符串数据用getStringExtra()，
        * 接收整型数据，用getIntExtra()，布尔型数据则用getBooleanEctra()...*/
        String data = intent.getStringExtra("extra_data");
        Log.d("secondActivity", data);
        TextView tv1 = findViewById(R.id.tv1);
        tv1.setText(data);

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataBack();
//                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();//无效
        sendDataBack();
    }

    public void sendDataBack() {
        //设置一个空Intent
        Intent intent1 = new Intent();
        //写入数据的操作是一样的，不看是发送端or接受端
        intent1.putExtra("data_return", "this is a message from SecondActivity");
        //调用Activity的setResult方法，第一个参数为处理结果（OK or CANCELED），第二个为带数据的Intent
        setResult(RESULT_OK,intent1);
        //销毁当前Activity
        finish();//如果要按back键实现，重写onBackPressed()实现
    }
}