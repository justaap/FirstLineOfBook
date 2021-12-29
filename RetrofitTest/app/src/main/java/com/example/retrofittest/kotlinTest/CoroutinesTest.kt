package com.example.retrofittest.kotlinTest

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
fun main() {
    GlobalScope.launch {
        println("code run in coroutine scope")
        delay(1500)//delay只会挂起当前协程，不影响其他协程运行，因此只能在协程的作用域或其他挂起函数中调用
        println("code run in coroutine scope finished")
    }
    Thread.sleep(2000)//Sleep会阻塞当前线程，因此运行在本线程下的所有协程都会被阻塞。
    //以上代码主线程只阻塞了1秒，而协程挂起了1.5秒，因此只打印了“code run in coroutine scope”
}