package com.kd100.jetpacktest

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import com.kd100.jetpacktest.RoomTest.AppDatabase
import com.kd100.jetpacktest.RoomTest.Book
import com.kd100.jetpacktest.RoomTest.UserEntity
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Lifecycle.2.通过LifecycleOwner的getLifecycle()获取Lifecycle对象，
//        然后添加观察对象（Activity本身就是一个LifecycleOwner）
        val mLifecycle = lifecycle
        mLifecycle.addObserver(MyObserver(mLifecycle))

        //从SharedPreferences获取数据
        sp = getPreferences(Context.MODE_PRIVATE)
        val counterReserved = sp.getInt("count_reserved", 0)

        //viewModel初始化
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //通过工厂模式初始化viewModel,才能将参数传递给MainViewModel的构造函数
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(counterReserved)
        ).get(MainViewModel::class.java)

        //添加viewModel中数据变化时的回调事件：LiveData的observe方法
        viewModel.counter.observe(this, {
            tv_info_text.text = it.toString()
        })

        btn_plus.setOnClickListener {
//            viewModel.counter++
//            refrashCounter()
            viewModel.plusone()
        }
        btn_clear.setOnClickListener {
//            viewModel.counter = 0
//            refrashCounter()
            viewModel.clear()
        }
//        refrashCounter()

        //初始化数据库访问对象
        val userDao = AppDatabase.getDataBase(this).userDao()
        val bookDao = AppDatabase.getDataBase(this).bookDao()
        val user1 = UserEntity("Tom", "Brady", 40)
        val user2 = UserEntity("Tom", "Hanks", 63)
        val book = Book("活着", 260, "余华")
        btn_add_data.setOnClickListener {
            thread {
                try {
                    //增
                    user1.id = userDao.insertUser(user1)
                    user2.id = userDao.insertUser(user2)
                    book.id = bookDao.insertBook(book)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        btn_updata_data.setOnClickListener {
            thread {
                if (user1.age == 40) user1.age = 42
                else user1.age = 40
                userDao.updateUser(user1)
            }
        }
        btn_delete_data.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("Hanks")
                userDao.deleteUserByLastName("Brady")
            }
        }
        btn_query_data.setOnClickListener {
            thread {
                val sb = StringBuilder()
                for (user in userDao.loadAllUsers()) {
                    Log.d("MainActivity", user.toString())
                    sb.append(user.toString() + " id is ${user.id}\n")
                }
                for (book in bookDao.loadAllBooks()) {
                    sb.append(book.toString() + " id is ${book.id}\n")
                }
                viewModel.show.postValue(sb.toString())
            }
        }
        viewModel.show.observe(this, {
            tv_info_text.text = it.toString()
        })
        btn_get_user_by_name.setOnClickListener {
            thread {
                val sb = StringBuilder()
                val user = userDao.loadByFirstName("Tom")
                for (user in user) {
                    Log.d("MainActivity ByName", user.toString())
                    sb.append(user.toString() + " id is ${user.id}\n")
                }
                viewModel.show.postValue(sb.toString())
//                user?.let { viewModel.show.postValue(user.toString()) }
//                viewModel.show.postValue(user.toString())
            }
        }

        btn_switch_flutter.setOnClickListener {
            startActByNewEngine()
        }

        //缓存flutter引擎，
        preWarmFlutterEngine()
        btn_switch_flutter_by_cache_engine.setOnClickListener {
            startActByCacheEngine()
        }
        initListen()
    }

    private fun startActByCacheEngine() {
        startActivity(
            FlutterActivity
                .withCachedEngine(getString(R.string.MainFlutterEngineID))
                .build(this)
        )
    }

    private fun preWarmFlutterEngine() {
        val flutterEngine = FlutterEngine(this)
        flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
        FlutterEngineCache.getInstance().put(getString(R.string.MainFlutterEngineID), flutterEngine)
    }

    private fun startActByNewEngine() {
        startActivity(
            FlutterActivity
                .withNewEngine()
                .initialRoute("") // 启动指定 Flutter page，没有可以省略
                .build(this))
    }


    private fun initListen() {

    }

    private fun refrashCounter() {
        tv_info_text.text = viewModel.counter.toString()
    }

    override fun onPause() {
        super.onPause()
        //将数据存入sharedPreferences
        sp.edit {
            viewModel.counter.value?.let { putInt("count_reserved", it) }
        }
    }
}