package KTPractice.OOP

import kotlin.math.sign

/**继承和构造函数的示例：
 * student类继承human类
 * 1.使human类可继承（kt中非抽象类默认不可被继承，相当于声明了final）
 * 2.通过冒号":“实现继承（相当于java的extends）*/
class InheritAndConstructor {
}

/*通过关键字open使Human类可被继承*/
open class Human {
    var name = ""
    var age = 0
    fun eat() {
        println(name + " is eating. He is " + age + " years old.")
    }
    //含参的构造函数
    fun Human(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}

/**summary
 * 继承语法：通过冒号":“实现，父类后面需要加括号（即构造函数）；
 * 构造函数分“主构造函数”和“次构造函数”；
 * 主构造函数：没有函数体，一个类只能有一个主构造函数，
 * 次构造函数：通过关键字constructor定义，有函数体，一个类可以有多个次构造函数；
 */

/*继承语法：通过冒号":“实现，父类后面需要加括号（即构造函数）；
* 构造函数分“主构造函数”和“次构造函数”；*/
class Student1: Human() {//调用无参构造函数
    var sId = ""
    var grade = 0
}

/*主构造函数：没有函数体，一个类只能有一个主构造函数
* 如果要在主构造函数中写逻辑，通过关键字init实现*/
class Student2(val sId: String, val grade: Int) : Human() {
    //init 非必需
    init {
        println("sId is " + sId)
        println("grade is $grade")//语法糖,用$直接使用变量
    }
}

/*主构造函数中引入参数，
* 则继承时需要调用含参的构造函数*/
open class Human1(val name: String, val age: Int){
    fun eat() {
        println(name + " is eating. He is " + age + " years old.")
    }
}
class Student3(val sId: String, val grade: Int, name: String, age: Int) : Human1(name, age) {
}

/*次构造函数：通过关键字constructor定义，有函数体，一个类可以有多个次构造函数；*/
class Student4(val sId: String, val grade: Int, name: String, age: Int) : Human1(name, age) {
   //所有次构造函数必须调用主构造函数（直接或间接）
    constructor(name: String, age: Int) : this("", 0, name, age){}//直接调用主构造函数
    constructor():this("",0)//间接调用主构造函数
}

fun main() {
    println("")
    val s1 = Student1()
    s1.name = "student1"
    s1.age = 11
    s1.eat()

    println("")
    val s2 = Student2("111",2)
    s2.name = "student2"
    s2.age = 12
    s2.eat()

    println("")
    val s3= Student3("333",3,"student3",13)
    s3.eat()

    println("\n次构造函数测试")
    val s4=Student4()
    val s5=Student4("a123",5,"Tom",15)
    val s6=Student4("jack",16)
    s4.eat()
    s5.eat()
    s6.eat()
}
