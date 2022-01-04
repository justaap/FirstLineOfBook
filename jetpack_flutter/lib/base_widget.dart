import 'package:flutter/cupertino.dart';
import '../../layout_demo.dart';
import '../../my_app.dart';
import '../../rich_text.dart';
import '../../stateful_demo.dart';
import '../../wechat_demo/wechat_demo.dart';

import 'list_view_demo.dart';

//存放基础Widget代码
class BaseWidget extends StatelessWidget{
  const BaseWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return WechatDemo();

    return StatefulDemo();

    return LayouDemo();

    return RichTextDemo();

    return ListViewDemo(); //显示ListViewDemo

    return MyApp();

    return Container();
  }
}