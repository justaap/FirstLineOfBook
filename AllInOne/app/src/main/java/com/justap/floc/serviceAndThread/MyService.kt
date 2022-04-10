package com.justap.floc.serviceAndThread

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import floc.R

/**
 *  普通service与activity之间的通信：创建一个Binder内部类；
 *  前台service的创建方法，见makeForegroundService：
 */
class MyService : Service() {

    //通过onBind与Activity通信
    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(applicationContext, "MyService Created", Toast.LENGTH_SHORT).show()
        makeForegroundService()
    }

    //使用前台service,创建通知使其显示在状态栏来实现前台
    private fun makeForegroundService() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "my_service",
                "前台service通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, ThreadTestActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, "my_service")
            .setContentTitle("content title")
            .setContentText("content text")
            .setSmallIcon(R.drawable.star_empty_black)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.icon_right))
            .setContentIntent(pi)
            .build()
        startForeground(1,notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(applicationContext, "MyService Start", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "MyService Destroyed", Toast.LENGTH_SHORT).show()
    }

    private val mBinder = DownloadBinder()

    class DownloadBinder : Binder() {
        fun startDownload() {
            Log.d("MyService","startDownload")
        }

        fun getProgress():Int {
            Log.d("MyService", "getProgress")
            return 0
        }
    }
}