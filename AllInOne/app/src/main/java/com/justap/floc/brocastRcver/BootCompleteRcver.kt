package com.justap.floc.brocastRcver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * 静态注册广播，需在AndroidManifest中注册receiver，
 * 并开启应用权限：
 * */

class BootCompleteRcver : BroadcastReceiver() {

    //onReceive中勿进行耗时操作、不允许开启线程
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "开机啦",Toast.LENGTH_LONG).show()
    }
}