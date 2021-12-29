package com.example.recyclerviewtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private List<Fruit> fruitList;

    //构造函数中初始化数据源
    public FruitAdapter(List<Fruit> fruitList) {
        this.fruitList = fruitList;
    }

    //定义一个内部类
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
        View fruitView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.fruit_image);
            fruitName = itemView.findViewById(R.id.fruit_name);
            fruitView = itemView;
        }
    }

    @NonNull
    @Override
    //创建ViewHolder实例
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //首先加载fruit_item布局
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item_vertical, parent, false);
        ViewHolder holder = new ViewHolder(view);
        //设置点击一个item的点击事件
        holder.fruitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //调用方法获取点击的位置
                int position = holder.getAdapterPosition();
                Fruit fruit = fruitList.get(position);
                Toast.makeText(view.getContext(),fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
        //设置点击一个item中图片的点击事件
        holder.fruitImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Fruit fruit = fruitList.get(position);
                Toast.makeText(view.getContext(),
                        fruit.getName()+"的图",Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    //给recyclerView子项的数据赋值，被滑到屏幕内时执行
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = fruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    @Override
    //返回总条目数
    public int getItemCount() {
        return fruitList.size();
    }

}
