package com.example.activitylifecycletest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    //    private final String tag="MainActivity";
    private final String tag = getClass().getSimpleName();//获取当前Activity的类名

    /*onCreate()，在Activity第一次被创建的时候调用。完成Activity的初始化操作：加载布局、绑定事件等；*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(tag, "onCreate");
        setContentView(R.layout.activity_main);

        //恢复onSaveInstanceState()方法中保存的数据
        if (savedInstanceState != null) {
            String tempData = savedInstanceState.getString("data_key");
            Log.d(tag, "tempData is " + tempData);
        }

        Button normalBtn = findViewById(R.id.startNormalActivity);
        Button dialogBtn = findViewById(R.id.startDialogActivity);

        normalBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, NormalActivity.class)));
        dialogBtn.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this,DialogActivity.class)));
    }


    /*Activity被回收：按Back键返回此Activity，或者屏幕旋转的时候，走onCreate而不是onRestart，因此需要保存一些数据在此*/
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String tempData = "Something you just typed";
        outState.putString("data_key",tempData);//保存字符串（键值形式）
    }

    /*onStart()，在Activity由不可见到可见的时候调用；*/
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(tag, "onStart");
    }

    /*onResume()，在Activity准备好和用户交互的时候调用。
    * 此时Activity位于栈顶，是运行状态；
    * */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(tag, "onResume");
    }

    /*onPause()，系统去启动或恢复另一个Activity的时候调用（比如有小窗或者对话框弹出）。
    * 通常释放消耗CPU的资源、保存关键数据；
    * 且要求执行速度要快，否则影响新Activity的使用；*/
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(tag, "onPause");
    }

    /*onStop()，在Activity完全不可见的时候调用。
    * 与onPause()区别：启动一个对话框式的Activity时，只会执行onPause()；
    * */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(tag, "onStop");
    }

    /*onDestroy()，在Activity被销毁前调用；*/
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(tag, "onDestroy");
    }

    /*onRestart()，在Activity由停止变为运行状态前调用；*/
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(tag, "onRestart");
    }
}
