package com.kd100.jetpacktest

import android.util.Log
import android.util.StateSet
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class MyObserver(val lifecycle:Lifecycle) : LifecycleObserver {

    /**Lifecycle.Event共有7种
     * ON_CREATE、ON_START、ON_RESUME、ON_PAUSE、ON_STOP、ON_DESTROY、
     * 和ON_ANY：匹配任何生命周期的回调
     * */
//Lifecycle.1.通过注解将对应方法与生命周期事件绑定
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun activityStart() {
        Log.d("MyObserver", "activityStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun activityCREATE() {
        Log.d("MyObserver", "activityCREATE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun activityRESUME() {
        Log.d("MyObserver", "activityRESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun activityPAUSE() {
        Log.d("MyObserver", "activityPAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun activityStop() {
        Log.d("MyObserver", "activityStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun activityDESTROY() {
        Log.d("MyObserver", "activityDESTROY")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun activityAny() {
        Log.d("MyObserver", "activityAny")
    }

}