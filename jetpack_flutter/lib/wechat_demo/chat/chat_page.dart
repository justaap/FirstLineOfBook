import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import '../../wechat_demo/chat/chat_item.dart';
import '../../wechat_demo/discover_page.dart';

import '../../main.dart';
/// PopupMenuButton的使用
/// 网络请求：在initState发起请求、解析数据，2种更新UI：1.FutureBuilder；2.使用async回调then
/// 保持Widget的状态一：1。AutomaticKeepAliveClientMixin；2.bool get wantKeepAlive => true; 3.super.initState();4.父页面body用Indexedstack
/// 保持Widget的状态二：
class ChatPage extends StatefulWidget{

  @override
  State<StatefulWidget> createState() {
    return _ChatPageState();
  }
}

class _ChatPageState extends State<ChatPage> {
/*class _ChatPageState extends State<ChatPage> with AutomaticKeepAliveClientMixin{

  @override
  bool get wantKeepAlive => true;*/

  List<ChatItem> _chatList = [];
  bool _cancelConnect = false;
  //网络请求发送一般写在initState中
  @override
  void initState() {
    super.initState();
    //通过then实现回调
    getData().then((value) => {
      if(!_cancelConnect){
        setState((){
          _chatList = value!;
        })
      }
    }).catchError((onError){
      print(onError);
    }).whenComplete(
            () => print("完成")
    ).timeout(Duration(milliseconds: 1000)).catchError((onError){
      _cancelConnect = true;//超时取消刷新
      print("timeoutError: ${onError}");
    });
    print("请求");
  }

  /// {
  ///   "chat_list": [
  ///     {
  ///       "imageUrl": "https://randomuser.me/api/portraits/women/@natural(20,99).jpg",
  ///       "name": "@cname",
  ///       "message": "@cparagraph"
  ///     }
  ///   ]
  /// }
  //发起请求
  Future<List<ChatItem>?> getData() async{
    _cancelConnect = false;
    var response = await http.get(Uri.parse("http://rap2api.taobao.org/app/mock/data/2178507"));
    print("response.body is ${response.body}");

    //解析响应数据
    if(response.statusCode == 200) {
      final responseMap = json.decode(response.body);
      List<ChatItem> chatList = responseMap["chat_list"].map<ChatItem>(
          (item){
            return ChatItem.fromMap(item);
          }
      ).toList();
      return chatList;
    }else{
      throw Exception("statusCode: ${response.statusCode}");
    }
  }

  @override
  Widget build(BuildContext context) {
    // super.build(context);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromRGBO(220, 220, 220, 1),
        shadowColor: Color.fromRGBO(0, 0, 0, 0),//控制阴影方法1
        elevation: 0.0,//控制阴影方法2
        title:const Text(
          "微信",
          style: TextStyle(fontSize: 18,color: Colors.black87),
        ),
        centerTitle: true,
        actions: [
          _container()
        ],
      ),
      //网络请求中，使用FutureBuilder更新UI
      body: FutureBuilder(
        future: getData(),
        builder: (BuildContext context, AsyncSnapshot snapshot) {
          if(snapshot.connectionState == ConnectionState.done) {
            if(snapshot.hasError) {
              return Center(child: Text("Error:${snapshot.error}"),);
            }else{
              return ListView(
                children: snapshot.data.map<Widget>((item) {
                  return ListTile(
                    title: Text(item.name),
                    subtitle: Container(
                      height: 20,
                      width: 20,
                      child: Text(item.message,overflow: TextOverflow.ellipsis,),
                    ),
                    leading: CircleAvatar(backgroundImage: NetworkImage(item.imageUrl),),
                  );
                }).toList(),
              );
            }
          }else{
            return Center(child: CircularProgressIndicator(),);
          }
        },
      ),

      //不使用FutureBuilder更新UI
      /*body: Container(
        child: _chatList.length == 0
            ?Center(child: CircularProgressIndicator(),)
            :ListView.builder(
              itemCount:_chatList.length,
              itemBuilder:(BuildContext context,int index){
                return ListTile(
                  leading: CircleAvatar(backgroundImage: NetworkImage(_chatList[index].imageUrl),),
                  title: Text(_chatList[index].name),
                  subtitle: Text(_chatList[index].message,overflow: TextOverflow.ellipsis,),
                );
              },
          )
      ),*/

    );
  }

  //右上角使用popupMenuButton
  Container _container(){
    return Container(
      margin: EdgeInsets.only(right: 10),//右边margin10
      child: PopupMenuButton(
        color: Colors.grey,
        onSelected: (item){
          print("item is $item"); //此处的item与PopupMenuItem的value对应，value为空此处无效
        },
        onCanceled: (){
          print("onCanceled");//
        },
        offset: Offset(-20,50),//偏移：左20、下50
        child: const Image(
          image: AssetImage('assets/images/圆加.png'),
          width: 25,
          height: 25,
        ),//加载本地图片文件
        // child: Icon(Icons.add,size: 35, color: Colors.black54,),
        itemBuilder: (BuildContext context) {
          return <PopupMenuItem>[
            PopupMenuItem(
                value: {"imageName":" name","title":"tit"},
                onTap: (){
                  print("item1 tap");
                  /*Navigator.of(context).push(
                      MaterialPageRoute(builder: (BuildContext context){
                        return DiscoverPage();
                      })
                  );*/
                },
                child: Row(children: const [
                  Image(image: AssetImage('assets/images/发起群聊.png'),width: 25,),
                  // Icon(Icons.add_rounded,size: 35, color: Colors.black54,),
                  SizedBox(width: 20),
                  Text("layoutDemo",style: TextStyle(color: Colors.white),),
                ],)
            ),
            PopupMenuItem(
                value: Text("第二行值"),
                child: Row(children: const [
                  Image(image: AssetImage('assets/images/添加朋友.png'),width: 25,),
                  // Icon(Icons.add_rounded,size: 35, color: Colors.black54,),
                  SizedBox(width: 20),
                  Text("layoutDemo",style: TextStyle(color: Colors.white),),
                ],)
            ),
          ];
        },
      ),
    );
  }

}
