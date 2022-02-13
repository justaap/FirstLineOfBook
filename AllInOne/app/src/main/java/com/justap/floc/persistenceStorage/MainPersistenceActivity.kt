package com.justap.floc.persistenceStorage

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.core.content.edit
import floc.databinding.ActivityMainPersistenceBinding
import kotlinx.android.synthetic.main.activity_main_persistence.*
import java.lang.Exception
import java.lang.StringBuilder

/**
 * 数据持久化功能的3中方式：文件存储、sharedPreferences、数据库
 * 文件存储:详情看FileUtils
 * sharedPreferences:详情看initPreferences()
 * 数据库:主要为增删改查见(initDB())、使用事务见（updateData()）和更新数据库见(MySQLiteDBHelper类
 * */
class MainPersistenceActivity : AppCompatActivity() {

    private lateinit var vBinding: ActivityMainPersistenceBinding
    val fileName = "fileName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化ViewBinding
        vBinding = ActivityMainPersistenceBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        //加载本地文件的内容并展示在editText中
        FileUtils.getFile(this,fileName).let {
            vBinding.etFromFile.setText(it)
        }

        initPreferences()
        initDB()
    }

    override fun onDestroy() {
        super.onDestroy()
        //将editText中的内容保存到文件中
        FileUtils.save2flie(this,vBinding.etFromFile.text.toString(),fileName)
    }

    private lateinit var dbHelper:MySQLiteDBHelper
    private fun initDB() {
        //新建数据库：BookStore,版本为 1
        dbHelper = MySQLiteDBHelper(this, "BookStore.db", 1)
        vBinding.btnCreatDb.setOnClickListener {
            //getWritableDatabase和getReadableDatabase都可以创建数据库，已存在则打开该数据库
            //当磁盘已满时，getWritableDatabase出现异常，getReadableDatabase以只读的方式打开数据库
            dbHelper.writableDatabase
        }
        vBinding.btnInsert.setOnClickListener {
            addData("活着","余华",666,23.11)
        }
        vBinding.btnUpdate.setOnClickListener {
            updateData()
        }
        vBinding.btnQuery.setOnClickListener {
            queryData()
        }
        vBinding.btnDelete.setOnClickListener {
            deleteData()
        }
    }

    private fun queryData() {
        val db = dbHelper.writableDatabase
        //法1.使用SQL操作
//        val cursor = db.rawQuery("select * from Book", arrayOf())

        //法2.使用API操作
        val result = StringBuilder("查询结果显示:\n")
        //查询Book表所有数据
        // query返回一个含查询结果的Cursor对象,
        val cursor = db.query("Book", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do{
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
                val pages = cursor.getInt(cursor.getColumnIndexOrThrow("pages"))
                val price = cursor.getDouble(cursor.getColumnIndexOrThrow("price"))
                result.append("$name, $author, $pages, $price\n")
            }while (cursor.moveToNext())
        }
        cursor.close()
        vBinding.tvQueryResult.text = result.toString()
    }

    private fun deleteData() {
        //删除页数大于600的书
        val db = dbHelper.writableDatabase
        //法1.使用SQL操作
        /*db.execSQL(
            "delete from Book where pages>?",
            arrayOf("600")
        )*/

        //法2.使用API操作
        db.delete("Book","pages>?", arrayOf("600"))
    }

    private fun updateData() {
        //更新《活着》的页码为555
        val db = dbHelper.writableDatabase
        //法1.使用SQL操作
        /*db.execSQL(
            "update Book set pages=? where name=?",
            arrayOf("555","活着")
        )*/

        //法2.使用API操作
        db.beginTransaction()//开启事务
        try {
            val values = ContentValues()
            values.put("pages", 555)
            //第三个参数中？为占位符，由第4个参数提供
            db.update("Book",values,"name=?", arrayOf("活着"))
            db.setTransactionSuccessful()//事务执行成功
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            //关闭事务
            db.endTransaction()
        }
    }

    //新增数据到指定表：调用insert方法
    private fun addData(bookName:String,author:String,pages:Int,price:Double) {
        val db = dbHelper.writableDatabase
        //法1.使用SQL操作
        /*db.execSQL(
            "insert into Book(neme,author,pages,price) values(?,?,?,?)",
            arrayOf(bookName, author, pages, price)
        )*/

        //法2.使用API操作
        //SQLiteDB的insert方法需要通过ContentValues添加数据
        //添加数据方法1.原生
        /*val values = ContentValues().apply {
            put("name",bookName)
            put("author",author)
            put("pages",pages)
            put("price",price)
        }*/
        //添加数据方法2.使用kt的扩展方法
        val values = contentValuesOf(
            "name" to bookName,
            "author" to author,
            "pages" to pages,
            "price" to price
        )
        db.insert("Book", null, values)
    }

    private fun initPreferences() {
        //pref1.获取Context的SharedPreferences，
        //  还可以获取Activity的Preferences-by getPreferences()，不过不可以自定义文件名(用的当前类名
        //  （存在data/data/包名/shared_prefs 目录下）
        val pref = getSharedPreferences("sharedPre_data", Context.MODE_PRIVATE)
        //pref2.保存 sharedPreferences中的值
        vBinding.btnLogin.setOnClickListener {
            var pwd = ""
            var name = ""
            var isRemenbered = false
            if (cb_remember_pwd.isChecked) {
                name = et_name.text.toString()
                pwd = et_pwd.text.toString()
                isRemenbered = true
            }

            //SharedPreferences.一、原生写法：保存数据
            /*val editor = pref.edit()
            editor.putString("name", name)
            editor.putString("password", pwd)
            editor.putBoolean("isRemenbered", isRemenbered)
            editor.apply()//最后需要apply一下*/

            //SharedPreferences.二、扩展函数法：使用kotlin中SharedPreferences的扩展函数edit，可简化写法
            pref.edit(false){
                putString("name",name)
                putString("password",pwd)
                putBoolean("isRemenbered",isRemenbered)
            }
            Toast.makeText(this,"登录成功"+isRemenbered+"保存密码",Toast.LENGTH_SHORT).show()
        }
        //pref3.获取 sharedPreferences中的值
        if (pref.getBoolean("isRemenbered", false)) {
            et_name.setText(pref.getString("name", ""))
            et_pwd.setText(pref.getString("password", ""))
            cb_remember_pwd.isChecked = true
        }
    }
}