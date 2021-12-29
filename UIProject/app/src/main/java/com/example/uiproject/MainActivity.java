package com.example.uiproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

/*实现View的OnClickListener接口，重写onClick方法，方便设置activity内各控件的点击事件；
* 然后注册点击事件
* 然后重写onClick方法*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et1 ;
    Button btn1;
    ImageView iv ;
    ProgressBar pb;
    ProgressBar pb1;
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //隐藏默认的标题栏
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        et1 = findViewById(R.id.et1);
        btn1 = findViewById(R.id.btn1);
        iv = findViewById(R.id.imageView1);
        pb = findViewById(R.id.progressBar);
        pb1 = findViewById(R.id.progressBar1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(this);//注册点击监听
        btn2.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                String inputText = et1.getText().toString();    //获取EditText的内容
                if(inputText.isEmpty()) inputText = et1.getHint().toString();   //获取EditText的提示内容
                Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show();
                iv.setImageResource(R.drawable.img_3);  //动态更改ImageView中的图片

                //设置progressBar的可见性，Visible（默认）、gone（完全消失）、invisible（不可见，但占位）三种，
                if (pb.getVisibility() == View.VISIBLE) {
                    pb.setVisibility(View.GONE);
                } else {
                    pb.setVisibility(View.VISIBLE);
                }

                //条形progressBar
                if(pb1.getProgress()==100) pb1.setProgress(0);//满了就归零
                pb1.setProgress(pb1.getProgress()+10);//设置每次进度+10
                break;

                /*设置提示框（无需在xml页面设置），*/
            case R.id.btn2:
                //首先实例化一个提示框builder
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("提示框标题");//设置提示框标题
                dialog.setMessage("提示框内容");//设置提示框内容
                dialog.setCancelable(false);//点击空白处可关闭提示框
                //设置取消键的点击响应事件
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                //设置确认键的点击响应事件
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();//提示框显示
                break;

            default:
                break;
        }
    }
}