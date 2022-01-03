import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

/// 展示Container、Column、Row、Stack、Icon、Expanded、AspectRatio的使用：
/// container的alignment属性；
/// column和row的MainAxisAlignment、CrossAxisAlignment属性；
/// stack的alignment属性,配合positioned类使用；
/// Expanded的flex属性，一般在Row和Column中使用；
/// AspectRatio的aspectRatio属性，一般在container中使用；

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
              //Positioned设置top、left、right的margin属性，约束内容显示（相对于stack中的最大控件）
              //左右或上下同时限制时，不可设置width或height属性，会报错
              Positioned(
                top: 20,
                left: 20,
                right: 20,
                // width: 50,
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
            children: [
              Container(
                child: Icon(Icons.add,size: 60,color: Colors.blue,),
                color: Colors.red,
              ),//图标icon使用
              Container(
                child: Icon(Icons.ac_unit,size: 60,),
                color: Colors.green,
              ),//图标icon使用
              Container(
                child: Icon(Icons.access_alarm,size: 60,),
                color: Colors.blue,
              ),//图标icon使用
            ],
          ),

          //Expanded示例，在row或column中，填充对应的主轴方向内容，宽高度属性不再生效
          //mainAxisAlignment属性也无效，
          //flex相当于weiht属性，设置占比
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Expanded(
                  flex: 2,
                  child:Container(
                    child: Icon(Icons.add,size: 60,color: Colors.blue,),
                    color: Colors.red,
                  )),//图标icon使用
              Expanded(
                  flex: 2,
                  child:Container(
                    child: Icon(Icons.ac_unit,size: 60,),
                    color: Colors.green,
                  )),//图标icon使用
              Expanded(
                  flex: 1,
                  child:Container(
                    child: Icon(Icons.access_alarm,size: 60,),
                    color: Colors.blue,
                  )),//图标icon使用
            ],
          ),

          //crossAxisAlignment属性示例
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
          ),

          //AspectRatio:aspectRatio属性意为宽高比为2，
          //设置父container高为100，则子视图宽为200
          Container(
            color: Colors.red,
            height: 100,
            child: (
                AspectRatio(
                  aspectRatio: 2,
                  child: Icon(Icons.add,size: 100,),
                )
            ),
          )
        ],
      )

    );
  }

}