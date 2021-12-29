package KTPractice

class LogicalControl {
    /*if条件语句*/
    //与java类似
    fun largerNumber(num1: Int, num2: Int): Int {
        var res=0
        if (num1 > num2) {
            res = num1
        } else {
            res = num2
        }
        return res
    }
    /*语法糖*/
    fun largerNumberSugar(num1: Int, num2: Int) = if (num1 > num2) num1 else num2

    /*when条件语句*/
    //类似java的switch语句,结尾一定要有else
    fun getScroe(name: String) = when (name) {
        "Tom" -> 88
        "Jarry" -> 70
        "lily" -> 74
        else -> "sorry，there is no one called " + name  //结尾 else 必需
    }
    //when还可以匹配数据类型，
    /*Number是kt的内置抽象类，子类有
    * Byte，8位，
    * Short，16位，
    * Int，32位，123
    * Long，64位，123L
    * Float，32位，123F or 123f
    * Double，64位，12.3 */
    fun checkNumber(num: Number) {
        print("number is ")
        when (num) {
            /*is类似java的instanceof*/
            is Int -> println("Int")
            is Double -> println("Double")
            else-> println("not supported")
        }
    }

}

fun main() {
    val lc = LogicalControl() //类的初始化，同样自动类型推导

    println("/* if 语句测试*/")
    val a = 8
    val b = 1
    println("the larger number is:" + lc.largerNumber(a, b))
    println("gramer sugar: the larger number is:" + lc.largerNumberSugar(a, b))

    println("\n/* when 语句测试*/")
    println("Tom's score is:"+lc.getScroe("Tom"))
    println("Tom's score is:"+lc.getScroe("tom"))   //查无此人
    lc.checkNumber(2)       //Int
    lc.checkNumber(2.2)     //Double
    lc.checkNumber(2L)      //Long不被支持
    lc.checkNumber(123F)    //Float不被支持

}


