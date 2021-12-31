import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// 展示Container、Column、Row、Stack、Icon的使用：
/// container的alignment属性；
/// column和row的MainAxisAlignment、CrossAxisAlignment属性；
/// stack的alignment属性,配合positioned类使用；

class LayouDemo extends StatelessWidget{
  const LayouDemo({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      color: Colors.blue[100],//背景颜色
      alignment: Alignment(0,0), //布局位置在左上角，中间(0,0)
      // child: Text("container"), //内容
      child: Column(//Column为竖向布局
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [

          Stack(//Z轴布局，children中第一个在最底部，
            //alignment控制所有子控件相对于最大子控件的位置
            alignment: Alignment.center,
            children: [
              Container(
                child:  Icon(Icons.access_alarm,size: 150,),
                color: Colors.blue,
              ),
              Container(
                child:  Icon(Icons.ac_unit,size: 100,),
                color: Colors.yellow,
              ),
              //Positioned设置top、left、right的margin属性，约束内容显示（相对于stack的最大控件）
              Positioned(
                top: 20,
                left: 20,
                right: 20,
                child: Container(
                  child:  Icon(Icons.add,size: 50,),
                  color: Colors.red,
                ),
              ),
            ],
          ),

          Row(//Row为横向布局
            //row和column的特有属性：主轴对齐方式，默认children沿着开始方向，此处为平均分配剩下空间
            mainAxisAlignment: MainAxisAlignment.spaceAround,
            children: const [
              Icon(Icons.add,size: 50,),//图标icon使用
              Icon(Icons.ac_unit,size: 60,),
              Icon(Icons.access_alarm,size: 60,),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,//各间隔一致

            //交叉轴对齐方式，start为顶部对齐
            // crossAxisAlignment: CrossAxisAlignment.start,

            //还可以设置按文字对齐,需要配合文字基线使用，否则报错
            crossAxisAlignment: CrossAxisAlignment.baseline,
            textBaseline: TextBaseline.alphabetic,
            children: [
              Container(
                child: Text("一二三四七",style: TextStyle(fontSize: 20),),
                color: Colors.red,
                height: 40,
              ),
              Container(
                child: Text("五六",style: TextStyle(fontSize: 30),),
                color: Colors.green,
                alignment: Alignment(0,0),
                height: 70,
              ),
              Container(
                child: Text("八九十",style: TextStyle(fontSize: 40),),
                color: Colors.blue,
                height: 60,
              ),
            ],
          )
        ],
      )

    );
  }

}