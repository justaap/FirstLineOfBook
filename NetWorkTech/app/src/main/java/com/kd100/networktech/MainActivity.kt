package com.kd100.networktech

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kd100.networktech.databinding.ActivityMainBinding
import com.kd100.networktech.saxhelper.SAXHandler
import kotlinx.coroutines.*
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.xml.sax.InputSource
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.StringReader
import javax.xml.parsers.SAXParserFactory
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    override fun onDestroy() {
        super.onDestroy()
        uiScope.cancel()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //viewBinding初始化
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        viewModel = ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)
//        val url = "http://192.168.1.102:88/get_data.xml"
//        val url = "http://10.240.20.53/get_data.xml"
        val url = "http://10.240.20.53/get_data.json"
        binding.btnSendRequest.setOnClickListener {
//            sendRequestWithOkHttp(url)
            binding.tvShow.text = "发送请求..."
            Log.d("MainActivity", "发送请求...")

//            sendRequestWithOkHttpCallback(url)
//            sendRequestWithHttpURLConnectionCallback(url)

            uiScope.launch {
                val result = sendRequestWithOkHttpSuspend(url)
//                binding.tvShow.text = "requestWithOkHttpSuspend result is $result"
                viewModel.result.value = "requestWithOkHttpSuspend result is $result"
//                viewModel.result.postValue("requestWithOkHttpSuspend result is $result")
            }
        }
        viewModel.result.observe(this, Observer {
            binding.tvShow.text = it
        })


    }

    //使用协程发起网络请求
    suspend fun sendRequestWithOkHttpSuspend(address: String):String{
        //suspendCoroutine{}只能在协程作用域和挂起函数中调用，
        // 接收一个lambda表达式，将当前协程挂起，然后在普通线程中执行lambda中代码，
        // lambda中传入Continuation参数，调用其resume()和resumeWithException()让协程恢复执行

        //定义的函数返回类型就是resume()中需要传入的数据类型
        return suspendCoroutine { continuation ->//挂起当前协程
            //在普通线程中进行网络请求，回调结果
            HttpUtil.sendRequestWithOkHttp(address, object : okhttp3.Callback {
                override fun onResponse(call: Call, response: Response) {
                    response.body?.let { continuation.resume(it.string()) }//恢复当前协程
                }
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)//恢复当前协程
                }
            })
        }
    }

    fun sendRequestWithOkHttpCallback(url: String) {
        HttpUtil.sendRequestWithOkHttp(url, object : okhttp3.Callback {
            override fun onResponse(call: Call, response: Response) {
                val result = response.body?.string()//注意是 string不是toString
//                Log.d("MainActivity", "OkHttpCallback response.body?.string() is：" + result)
//                response.body?.let { parseJSONWithGSON(it.string()) }//.string()使用一次即清空内容
                parseJSONWithGSON(result)
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("MainActivity", e.toString())
            }
        })
    }

    private fun sendRequestWithHttpURLConnectionCallback(url: String) {
        //object:interfaceName:实现匿名内部类的语法，类似java 的new interfaceName
        HttpUtil.sendRequestWithHttpURLConnection(url, object : HttpCallbackListener {
            override fun onFinish(response: String) {
                Log.d("MainActivity","URLConnection response is $response")
                parseJSONWithGSON(response)
//                viewModel.result.value = response//Cannot invoke setValue on a background thread
            }

            override fun onError(e: java.lang.Exception) {
                Log.d("MainActivity", e.toString())
            }
        })
    }

    fun sendRequestWithOkHttp(url: String) {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
//                    parseXMLWithPull(responseData)
//                    parseXmlWithSAX(responseData)
//                    parseJSONWithJSONObject(responseData)
                    parseJSONWithGSON(responseData)
//                    viewModel.
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**json数组
     * [{"id": "5", "version":"5.5", "name": "Clash of Clans"},
     * {"id": "6", "version": "7.0", "name ":"Boom Beach"},
     * {"id": "7", "version": "3.5", "name": "clash Royale"}]*/
    fun parseJSONWithJSONObject(jsonData: String) {
        try {
            //1.将服务器返回的json数组传入JSONArray对象中
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                //2.一个JSONObject就是数组的一个元素，遍历所有元素，逐一取出值
                val jsonObject = jsonArray.getJSONObject(i)
                //3.按照key值查询，key输入有误报错：System.err: org.json.JSONException: No value for verison
                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")
                Log.d("MainActivity", "id is $id")
                Log.d("MainActivity", "name is $name")
                Log.d("MainActivity", "version is $version")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun parseJSONWithGSON(jsonData: String?) {
        try {
            val gson = Gson()
            //1.将数据映射到根据数据源定义的类中
            val typeOfT = object : TypeToken<List<App>>() {}.type
            val appList = gson.fromJson<List<App>>(jsonData, typeOfT)
            for (app in appList) {
                Log.d("MainActivity Gson", "id is ${app.id}")
                Log.d("MainActivity Gson", "name is ${app.name}")
                Log.d("MainActivity Gson", "version is ${app.version}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun parseXmlWithSAX(xmlData: String) {
        //1.获取工厂实例
        val factory = SAXParserFactory.newInstance()
        //2.通过工厂实例得到解析器的xmlReader对象
        val SAXParserReader = factory.newSAXParser().xmlReader
        val handler = SAXHandler()
        //3.将自定义的handler与xmlReader绑定
        SAXParserReader.contentHandler = handler
        //4.将xml数据注入xml解析器中
        SAXParserReader.parse(InputSource(StringReader(xmlData)))
    }

    private fun parseXMLWithPull(xmlData: String) {
        try {
            var id = ""
            var name = ""
            var version = ""
            //1.获取工厂实例
            val factory = XmlPullParserFactory.newInstance()
            //2.通过工厂实例得到xml解析器对象
            val xmlPullParser = factory.newPullParser()
            //3.将xml数据注入xml解析器中
            xmlPullParser.setInput(StringReader(xmlData))
            //4.开始解析
            var eventType = xmlPullParser.eventType//获取当前解析事件
            while (eventType != XmlPullParser.END_DOCUMENT) {//END_DOCUMENT表示解析结束
                val nodeName = xmlPullParser.name//获取节点名称
                when (eventType) {
                    XmlPullParser.START_TAG -> {//开始解析某节点
                        when (nodeName) {
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                    }
                    XmlPullParser.END_TAG -> {//完成解析某节点
                        if (nodeName == "app") {
                            Log.d("MainActivity", "id is $id")
                            Log.d("MainActivity", "name is $name")
                            Log.d("MainActivity", "version is $version")
                        }
                    }
                }
                eventType = xmlPullParser.next()//更新解析事件
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    /** xml数据：
     * <apps>
     *       <app>
     *          <id>1</id>
     *          <name>Google Maps</name>
     *          <version>1.0</version>
     *      </app>
     *      <app>
     *          <id>2</id>
     *          <name>Chrome</name>
     *          <version>2.2</version>
     *      </app>
     *      <app>
     *          <id>3</id>
     *          <name>Google Play</name>
     *          <version>2.3</version>
     *      </app>
     * </apps>
     *
     * */
}