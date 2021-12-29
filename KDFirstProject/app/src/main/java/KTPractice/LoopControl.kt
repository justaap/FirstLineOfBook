package KTPractice

/*循环有while循环和for循环两种
* kotlin的while循环和java的while无区别
* for循环修改比较大*/

fun main() {
    /*0..5：表示0到5的闭区间（只能升序，默认步长为1）*/
    println("闭区间示例")
    for (i in 0..5)  println(i)

    /*左开右闭区间用关键字until（只能升序）*/
    println("左开右闭区间示例")
    for (i in 3 until 5) println(i)

    /*改变默认步长为1用关键字step（升降序均可用）*/
    println("自定义步长示例")
    for (i in 0..5 step 2) println(i)

    /*区间降序用关键字downTo*/
    println("闭区间降序示例")
    for (i in 5 downTo 1) println(i)
}

class LoopControl {
}
