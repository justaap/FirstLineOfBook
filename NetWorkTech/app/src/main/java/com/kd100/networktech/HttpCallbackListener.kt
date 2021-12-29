package com.kd100.networktech

import java.lang.Exception

interface HttpCallbackListener {
    fun onFinish(response: String)
    fun onError(e:Exception)
}