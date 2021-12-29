package KTPractice

import kotlin.Int as Int1

class OperatorOverride {
}

/**
 * 运算符重载
 * Kotlin的运算符重载允许让任意两个对象进行相加，或进行更多其他的运算操作;
 * 使用方法：在指定函数的前面加上operator关键字，就可以实现运算符重载的功能;
 * */

//语法结构,通过关键字operator重写plus()函数，减号运算符对应的是minus()函数。
//关键字operator和函数名plus是固定不变的，而接收的参数和函数返回值可以根据逻辑自行设定.
class Obj {
    operator fun plus(obj: Obj): Obj {
        //do something
        return obj
    }
}


class Money(val value: Int1){
    //让两个money对象相加
    operator fun plus(money: Money): Money {
        val sum = value + money.value
        return Money(sum)
    }

    //让money和数字相加,初始化后类型为int
    operator fun plus(newValue: Int1): Int1 {
        //修改函数返回值为Int，则此方法初始化的参数类型为Int
        val sum = value + newValue
        return sum
    }
}


fun main() {
    val money1 = Money(5)
    val money2 = Money(7)
    val money3 = money1 + money2//调用第一个方法 money1.plus(money2)
    println("money3.value is ${money3.value}")//12
    println("money3 is $money3")//KTPractice.Money@71be98f5

    val money4 = money3 + 10
    println("money4 is $money4")//22
    println("money4 is Int: "+(money4 is Int1))//true
    println("money3 is Money:"+(money3 is Money))//true
}