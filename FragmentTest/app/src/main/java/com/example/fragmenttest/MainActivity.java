package com.example.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.SurfaceControl;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.cert.TrustAnchor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //直接获取activity内fragment中的按钮
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(view -> {
            replaceFragment(new AnotherRightFragment());
        });
        replaceFragment(new RightFragment());

    }

    //动态添加、修改fragment
    private void replaceFragment(Fragment Fragment) {
//        获取fragmentManager,还可以通过findFragmentById方法精准获取
        FragmentManager fragmentManager = getSupportFragmentManager();
//        开启一个事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        向容器中替换fragment
        transaction.replace(R.id.rightLayout, Fragment);
//        将事务添加到返回栈
        transaction.addToBackStack(null);
//        提交事务
        transaction.commit();
    }

}