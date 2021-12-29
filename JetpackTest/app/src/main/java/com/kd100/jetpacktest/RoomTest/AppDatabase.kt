package com.kd100.jetpacktest.RoomTest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//声明数据库版本号、包含的实体类（多个类用逗号隔开）
@Database(version = 3,entities = [UserEntity::class, Book::class])//升级数据库，添加book表，版本号改为2
abstract class AppDatabase: RoomDatabase() {//必须继承自RoomDatabase类，且声明为抽象类，以获取Dao中的实例

    abstract fun userDao(): UserDao//获取dao实例，方法的具体实现是在底层自动完成的
    abstract fun bookDao(): BookDao//数据库升级

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDataBase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            //数据库操作属于耗时操作，Room默认是不允许在主线程中进行数据库操作的;
            //可通过allowMainThreadQueries()更改（建议仅测试环境使用）
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,"app_database")
//                .allowMainThreadQueries()//运行在主线程进行数据操作
//                .fallbackToDestructiveMigration()//数据库升级（销毁之前的数据库，开发测试阶段用）
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)//初始化DataBase时应用升级操作,数据库升级常规写法
                .build().apply {
                    instance = this
                }
        }

        //数据库升级：添加新表
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //升级数据库，添加建表语句
                database.execSQL("create table Book(id integer primary key autoincrement not null,name text not null,pages integer not null)")
            }
        }

        //数据库升级：添加新列
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                //升级数据库，添加建表语句
                database.execSQL("alter table Book add column author text not null default 'unknow'")
            }
        }
    }
}