package KTPractice

class FirstKT {
}

fun main() {
    println("hello kotlin")//末尾不加分号

    val a=10    //类型推导机制：自动推导a为整型变量。声明变量优先用val，不满足需求再用var
    println("val型变量:a=" + a)
    //a=a*20      //val类型变量不可重新赋值，此句报错

    var b: Int = 10 //显示声明变量b为Int类型，kotlin完全使用对象数据类型，Int为一个类（含方法和继承结构）
    b*=10
    println("var型变量:b="+b)
}