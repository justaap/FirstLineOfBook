import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:jetpack_flutter/wechat_demo/chat_page.dart';
import 'package:jetpack_flutter/wechat_demo/contacts_page.dart';
import 'package:jetpack_flutter/wechat_demo/discover_page.dart';
import 'package:jetpack_flutter/wechat_demo/mine_page.dart';


///仿微信UI
///

class WechatDemo extends StatefulWidget{
  @override
  State<StatefulWidget> createState() {
    return WechatRoot();
  }
}

class WechatRoot extends State<WechatDemo>{
  var _currentIndex = 0;
  //初始化页面缓存
  final List<Widget> _pages=[
    ChatPage(),
    ContactsPage(),
    DiscoverPage(),
    MinePage()
  ];
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: _pages[_currentIndex],
      bottomNavigationBar: BottomNavigationBar(
        //默认为白色背景，修改类型为fix，设置对应的背景色
        type: BottomNavigationBarType.fixed,
        fixedColor: Colors.green,
        backgroundColor: Colors.grey,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.chat),label: "微信"),
          BottomNavigationBarItem(icon: Icon(Icons.bookmark),label: "通讯录"),
          BottomNavigationBarItem(icon: Icon(Icons.history),label: "发现"),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline),label: "我"),
        ],
        selectedFontSize: 12,//设置选中大小与unselectedFontSize默认值一样为12，取消选中变大效果
        unselectedFontSize: 12 ,
        currentIndex: _currentIndex,
        onTap: (index){
          _currentIndex = index;
          setState(() {});
        },
      ),
    );
  }

}