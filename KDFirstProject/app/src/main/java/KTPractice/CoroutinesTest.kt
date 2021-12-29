package KTPractice

import kotlinx.coroutines.*

class CoroutinesTest {
}

@DelicateCoroutinesApi
fun main() {
//    runGlobalScope()
//    runBlocking()
//    multipleCoroutines()
//    testCoroutinesTime()
    coroutineScopeAndRunBlocking()
    asyncAndAwait()
    withContextTest()
}

private fun runGlobalScope() {
    GlobalScope.launch {
        println("code run in coroutine scope")
        delay(1500)//delay只会挂起当前协程，不影响其他协程运行，因此只能在协程的作用域或其他挂起函数中调用
        println("code run in coroutine scope finished")
    }
    Thread.sleep(1000)//Sleep会阻塞当前线程，因此运行在本线程下的所有协程都会被阻塞。
    //以上代码主线程只阻塞了1秒，而协程挂起了1.5秒，因此只打印了“code run in coroutine scope”
}

private fun runBlocking() {
    //runBlocking会创建一个协程的作用域，可以保证线程一直阻塞，直到协程作用域内的所有代码和子协程执行完，正式环境中有性能问题
    runBlocking {
        println("runBlocking code run in coroutine scope")
        delay(1500)
        println("runBlocking code run in coroutine scope finished")
    }
    Thread.sleep(1000)
}

private fun multipleCoroutines() {
    //使用launch函数创建多协程
    runBlocking {
        //此处的launch函数必须在协程的作用域中调用
        launch {
            //创建子协程1
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }
        launch {
            //创建子协程2
            println("launch2")
            delay(500)
            println("launch2 finished")
        }
    }
}

private fun testCoroutinesTime() {
    val startTime = System.currentTimeMillis()
    runBlocking{
        repeat(100000) {//开启10万个协程
            launch {
                println(it)
            }
        }
    }
    val entTime = System.currentTimeMillis()
    println("共耗时： ${entTime - startTime} ")
}

//为了使函数拥有协程作用域，需要用到suspend关键字，
//kotlin的suspend关键字，将函数声明为挂起函数，该类函数之间可相互调用；
suspend fun printDot1() {
    println(".")
    //Suspend function 'delay' should be called only
    //from a coroutine or another suspend function
    delay(1000)
}
//但是suspend无法提供协程作用域，为了能在函数中调用launch函数，可用coroutineScope函数
//coroutineScope可继承外部的协程作用域并创建一个子协程；
//coroutineScope也是一个挂起函数，可与其他挂起函数调用；
suspend fun printDot2() = coroutineScope {
    launch {
        println(".")
        delay(1000)
    }
}

//coroutineScope与runBlocking函数类似，都可挂起外部协程，直至作用域内的所有代码和子协程执行完毕;
//但是coroutineScope只阻塞当前协程，不影响其他协程、线程，
// 而runBlocking函数会挂起外部线程，影响性能，不推荐在实际项目中使用;
fun coroutineScopeAndRunBlocking() {
    runBlocking{
        coroutineScope {
            launch {
                for (i in 1..10 step 2) {
                    printDot1()
                }
            }
        }
        println("coroutineScope finished")
    }
    println("runBlocking finished")//在嵌套的作用域中由内到外依次执行
}

fun asyncAndAwait() {
    runBlocking(){
        val result = async {
            1+2
        }
        println(result.await())//通过async的await方法获取执行结果
    }
}
fun withContextTest() {
    runBlocking{
        //强制要求指定一个线程参数，有3种值可选：Dispatchers.Default、Dispatchers.IO和Dispatchers.Main；
        val result = withContext(Dispatchers.Default){
            5+5
        }
        println(result)
    }
}