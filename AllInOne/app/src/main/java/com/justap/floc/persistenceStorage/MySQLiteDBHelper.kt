package com.justap.floc.persistenceStorage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.lifecycle.OnLifecycleEvent

/**
 *
 * */

class MySQLiteDBHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {
    //创建book表的指令
    // SQLite的数据类型：integer(整型),real(浮点),text(文本),blob(二进制)
    private val createBookTb = "create table Book(" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)"

    private val createCategory = "create table Category(" +
            "id integer primary key autoincrement" +
            "category_name text," +
            "category_code integer)"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createBookTb)//v1
        db.execSQL(createCategory)//v2需求
        Toast.makeText(context, "成功建Book表", Toast.LENGTH_SHORT).show()
    }

    //当初始化MySQLiteDBHelper时传入大于当前version的值，会调用onUpgrade方法
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //更新数据库方法1，需要将存在的表先删除，不建议
        /*db.execSQL("drop table if exists Book")
        onCreate(db)*/

        //更新数据库方法2，先判断当前版本，只执行旧版本没有的指令,建议
        if (oldVersion <= 1) {
            //添加新表Category
//            db.execSQL(createCategory)
        }
        if (oldVersion <= 2) {
            //Book表添加新列category_id
//            db.execSQL("alter table Book add column category_id integer")
        }
    }

}