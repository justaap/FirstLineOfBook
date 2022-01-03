import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class DiscoverPage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _DiscoverPageState();
  }

}

class _DiscoverPageState extends State<DiscoverPage>{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromRGBO(220, 220, 220, 1),
        title:const Text(
          "发现",
          style: TextStyle(fontSize: 18,color: Colors.black),
        ),
        centerTitle: true,),
      body: const Center(
        child: Text("聊天记录"),
      ),
    );
  }
}
