import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class ContactsPage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _ContactsPageState();
  }

}

class _ContactsPageState extends State<ContactsPage>{
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromRGBO(220, 220, 220, 1),
        elevation: 0.0,
        title:const Text(
          "通讯录",
          style: TextStyle(fontSize: 18,color: Colors.black),
        ),
        centerTitle: true,
      ),
      body: const Center(
        child: Text("聊天记录"),
      ),
    );
  }
}
