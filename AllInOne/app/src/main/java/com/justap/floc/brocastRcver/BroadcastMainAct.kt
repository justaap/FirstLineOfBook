package com.justap.floc.brocastRcver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import floc.R
import kotlinx.android.synthetic.main.activity_broadcast_main.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * 广播注册方式有2种：静态注册（AndroidManifest注册），动态注册（代码注册）：
 * 动态注册：只有在程序启动后才能接收广播
 * 静态注册：在未启动时接收广播（为防自启，在被削弱），实例见BootCompleteRcver.kt
 *      部分可被静态注册接收的隐式广播：https://developer.android.google.cn/guide/components/broadcast-exceptions
 *
 * */

class BroadcastMainAct : AppCompatActivity() {

    private lateinit var timeChangeRcv:TimeChangeRcv

    companion object{
        const val MY_BROADCAST = "com.justap.floc.brocastRcver.MY_BROADCAST"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_main)
        tv_show.text = "现在时间是（每分钟更新）："
        registerDynamicBroadcast()
        registerDiyBrocast()
    }

    /**
     * 发送自定义广播（标准广播和有序广播）
     *  默认为隐式广播，使用setPackage指定发给某一个应用，即声明为显示广播
     * */
    private fun registerDiyBrocast() {
        //标准广播
        btn_send_brocast.setOnClickListener {
            val intent = Intent(MY_BROADCAST)
            intent.putExtra("EXTRA","来自于标准广播发送方的消息")
            intent.setPackage(packageName) //声明为显示广播,否则静态广播无法接受此广播
            sendBroadcast(intent)
        }
        //有序广播，可被拦截（接收后用abortBroadcast），可设置优先级（Manifest中设置intent-filter的priority属性）
        btn_send_order_brocast.setOnClickListener {
            val intent = Intent(MY_BROADCAST)
            intent.putExtra("EXTRA","来自于有序广播发送方的消息")
            intent.setPackage(packageName) //声明为显示广播,否则静态广播无法接受此广播
//            sendBroadcast(intent)
            sendOrderedBroadcast(intent, null)
        }
    }

    /** 动态注册广播：设置监听的action类型*/
    private fun registerDynamicBroadcast() {
        val intentFilter = IntentFilter()
//        intentFilter.addAction("android.intent.action.TIME_TICK")
        intentFilter.addAction(Intent.ACTION_TIME_TICK)//监听系统时间变化(每分钟
        timeChangeRcv = TimeChangeRcv()
        registerReceiver(timeChangeRcv, intentFilter)
    }

    /**
     * 动态注册广播：继承BroadcastReceiver类，设置监听的响应事件
     * onReceive中勿进行耗时操作、不允许开启线程
     * */
    inner class TimeChangeRcv : BroadcastReceiver() {
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        override fun onReceive(p0: Context?, p1: Intent?) {
            if (Intent.ACTION_TIME_TICK == p1?.action){
                Toast.makeText(p0,"time has changed",Toast.LENGTH_LONG).show()
                tv_show.text = "现在时间是（每分钟更新）：" +
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().time)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //退出页面时注销，否则一直监听并响应
        unregisterReceiver(timeChangeRcv)
    }
}