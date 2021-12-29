package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Fruit> fruitList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.数据初始化
        fruitsInit();
        //2.指定recyclerView的布局方式为线性布局,
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        //设置为水平滚动展示
        //此外还有GridLayoutManager(网格布局)和StaggeredGridLayoutManager(瀑布路布局)两种排列方式
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        //3.绑定adapter和数据
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);


    }
    public void fruitsInit() {
        fruitList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fruitList.add(new Fruit("苹果", R.drawable.apple_pic));
            fruitList.add(new Fruit("香蕉", R.drawable.banana_pic));
            fruitList.add(new Fruit("橘子", R.drawable.orange_pic));
            fruitList.add(new Fruit("西瓜", R.drawable.watermelon_pic));
            fruitList.add(new Fruit("雪梨", R.drawable.pear_pic));
            fruitList.add(new Fruit("葡萄", R.drawable.grape_pic));
            fruitList.add(new Fruit("菠萝", R.drawable.pineapple_pic));
            fruitList.add(new Fruit("草莓", R.drawable.strawberry_pic));
            fruitList.add(new Fruit("樱桃", R.drawable.cherry_pic));
            fruitList.add(new Fruit("芒果", R.drawable.mango_pic));
        }
    }
}