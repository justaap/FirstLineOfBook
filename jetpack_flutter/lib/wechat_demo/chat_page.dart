import 'package:flutter/cupertino.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class ChatPage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _ChatPageState();
  }

}

class _ChatPageState extends State<ChatPage>{

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromRGBO(220, 220, 220, 1),
        shadowColor: Color.fromRGBO(0, 0, 0, 0),//控制阴影方法1
        elevation: 0.0,//控制阴影方法2
        title:Text(
          "微信",
          style: TextStyle(fontSize: 18,color: Colors.black),
        ),
        centerTitle: true,),
      body: Center(
        child:Text.rich(
            TextSpan(
              text: "聊天记",
            )
        )
      ),
    );
  }
}
