package KTPractice

/**数据类
 * 数据类通常要重写equals() hashCode() toString()方法
 * kotlin通过关键字data可以自动生成这几个方法，减少开发量
 *
 * 单例类
 * 不像java，要把构造函数私有化，kotlin将class关键字改成object即可
 * */

class DataAndSingleton {
}

//通过data，一行代码即可定义数据类，类中没有代码时，可以省略大括号
data class Cellphone(val brand: String, val price: Double)

//定义单例类用object，而不是class，不用私有化构造函数，不用提供getInstance()，调用时按静态方法使用
object Singleton {
    fun singletonTest() {
        println("singletonTest is called")
    }
}

fun main() {
    println("数据类测试")
    val phone1 = Cellphone("oppo", 1000.0)
    val phone3 = Cellphone("oppo", 1000.0)
    val phone2 = Cellphone("oppo", 2000.0)
    println(phone1)//output：Cellphone(brand=oppo, price=1000.0)
    println("phone1 equals phone2 :" + (phone1 == phone2))//output：false
    println("phone1 equals phone3 :" + (phone1 == phone3))//output：true

    println("单例类测试")
    Singleton.singletonTest()//按静态方法使用

}