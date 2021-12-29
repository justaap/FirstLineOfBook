package com.example.retrofittest

import okhttp3.CacheControl
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface AppService {
    //通过注解表明此方法发起get请求，并声明相对路径
    @GET("get_data.json")
    fun getAppData(): Call<List<App>>//必须声明为retrofit内置的Call类型，泛型可通过Call Adapter自定义成Observable、Flowable等类型

    //GET 动态参数请求： http://example.com/<page>/get_data.json,其中page为页数，动态变化
    @GET("{page}/get_data.json")//使用占位符{page}
    fun getDataByPage(@Path("page") page: Int): Call<List<App>> //@Path("page")注解会自动将其替换到占位符中

    //GET 带多参数请求： http://example.com/<page>/get_data.json？u=<user>&t=<token>
    @GET("get_data.json")
    fun getDataWithParParameter(@Query("u") user: String, @Query("t") token: String): Call<List<App>>

    //DELETE 删除请求 http://example.com/data/<id>
    @DELETE("data/{id}")
    fun deleteData(@Path("id") id: String): Call<ResponseBody>//POST、PUT 、PATCH、DELETE等请求无需关注响应数据的详情，用ResponseBody即可

    //POST 提交数据请求 http://example.com/data/create
    @POST("data/create")
    fun createDataByPost(@Body data: Date): Call<ResponseBody>
    //post提交数据需要将数据放到http请求的body部分，因此通过@Body注解完成
    //添加了@body注解后，会自动将Data中的数据转换成JSON格式的文本，并放到请求的body中，PUT 、PATCH、DELETE同理

    //GET http://example.com/get_data.json
    //在HTTP请求的header中指定参数：
    //User-Agent:okhttp
    //Cache-Control:max-age=0
    @Headers("User-Agent:okhttp", "Cache-Control:max-age=0")
    @GET("get_data.json")
    fun getDataByHeader():Call<List<App>>
    //除了上述静态声明header，还可以动态声明
    @GET("get_data.json")
    fun getDataByHeader(@Header("User-Agent") userAgent: String,
                @Header("Cache-Control") cacheControl: String):Call<List<App>>

}