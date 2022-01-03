import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MinePage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _MinePageState();
  }

}

class _MinePageState extends State<MinePage>{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(title:Text("我的")),
      body: Center(
        child: Text("聊天记录"),
      ),
    );
  }
}
