package com.example.retrofittest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofittest.ServiceCreater.await
import com.example.retrofittest.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object {
        const val BASE_URL1 = "http://10.240.20.53/"
        const val BASE_URL2 = "http://192.168.1.102:88/"
    }

    val uiScope = CoroutineScope(Dispatchers.Main)

    private lateinit var viewBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnCommit.setOnClickListener {
//            requestByRetrofit()
            uiScope.launch {
                getAppDataByCoroutines()
            }
        }
        viewBinding.btnCommit2.setOnClickListener {
            requestByRetrofit()
        }
        viewBinding.btnCommit3.setOnClickListener {
            /*startActivity(
                FlutterActivity
                    .withNewEngine()
                    .initialRoute("") // 启动指定 Flutter page，没有可以省略
                    .build(this))*/
        }
    }
    //使用协程实现网络请求
    suspend fun getAppDataByCoroutines() {
        try {
            val list = ServiceCreater.create2<AppService>().getAppData().await()
            val sb = StringBuilder()
            sb.append("getAppDataByCoroutines\n")
            if (list != null) {
                for (app in list) {
                    Log.d("MainActivity", "getAppDataByCoroutines")
                    Log.d("MainActivity", "id is ${app.id}")
                    Log.d("MainActivity", "name is ${app.name}")
                    Log.d("MainActivity", "version is ${app.version}")
                    sb.append("id:${app.id}, name:${app.name}, version:${app.version}\n")
                }
            }
            viewBinding.tvShow.text = sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun requestByRetrofit() {
        /**发起请求的时候，Retrofit会自动在内部开启子线程，
         * 当数据回调到Callback中之后，Retrofit又会自动切换回主线程，
         * 整个操作过程不用考虑线程切换问题*/

        /*//1.创建Retrofit对象，定义根路径和数据解析转换库
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL1)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            //2.创建自定义接口的动态代理对象
            val appService = retrofit.create(AppService::class.java)*/

        //封装成单例类，方便复用
        val appService = ServiceCreater.create(AppService::class.java)
        val appService2 = ServiceCreater.create2<AppService>()
        //3.调用自定义接口中的方法获取不同接口的数据并处理
        //            appService.getAppData().enqueue(object : Callback<List<App>> {
        appService2.getAppData().enqueue(object : Callback<List<App>> {
            override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                val list = response.body()
                val sb = StringBuilder()
                sb.append("requestByRetrofit\n")
                if (list != null) {
                    for (app in list) {
                        Log.d("MainActivity", "requestByRetrofit")
                        Log.d("MainActivity", "id is ${app.id}")
                        Log.d("MainActivity", "name is ${app.name}")
                        Log.d("MainActivity", "version is ${app.version}")
                        sb.append("id:${app.id}, name:${app.name}, version:${app.version}\n")
                    }
                }
                viewBinding.tvShow.text = sb.toString()
            }

            override fun onFailure(call: Call<List<App>>, t: Throwable) {
                Log.d("MainActivity", t.toString())
                t.printStackTrace()
            }
        })
    }
}