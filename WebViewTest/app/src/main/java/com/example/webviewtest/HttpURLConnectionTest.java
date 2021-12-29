package com.example.webviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpURLConnectionTest extends AppCompatActivity {

    TextView tv_url;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_urlconnection_test);

        tv_url = findViewById(R.id.tv_url);
        et = findViewById(R.id.et_http_url);
        Button btn = findViewById(R.id.btn_url);
        btn.setOnClickListener(view -> {
//            sendRequestWithHttpURLConnection();
            sendRequestWithOkHttp();//已经格式化，显示效果比较好
        });

    }

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //1.新建OkHttp实例
                    OkHttpClient client = new OkHttpClient();
                    //2.发送http请求，需创建Request对象
                    Request request = new Request.Builder()
                            .url("http://" + et.getText().toString())
                            .build();
                    //3.调用newCall方法创建一个Call对象，
                    // 调用其execute方法发送请求，获取服务器返回的数据
                    Response response = client.newCall(request).execute();//需捕捉异常
                    String responseData = response.body().string();

                    Log.d("MainActivity", "开始parse "+responseData);
                    showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private void sendRequestWithHttpURLConnection() {
//        发起网络请求需要开启线程
        new Thread(new Runnable() {
            HttpURLConnection connection;
            @Override

            public void run() {
                try {
                    //1.创建实例
                    URL url = new URL("https://" + et.getText().toString());
                    connection = (HttpURLConnection) url.openConnection();
                    //2.设置请求所使用的方法（GET和POST）
                    connection.setRequestMethod("GET");
                    //3.自由定制（连接、读取超时的毫秒数，服务器希望得到的一些消息头等）
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //4.调用getInputStream方法获取服务器返回的输入流
                    InputStream input = connection.getInputStream();
                    showResponse(responseToStr(input));//将输入流转码并显示

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //5.调用disconnect方法关闭HTTP连接
                    connection.disconnect();
                }
            }
        }).start();
    }

    //新建线程更行ui以显示内容
    private void showResponse(String response) {
        //Android不允许在子线程中进行UI操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_url.setText(response);//更新testView中的内容
            }
        });
    }

    //将输入流转换为字符串
    private String responseToStr(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(input));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);//逐行读取并保存
        }
        return response.toString();
    }
}