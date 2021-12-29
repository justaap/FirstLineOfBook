package com.example.listviewtestproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"苹果","香蕉","橘子","西瓜","雪梨","葡萄","菠萝",
            "草莓","樱桃","芒果","苹果1","香蕉1","橘子1","西瓜1",
            "雪梨1","葡萄1","菠萝1","草莓1","樱桃1","芒果1"};
    List<Fruit> fruitList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化适配器：绑定显示平台（页面）、显示效果和显示内容
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);

        //1数据初始化
        fruitListInit();
        //2适配器绑定数据
        FruitAdapter adapter = new FruitAdapter(this,R.layout.fruit_item,fruitList);
        //3将listView和适配器绑定
        ListView listView = findViewById(R.id.listview1);
        listView.setAdapter(adapter);
        //4设置ListView的点击事件(还可以设置长按、选择事件)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Fruit fruit = fruitList.get(position);//通过positon确认点击的item
                Toast.makeText(MainActivity.this,fruit.getName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fruitListInit() {
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