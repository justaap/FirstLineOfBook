import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import 'car.dart';

class ListViewDemo extends StatelessWidget{
  const ListViewDemo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("ListViewDemo"),
      ),
      // 使用ListView
      body:ListView.builder(
        itemCount: cars.length,
        itemBuilder: _listViewItem,
      ),
    );
  }

  Widget _listViewItem(BuildContext context, int index) {
    //Container 可以设置颜色、间距、子控件等
    return Container(
      color: Colors.white,
      margin: const EdgeInsets.all(20),
      //布局类Column 表示上下布局
      child: Column(
          children:[
            Image.network(cars[index].imageUrl),//加载网络图片
            const SizedBox(height: 10,),//调整图文间距1
            Text(cars[index].name),
            Container(height: 10,),//调整图文间距2
          ]
      ),
    );
  }

}