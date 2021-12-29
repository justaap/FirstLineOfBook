package KTPractice
import kotlin.math.max

fun main() {
    val a = 8
    val b = 9
    val res = largerNumber(a, b)
    println("the larger number is:" + res)
}

/*规则如下：*/
fun methodName(param1: Int, param2: Int): Int {
    return 0
}

/*示例：*/
//fun largerNumber(num1: Int, num2: Int): Int {
//    return max(num1,num2)
//}
/*语法糖：当函数只有一行时，可以不写函数体,并省去返回值显示声明*/
fun largerNumber(num1:Int,num2:Int)= max(num1,num2)
