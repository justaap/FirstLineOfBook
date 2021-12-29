package com.example.listviewtestproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

//需要继承ArrayAdapter类，并重写getView方法
public class FruitAdapter extends ArrayAdapter<Fruit> {

    private int resourceId;
    public FruitAdapter(@NonNull Context context, int resource, @NonNull List<Fruit> objects) {
        super(context, resource, objects);
        resourceId = resource;//初始化图片源id
    }

    //在每个子项滚动到屏幕内时调用此方法
    public View getView(int position, View convertViwe, ViewGroup parent) {
        Fruit fruit = getItem(position);

        //加载传入的布局
        //通过对convertView进行重用，提高ListView的运行效率
        View view = convertViwe == null ?
                LayoutInflater.from(getContext()).inflate(resourceId, null) : convertViwe;
        //设置图片源和名字
        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
        TextView fruitName = view.findViewById(R.id.fruit_name);
        fruitImage.setImageResource(fruit.getImageId());
        fruitName.setText(fruit.getName());

        return view;
    }
}
