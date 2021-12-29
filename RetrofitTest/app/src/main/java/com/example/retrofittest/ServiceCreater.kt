package com.example.retrofittest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object ServiceCreater {
    private const val BASE_URL = "http://10.240.20.53/"
    private const val BASE_URL2 = "http://192.168.1.102:88/"

    //1.创建Retrofit对象，定义根路径和数据解析转换库
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    //2.创建自定义接口的动态代理对象
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //Kotlin泛型实化2大前提：1.inline修饰方法，2.reified修饰泛型
    inline fun <reified T> create2(): T = retrofit.create(T::class.java)

    //定义Call<T>的扩展函数，所有返回类型是Call类型的retrofit请求接口都可以调用await()函数
    suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

            })
        }
    }

}