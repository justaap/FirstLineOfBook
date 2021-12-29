package com.example.kdfirstproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO：日志级别从低到高：verbose，debug，info，warn，error
        Log.d("MainActivity", "onCreate execute");
    }
}