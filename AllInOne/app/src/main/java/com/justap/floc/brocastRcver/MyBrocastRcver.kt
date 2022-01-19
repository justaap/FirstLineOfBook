package com.justap.floc.brocastRcver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * 静态注册自定义标准广播
 * */
class MyBrocastRcver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val infor = intent.getStringExtra("EXTRA")?:"收到自定义广播啦,但是内容不是string类"
        Toast.makeText(context, "标准广播:"+infor, Toast.LENGTH_SHORT).show()
    }
}
