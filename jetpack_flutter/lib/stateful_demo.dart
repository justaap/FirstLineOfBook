import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///有状态的widget，需要继承StatefulWidget，然后返回继承了State<T>类的widget
///调用setState通知UI更新；

class StatefulDemo extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _SDState();
  }
}

class _SDState extends State<StatefulDemo>{

  var _count = 0;
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("状态管理"),
      ),
      body: Center(
        child: Chip(label: Text("点击次数$_count"),),
      ),
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: plusBtnClicked,
      ),
    );
  }

  void plusBtnClicked() {
    _count++;
    print("count = $_count");
    //setState方法更新UI
    setState(() {});
  }
}