package KTPractice.OOP

import KTPractice.LogicalControl

/**接口的定义与实现，面向接口编程（多态）
 *
 * 函数的可见性修饰符：public，private，protected和internal;
 * public是默认项，可不写（java是default，同一包路径下的类可见）;
 * private：支队当前类内部可见（java一样）;
 * protected：只对当前类和子类可用（java还对同一包路径下的类可见）;
 * internal：对同一模块中的类可见;
 * */

interface InterfaceTest {
}

interface Study{
    fun readBooks()
    fun doHomeWorks(){
        //允许对接口中定义的函数进行默认实现，此时不再强制实现的类重写该函数（JDK1.8以后也有此功能）
        println("do homework default implementation")
    }
}

/*实现接口也是用冒号":"，和继承类之间用逗号隔开
* 此处继承了Human1类，实现了Study接口*/
class StudentInterface(name: String, age: Int) : Human1(name, age), Study {
    override fun readBooks() {
        println(name + " is reading")
    }

    //接口中已经默认实现的方法，可不重写
    /*override fun doHomeWorks() {
        println(name + " is doing homeword")
    }*/
}

fun main() {
    val s1 = StudentInterface("payne", 11)
    doStudy(s1)//实现了Study接口的StudentInterface类，可以传递给doStudy函数，
    s1.eat()
}

/*多态实例：用接口定义形参，但传入的实参可以是实现该接口的类*/
fun doStudy(study: Study) {
    study.readBooks()
    study.doHomeWorks()
}