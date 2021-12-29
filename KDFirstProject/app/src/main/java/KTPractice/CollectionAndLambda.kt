package KTPractice

class CollectionKt {
}

fun main() {
    /**list集合初始化：
     * 通过listOf()快速初始化一个不可变集合；
     * 可变集合用mutableListOf()
     *
     * set集合，不可存放重复元素
     * 用法与list集合一样，用setOf()和mutableSetOf()初始化
     *
     * map集合
     * 以键值对形式存数据
     * 不建议和java那样用put()/get()进行数据操作
     *  */
    println("不可变集合初始化listOf()")
    //创建的listConstant为不可变集合，无法增删改
    val listConstant = listOf("apple", "banana", "orange", "pear", "grape")
    //for-in 循环还可以遍历集合
    for (fruit in listConstant) println(fruit)

    println("可变集合初始化mutableListOf()")
    //listMutable为可变集合，可以增删改
    val listMutable = mutableListOf("apple", "banana", "orange", "pear")
    listMutable.add("grape")//添加“葡萄”
    listMutable.remove("apple")//删除“苹果”
    for (fruit in listMutable) println(fruit)

    println("map集合初始化")
    //map集合初始化方法一
    /*val map = HashMap<String, Int>()
    map.put("apple", 1)//java添加键值的方法，不建议使用
    map["banana"] = 2//kotlin添加键值的方法，推荐
    map["orange"] = 3*/

    //map集合初始化方法二
    val map = hashMapOf("apple" to 1, "banana" to 2, "orange" to 3, "pear" to 4)
    map["grape"] = 5    //hashMapOf方式初始化的map是可变的,但mapOf()初始化是不可变的
    for ((fruit, index) in map) {
        println("fruit is $fruit, index is $index")
    }


    /**lambda表达式
     * 语法结构：{param1：paramType1，param2：paramType2 -> function}
     * 参数列表的结尾以一个“->“符号结束，同时为函数体的开始，最后一行代码会作为返回值；
     * */

    println("\nlambda语法简化过程")
    val list = listOf("apple", "banana", "orange", "pear", "grape", "watermelon")
    /*lambda由繁到简--寻找最长名字的水果*/
    //1.按照语法结构定义一个lambda参数
    /*val lambda = { fruit: String -> fruit.length }//定义一个lambda参数
    val maxLenthFruit = list.maxByOrNull(lambda)//返回最长字符串*/
    //2.不需要专门定义一个lambda变量，直接将表达式传入函数中
    /*val maxLenthFruit = list.maxByOrNull({ fruit: String -> fruit.length })*/
    //3.kotlin规定，lambda参数时函数的最后一个参数时，可将表达式移到函数括号外面
    /*val maxLenthFruit = list.maxByOrNull() { fruit: String -> fruit.length }*/
    //4.如果lambda参数时唯一一个参数，可将函数名后的括号省略
    /*val maxLenthFruit = list.maxByOrNull { fruit: String -> fruit.length }*/
    //5.当lambda表达式的参数列表只有一个参数时，不用声明参数名，用it关键字代替
    val maxLenthFruit = list.maxByOrNull { it.length }
    //打印最长名字的水果
    println(maxLenthFruit)

    println("转换为大写：")
    //把所有水果名称转换为大写
    //函数式api：map()函数将集合中每个元素映射为一个另外的值，生成一个新的集合
    val newList = list.map { it.uppercase() }
    for (fruit in newList) println(fruit)

    println("过滤长度大于5的字符，并转为大写：")
    //函数式api：filter()过滤集合中的数据
    val shortList = list.filter { it.length < 6 }.map { it.uppercase() }//先过滤再转换，效率高
    for (fruit in shortList) println(fruit)

    //函数式api：any()判断集合中是否至少存在一个元素满足条件
    //函数式api：all()判断是否所有元素都满足条件
    val anyResult = list.any { it.length < 6 }
    val allResult = list.all { it.length < 6 }
    println("anyResult:$anyResult, allResult:$allResult")


    println("\n调用java的函数式APi")
    /**调用java的函数式APi
     * 当java单抽象方法接口（只有一个待实现方法）只接收一个参数，
     * 如果接口中有多个待实现方法，无法使用；
     * */
    //1.调用java线程类Thread，kotlin创建匿名类实例用object关键字（不是new）
    Thread(object : Runnable {
        override fun run() {
            println("Thread1 is running...")
        }
    }).start()
    //2.Thread类的构造方法符合java函数式api的使用条件，可简化为
    Thread(Runnable {
        println("Thread2 is running")
    }).start()
    //3.如果java方法的参数列表中只有一个java单抽象方法接口参数，可省略接口名
    Thread({
        println("Thread3 is running")
    }).start()
    //4.当lambda表达式是方法的最后一个参数时，可将表达式移到方法括号外，表达式是方法的唯一参数，可省略方法括号
    Thread{
        println("Thread4 is running")
    }.start()

}