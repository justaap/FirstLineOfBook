import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import '../../wechat_demo/chat/chat_page.dart';
import '../../wechat_demo/contacts_page.dart';
import '../../wechat_demo/discover_page.dart';
import 'mine/mine_page.dart';


///仿微信UI
///使用PageView保持状态：1.初始化PageController；2.body中使用PageView;3.点击回调中使用pageController的jumpToPage(index);
///

class WechatDemo extends StatefulWidget{
  const WechatDemo({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _WechatDemoState();
  }
}

class _WechatDemoState extends State<WechatDemo>  {

  int _currentIndex = 3;
  final _pageController = PageController(initialPage: 3);

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
      // body: _pages[_currentIndex],

      //使用IndexedStack，实现子页面保持状态
      /*body: IndexedStack(
        index: _currentIndex,
        children: _pages,
      ),*/

      //使用pageView保持子页面状态
      body: PageView(
        controller: _pageController,
        children:_pages,
        onPageChanged: ((index){
          _currentIndex = index;//滑动page时底部也更新
          setState(() {
          });
        }),
        // physics: NeverScrollableScrollPhysics(),//取消左右滑动效果
      ),
      bottomNavigationBar: BottomNavigationBar(
        //默认为白色背景，修改类型为fix，设置对应的背景色
        type: BottomNavigationBarType.fixed,
        fixedColor: Colors.green,
        backgroundColor: Colors.white,
        items: const [
          BottomNavigationBarItem(
              label: "微信",
              icon: Image(image: AssetImage("images/tabbar_chat.png"),width: 20,height: 20,),
              activeIcon: Image(image: AssetImage("images/tabbar_chat_hl.png"),width: 20,height: 20,)
          ),
          BottomNavigationBarItem(
              label: "通讯录",
              icon: Image(image: AssetImage("images/tabbar_friends.png"),width: 20,height: 20,),
              activeIcon: Image(image: AssetImage("images/tabbar_friends_hl.png"),width: 20,height: 20,),
          ),
          BottomNavigationBarItem(
              label: "发现",
              icon: Image(image: AssetImage("images/tabbar_discover.png"),width: 20,height: 20,),
              activeIcon: Image(image: AssetImage("images/tabbar_discover_hl.png"),width: 20,height: 20,),
          ),
          BottomNavigationBarItem(
              label: "我",
              icon: Image(image: AssetImage("images/tabbar_mine.png"),width: 20,height: 20,),
              activeIcon: Image(image: AssetImage("images/tabbar_mine_hl.png"),width: 20,height: 20,),
          ),
        ],
        selectedFontSize: 12,//设置选中大小与unselectedFontSize默认值一样为12，取消选中变大效果
        unselectedFontSize: 12 ,
        currentIndex: _currentIndex,
        onTap: (index){
          setState(() {
            _currentIndex = index;
            _pageController.jumpToPage(index);
          });
        },
      ),
    );
  }

}