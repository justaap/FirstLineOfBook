import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

void main() => runApp(const MyApp()); //单行函数或方法的简写

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'randomWord ListView',
      theme: ThemeData(
        primaryColor: Colors.white,//无效？？
      ),
      home: RandomWords(), //直接托管body
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
        title: Text('Startup Name Generator'),
        //类似actions这种widget，需要一组widgets(children)，用方括号表示,顺序不管
        actions: [
          IconButton(onPressed: _pushSaved, icon: Icon(Icons.list))
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
                 title: Text('Saved Suggestions'),
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
        if (i.isOdd) return Divider();
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

/*
import 'package:flutter/material.dart';

void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        // This is the theme of your application.
        //
        // Try running your application with "flutter run". You'll see the
        // application has a blue toolbar. Then, without quitting the app, try
        // changing the primarySwatch below to Colors.green and then invoke
        // "hot reload" (press "r" in the console where you ran "flutter run",
        // or press Run > Flutter Hot Reload in a Flutter IDE). Notice that the
        // counter didn't reset back to zero; the application is not restarted.
        primarySwatch: Colors.red,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also a layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Invoke "debug painting" (press "p" in the console, choose the
          // "Toggle Debug Paint" action from the Flutter Inspector in Android
          // Studio, or the "Toggle Debug Paint" command in Visual Studio Code)
          // to see the wireframe for each widget.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
          ],
        ),
      ),
      flfoatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
*/
