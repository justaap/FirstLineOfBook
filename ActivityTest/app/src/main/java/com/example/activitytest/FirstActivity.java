package com.example.activitytest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    Button btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //任何Activity都要重写onCreate()方法
        setContentView(R.layout.first_layout);  //加载布局first_layout

        Button btn1 = findViewById(R.id.button1);   //通过id获取布局文件中的控件
        //设置监听事件
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*显式Intent的使用*/
                //第一个参数：启动Activity的上下文，第二个参数：要启动的目标Activity
                /*Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                startActivity(intent);//将构建好的Intent传入Activity类提供的方法中*/

                /*隐式Intent的使用*/
                //通过注册的action标签内容进行匹配，实现跳转
                Intent intent = new Intent("com.example.activitytest.ACTION_START");
                //隐式Intent可以有多个category，添加一个自定义的category
                intent.addCategory("com.example.activitytest.MY_CATEGORY");
                startActivity(intent);

                //Toast.makeText(FirstActivity.this, "btn1 is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //指定Intent的action为内置动作Intent.ACTION_VIEW（常量值为android.intent.action.VIEW）
                Intent intent = new Intent(Intent.ACTION_VIEW);

                //将网址字符串解析为URI对象
                Uri uri = Uri.parse("https://www.baidu.com");
                /*//data部分中，除了https协议，还有tel协议（打电话），geo协议（显示地理位置）
                Uri uri = Uri.parse("tel:10086");*/

                //将URI对象传递进Intent中
                intent.setData(uri);
                startActivity(intent);
            }
        });

        /*点击按钮3发送数据 todo：加入Intent数据发送后btn1点击闪退崩溃*/
        btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //使用显示Intent启动secondActivity
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                //通过putExtra（）方法输入键和键值，
                String data = "this is a message from firstActivity";
                intent.putExtra("extra_data", data);
//                startActivity(intent);

                //需要目标Activity返回参数时用，其中第二个参数为请求码，只要是唯一值即可，然后在目标Activity中设置响应
                startActivityForResult(intent,1);
            }
        });
    }

    //重写复收端Activity的方法以接受数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //根据自己发出的请求码进行具体任务的处理
        switch (requestCode) {
            case 1:
                //根据复发端Activity的处理结果进行反馈数据的处理
                if (resultCode == RESULT_OK) {
                    //具体获取数据的方式也是一样的，不分发送端or接受端
                    String returnData = data.getStringExtra("data_return");
                    btn3.setText(returnData);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 此方法用于初始化菜单，其中参数menu就是即将要显示的Menu实例;
     * (只会在第一次初始化菜单时调用)
     * Inflate the menu; this adds items to the action bar if it is present.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;    //返回true则显示该menu,false则不显示
    }
    /**
     * 重写onOptionsItemSelected()，定义菜单menu响应事件
     * */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {//获取item的id来绑定响应事件
            case R.id.add_item:
                Toast.makeText(FirstActivity.this, "add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.remove_item:
                Toast.makeText(FirstActivity.this, "remove", Toast.LENGTH_SHORT).show();
                break;
            case R.id.quit_item:
                finish();//销毁Activity
                break;
            default:
        }
        return true;
    }
}