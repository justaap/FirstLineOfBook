import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class RichTextDemo extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return RichText(
      text:TextSpan(
        text: "Flutter是谷歌出品的移动UI框架",
        style: TextStyle(fontSize: 20,color: Colors.blue[50]),
        children: <TextSpan>[
          TextSpan(
            text: "可以快速在IOS和Android上构建",
            style: TextStyle(fontSize: 40,color: Colors.blue[100]),
          ),
          TextSpan(
            text: "高质量的原生用户界面",
            style: TextStyle(fontSize: 10,color: Colors.blue[200]),
          ),
          TextSpan(
            text: "very very 🐮🍺🐮🍺🐮🍺🐮🍺",
            style: TextStyle(fontSize: 30,color: Colors.blue[400]),
          ),
          TextSpan(
            text: "Flutter可以与现有代码一起工作",
            style: TextStyle(fontSize: 20,color: Colors.blue[800]),
          ),
        ],
      ),
    );
  }
  
}