package com.justap.floc.serviceAndThread

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import floc.databinding.ActivityThreadTestBinding
import kotlin.concurrent.thread

/**
 * 1.通过在Handler实现在子线程中更新UI,即异步消息处理：
 * 2.开启和关闭Service：见 startService()和stopService()
 * 3.绑定Service实现与activity的通信：见 bindService()
 * 4.前台Service:让service一直保持运行状态，在状态栏显示，需声明权限，详见MyService类中OnCreat()
 */
class ThreadTestActivity : AppCompatActivity() {

    val updateText = 1
    var handler = object: Handler(Looper.myLooper()!!){
        override fun handleMessage(msg: Message) {
            when(msg.what){
                updateText -> vBinding.tvShow.text = "hello"
            }
        }
    }

    lateinit var vBinding:ActivityThreadTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityThreadTestBinding.inflate(layoutInflater)
        setContentView(vBinding.root)
        vBinding.btnChange.setOnClickListener {
            thread {
                val msg = Message()
                msg.what = updateText
                handler.handleMessage(msg)
            }
        }

        vBinding.btnStartService.setOnClickListener {
            startService()
        }

        vBinding.btnStopService.setOnClickListener {
            stopService()
        }

        vBinding.btnBindService.setOnClickListener {
            bindService()
        }

        vBinding.btnUnbindService.setOnClickListener {
            unbindService(serviceConnection)
        }

        vBinding.btnStartIntentService.setOnClickListener {
            val intent = Intent(this, MyIntentService::class.java)
            startService(intent)
        }
    }

    lateinit var downloadBinder: MyService.DownloadBinder
    private val serviceConnection = object :ServiceConnection{
        override fun onServiceConnected(componentName: ComponentName?, service: IBinder?) {
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {

        }
    }
    private fun bindService() {
        val intent = Intent(this, MyService::class.java)
        bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
    }

    private fun startService(){
        val intent = Intent(this, MyService::class.java)
        startService(intent)
    }

    private fun stopService(){
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }
}