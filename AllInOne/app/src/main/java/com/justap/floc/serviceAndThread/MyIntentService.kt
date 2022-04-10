package com.justap.floc.serviceAndThread

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 *
 */
class MyIntentService : IntentService("MyIntentService") {

    //IntentService会自行创建一个子线程
    override fun onHandleIntent(p0: Intent?) {
        Log.d("MyIntentService","threa id is ${Thread.currentThread().name}")
    }

    //IntentService在运行完毕后自动停止，否则需要调用stopSelf()来关闭
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService","onDestroy executed")
    }
}