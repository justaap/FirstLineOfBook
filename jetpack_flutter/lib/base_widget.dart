import 'package:flutter/cupertino.dart';
import 'package:jetpack_flutter/layout_demo.dart';
import 'package:jetpack_flutter/rich_text.dart';

import 'list_view_demo.dart';

//存放基础Widget代码
class BaseWidget extends StatelessWidget{
  const BaseWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {

    return LayouDemo();

    return RichTextDemo();

    return ListViewDemo(); //显示ListViewDemo

    return Container();
  }
}