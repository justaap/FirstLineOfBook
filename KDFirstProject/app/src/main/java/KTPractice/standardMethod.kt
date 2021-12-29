package KTPractice

import kotlin.text.StringBuilder

class standardMethod {
}

fun main() {
    val list= listOf("apple","banana","orange","pear","grape")
    traditionalTest(list)

    //with方法测试
    withTest(list)

    //run方法测试
    runTest(list)

    //apply方法测试
    applyTest(list)

}

fun applyTest(list: List<String>) {
    println("\napply函数打印：")
    //和run函数类似，不过返回值是对象本身
    val result=StringBuilder().run{
        append("Start eating fruits.\n")
        for (fruit in list) {
            append(fruit+ "\n")
        }
        append("fruits were all eaten")
    }
    println(result.toString())//返回值为StringBuilder
}

fun runTest(list:List<String>) {
    println("\nrun函数打印：")
    //和with函数类似，不过是在对象的基础上直接调用
    val result=StringBuilder().run{
        append("Start eating fruits.\n")
        for (fruit in list) {
            append(fruit+ "\n")
        }
        append("fruits were all eaten")
        toString()//最后一行就是返回值
    }
    println(result)
}

fun withTest(list:List<String>) {
    //频繁调用StringBuilder的多个方法，可用with函数简化
    println("\nwith函数打印：")
    //声明对象（参数一）并直接调用方法（参数二）
    val result = with(StringBuilder()) {
        //lambda中直接调用StringBuilder()的各个方法
        append("Start eating fruits.\n")
        for (fruit in list) {
            append(fruit+ "\n")
        }
        append("fruits were all eaten")
        toString()//最后一行就是返回值
    }
    println(result)
}

fun traditionalTest(list:List<String>) {
    //将所有水果字符串连接在一起
    println("传统方式打印:")
    val builder1 = StringBuilder()
    builder1.append("Start eating fruits.\n")
    for (fruit in list) {
        builder1.append(fruit + "\n")
    }
    builder1.append("fruits were all eaten")
    println(builder1.toString())
}