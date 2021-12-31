import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'randomWord ListView',
      theme: ThemeData(
        // primaryColor: Colors.green,//primaryColor无效？？
        primarySwatch: Colors.blue,//
      ),
      home: const RandomWords(), //直接托管body
    );

    /*final wordPair = WordPair.random();
    return MaterialApp(
      title: 'welcome to Flutter',
      home: Scaffold(
        appBar:AppBar(
          title: Text('Welcome to Flutter-appbar'),
        ),
        body: Center(
          // child: Text('Hello World'),
          child: Text(wordPair.asCamelCase),
        ),
      ),
    );*/
  }
}

//创建有状态的widget
class RandomWords extends StatefulWidget {
  const RandomWords({Key? key}) : super(key: key);

  @override
  createState() => RandomWordsState();
}

class RandomWordsState extends State<RandomWords> {
  @override
  Widget build(BuildContext context) {
    /*final wordPair = WordPair.random();
    return Text(wordPair.asPascalCase);*/
    return Scaffold(
      appBar: AppBar(
        title: const Text('Startup Name Generator'),
        //像actions这类widget，需要一组widgets(children)，用方括号表示,顺序不管
        actions: [
          IconButton(onPressed: _pushSaved, icon: const Icon(Icons.list))
        ],
      ),
      body: _buildSuggestions(),
    );
  }
  void _pushSaved(){
    //建立一个路由，并推入到导航管理器栈中，然后切换页面显示新路由，
    //新页面在MaterialPageRoute的builder属性中构建；
    Navigator.of(context).push(
        MaterialPageRoute(
            builder: (context){
              //获取红心set中的数据
              final tiles = _saved.map(
                      (pair) {
                    return ListTile(
                      title: Text(
                        pair.asPascalCase,
                        style: _biggerFont,
                      ),
                    );
                  });
              final divided = ListTile
                  .divideTiles(tiles: tiles,context: context)
                  .toList();
              return Scaffold(
                appBar: AppBar(
                  title: const Text('Saved Suggestions'),
                ),
                body: ListView(children:divided),
              );
            })
    );
  }
  /*MaterialPageRoute route() {
    builder:(context){

    };
  }*/

  final _suggestions = <WordPair>[]; //保存单词的列表
  final _biggerFont = const TextStyle(fontSize: 18);
  final _saved = Set<WordPair>(); //用于储存红心词汇
  Widget _buildSuggestions() {
    return ListView.builder(
      padding: const EdgeInsets.all(16),
      itemBuilder: (context, i) {
        //添加分割线
        if (i.isOdd) return const Divider();
        final index = i ~/ 2; //除以2并向下取整
        if (index >= _suggestions.length) {
          //下拉则生成10个单词
          _suggestions.addAll(generateWordPairs().take(10));
        }
        return _buildRow(_suggestions[index]);
      },
    );
  }

  Widget _buildRow(WordPair pair) {
    final _alreadySaved = _saved.contains(pair);
    return ListTile(
      title: Text(
        pair.asPascalCase,
        style: _biggerFont,
      ),
      //添加红心图标
      trailing: Icon(
        _alreadySaved ? Icons.favorite : Icons.favorite_border,
        color: _alreadySaved ? Colors.red : null,
      ),
      //添加点击红心时间
      onTap: (){
        //setState方法会触发build方法，从而更新UI
        setState(() {
          //更新到Set中
          if(_alreadySaved) {
            _saved.remove(pair);
          }else {
            _saved.add(pair);}
        });
      },
    );
  }
}
