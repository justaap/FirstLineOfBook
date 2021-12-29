package com.kd100.networktech

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpUtil {

    fun sendRequestWithHttpURLConnection(address:String,callbackListener: HttpCallbackListener) {
//        发起网络请求需要开启线程
        thread{
            var connection: HttpURLConnection? = null
            try {
                val response = StringBuilder()
                //1.创建实例
                val url = URL(address)
                connection = url.openConnection() as HttpURLConnection
                //2.设置请求所使用的方法（GET和POST）
                connection.requestMethod = "GET"
                //3.自由定制（连接、读取超时的毫秒数，服务器希望得到的一些消息头等）
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                //完成网络请求时将结果回调（注册事件）
                callbackListener.onFinish(response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                //网络请求失败时回调结果
                callbackListener.onError(e)
            } finally {
                //5.调用disconnect方法关闭HTTP连接
                connection?.disconnect()
            }
        }
    }

    fun sendRequestWithOkHttp(address: String, callbackListener: okhttp3.Callback) {
        val client = OkHttpClient()
        val request = Request.Builder().url(address).build()
        client.newCall(request).enqueue(callbackListener)
    }

}