package com.example.uipractice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Msg> msgList;

    public MsgAdapter(List<Msg> msgList) {
        this.msgList = msgList;
    }
//  收到的消息用左holder显示
    static class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView leftMsg;
        public LeftViewHolder(@NonNull View itemView) {
            super(itemView);
            leftMsg = itemView.findViewById(R.id.leftMsg);
        }
    }
//    发出的消息用右holder显示
    static class RightViewHolder extends RecyclerView.ViewHolder {
        TextView rightMsg;
        public RightViewHolder(@NonNull View itemView) {
            super(itemView);
            rightMsg = itemView.findViewById(R.id.rightMsg);
        }
    }

    @Override
    //重写此方法，获取Msg的类型，不同类型创建不同的界面
    public int getItemViewType(int position) {
        Msg msg = msgList.get(position);
        return msg.getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //根据不同viewType创建不同的item界面
        if (viewType == Msg.TYEP_RECEIVED) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msg_left_item, parent, false);
            return new LeftViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.msg_right_item, parent, false);
            return new RightViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Msg msg = msgList.get(position);
//        判断holder是继承自哪一个类
        if(holder instanceof LeftViewHolder){
            //把信息内容写入textView
            ((LeftViewHolder) holder).leftMsg.setText(msg.getContent());
        } else if (holder instanceof RightViewHolder) {
            ((RightViewHolder) holder).rightMsg.setText(msg.getContent());
        } else {
            throw new IllegalStateException("Unexpected value: " + holder);
        }
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
