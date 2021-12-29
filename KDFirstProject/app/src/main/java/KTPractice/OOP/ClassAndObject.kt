package KTPractice.OOP
/**类与对象的示例
 * 定义一个person类并实例化，调用其方法和属性*/
class ClassAndObject {
}

class Person {
    var name = ""
    var age = 0
    fun eat() {
        println(name + " is eating. He is " + age + " years old.")
    }
}

fun main() {
    /**与普通参数定义类似，类的实例化也是用val或者var，并且不用new关键字*/
    val p = Person()
    /**类的属性定义*/
    p.age = 18
    p.name = "Tom"
    /**类的方法调用*/
    p.eat()
}