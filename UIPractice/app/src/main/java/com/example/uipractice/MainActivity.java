package com.example.uipractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private MsgAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        1.数据初始化
        initMsg();
//        2.线性布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);//设置布局方式
        //3.绑定adapter
        adapter = new MsgAdapter(msgList);
        recyclerView.setAdapter(adapter);

        Button sendBtn = findViewById(R.id.send_btn);
        EditText inputText = findViewById(R.id.inputText);
        sendBtn.setOnClickListener(view -> {
            String content = inputText.getText().toString();
            if (!content.isEmpty()) {
                Msg msg = new Msg(content, Msg.TYEP_SEND);//新建发送类型的消息
                msgList.add(msg);
                if (adapter != null) {
                    //在末端插入一条信息
                    adapter.notifyItemInserted(msgList.size() - 1);
                }
                //滚动到最后一条信息
                recyclerView.scrollToPosition(msgList.size() - 1);
                inputText.setText("");//清空输入框
            }
        });
        inputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.scrollToPosition(msgList.size() - 1);
            }
        });
    }

    private void initMsg() {
        Msg msg = new Msg("This is Jerry, nice to talking to you.", Msg.TYEP_RECEIVED);
        msgList.add(msg);
        Msg msg1 = new Msg("Hello guy", Msg.TYEP_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("Hello, who is that?", Msg.TYEP_SEND);
        msgList.add(msg2);
        Msg msg3 = new Msg("This is tom, nice to talking to you.", Msg.TYEP_SEND);
        msgList.add(msg3);
    }
}