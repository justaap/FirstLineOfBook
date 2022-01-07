import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

///给自定义的item定义手势检测：最外层使用GestureDetector

class MinePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _MinePageState();
  }
}

class _MinePageState extends State<MinePage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      // appBar: AppBar(title:Text("我的")),
      body: Stack(
        children: [
          Container(
            color: Color.fromRGBO(220, 220, 220, 1),
            child: ListView(
              children: [
                const SizedBox(height: 10,),
                ItemCell(imgPath:"images/微信 支付.png",title: "支付", onTap:() => print("支付"),),
                const SizedBox(height: 10,),
                ItemCell(imgPath:"images/微信收藏.png",title: "收藏", onTap:() => print("收藏")),
                Row(children: [Container(color: Colors.white, width: 50, height: 1.5,),]),
                ItemCell(imgPath:"images/朋友圈.png",title: "朋友圈", onTap:() => print("朋友圈")),
                Row(children: [Container(color: Colors.white, width: 50, height: 1.5,),]),
                ItemCell(imgPath:"images/微信卡包.png",title: "卡包", onTap:() => print("卡包")),
                Row(children: [Container(color: Colors.white, width: 50, height: 1.5,),]),
                ItemCell(imgPath:"images/微信表情.png",title: "表情", onTap:() => print("表情")),
                const SizedBox(height: 10,),
                ItemCell(imgPath:"images/微信设置.png",title: "设置", onTap:() => print("设置")),
              ],
            ))
        ],
      ),
    );
  }
}

class ItemCell extends StatefulWidget{
  final String imgPath;
  final String title;
  final GestureTapCallback? onTap;

  const ItemCell({
    Key? key,
    required this.imgPath,
    required this.title,
    this.onTap,
  }) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _ItemCellState();
  }

}
class _ItemCellState extends State<ItemCell> {

  Color _backgroundColor = Colors.white;

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onDoubleTap: () => print("DoubleTap"), //双击
      onLongPress: () => print("LongPress"), //长按
      onTap: (){
        widget.onTap;
        setState(() {
          _backgroundColor = Colors.white;
        });
      },
      onTapDown: (TapDownDetails details){
        setState(() {
          _backgroundColor = Colors.grey;
        });
      },
      onTapCancel: (){
        setState(() {
          _backgroundColor = Colors.white;
        });
      },
      child: Container(
          color: _backgroundColor,
          padding: EdgeInsets.all(10),
          height: 54,
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  Image(
                    image: AssetImage(widget.imgPath),
                    width: 20,
                  ),
                  const SizedBox(
                    width: 15,
                  ),
                  Text(widget.title),
                ],
              ),
              const Image(
                image: AssetImage("images/icon_right.png"),
                width: 20,
              ),
            ],
          )
      ),
    );
  }
}
