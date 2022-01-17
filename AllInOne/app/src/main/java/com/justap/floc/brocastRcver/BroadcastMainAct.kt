package com.justap.floc.brocastRcver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import floc.R
import kotlinx.android.synthetic.main.activity_broadcast_main.*

/**
 * 广播注册方式有2种：静态注册（AndroidManifest注册），动态注册（代码注册）：
 *
 * */

class BroadcastMainAct : AppCompatActivity() {

    private lateinit var timeChangeRcv:TimeChangeRcv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_main)

        registerBroadcast()
    }

    /** 注册广播：设置监听的action类型*/
    private fun registerBroadcast() {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        intentFilter.addAction(Intent.ACTION_TIME_TICK)//监听系统时间变化(每分钟
        timeChangeRcv = TimeChangeRcv()
        registerReceiver(timeChangeRcv, intentFilter)
    }

    /** 注册广播：继承BroadcastReceiver类，设置监听的响应事件*/
    inner class TimeChangeRcv : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (Intent.ACTION_TIME_TICK == p1?.action){
                Toast.makeText(p0,"time has changed",Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //退出页面时注销，否则一直监听并响应
        unregisterReceiver(timeChangeRcv)
    }
}