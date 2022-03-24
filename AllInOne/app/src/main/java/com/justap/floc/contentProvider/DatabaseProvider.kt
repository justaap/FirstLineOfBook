package com.justap.floc.contentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.contentValuesOf
import com.justap.floc.persistenceStorage.MySQLiteDBHelper
import java.lang.StringBuilder

/**
 * 自定义contentProvider，供其他程序访问一些数据（此处通过JetpackTest项目来实现访问）
 *
 *  a.通过继承 ContentProvider 重写onCreate、getType和增删改查六个方法，在其中进行对数据库的操作，也可是其他存储方式
 *
 *  b.ContentProvider是通过URI实现具体数据访问的：URI标准格式（content://com.justap.floc.provider/item）
 *
 *  c.借助 UriMatcher 实现URI内容的匹配，从而决定访问的是具体表（content://com.justap.floc.provider/ *）
 *      还是具体某一行（content://com.justap.floc.provider/table/#）的数据
 *
 *  d.在其他程序中通过contentResolver访问（见文件末尾实例）
 * */

class DatabaseProvider : ContentProvider() {

    companion object{
        const val TABLE_BOOK = "Book"
        const val TABLE_CATEGORY = "Book"
    }

    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3
    private val authority = "com.justap.floc.provider"
    private var dbHelper: MySQLiteDBHelper? = null

    private val uriMatch by lazy {
        val matcher = UriMatcher(UriMatcher.NO_MATCH)
        matcher.addURI(authority, "book", bookDir)//访问该表所有数据
        matcher.addURI(authority, "book/#", bookItem)//访问该表单条数据
        matcher.addURI(authority, "category", categoryDir)
        matcher.addURI(authority, "category/#", categoryItem)
        matcher
    }

    //数据库的初始化，成功返回true，失败返回false
    override fun onCreate(): Boolean {
        val context = context ?: return false
        dbHelper = MySQLiteDBHelper(context,"BookStore.db",1)
        return true
    }

    /** 根据传入的uri返回相应的MIME类型
     *  MIME字符串由3部分组成：
     *      a.必须以vnd开头，
     *      b.1内容URI以路径结尾:android.cursor.dir/;b.2内容di以id结尾:android.cursor.item/
     *      c.最后接上vnd.<authority>.<path>
     */
    override fun getType(uri: Uri): String? {
        return when (uriMatch.match(uri)) {
            bookDir ->{
                "vnd.android.cursor.dir/vnd.$authority.book"
            }
            bookItem ->{
                "vnd.android.cursor.item/vnd.$authority.book"
            }
            categoryDir ->{
                "vnd.android.cursor.dir/vnd.$authority.category"
            }
            categoryItem ->{
                "vnd.android.cursor.item/vnd.$authority.category"
            }
            else -> null
        }
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper?.writableDatabase
        return when (uriMatch.match(uri)) {
            bookItem,bookDir ->{
                val newBookId = db?.insert("Book",null,values)
                Uri.parse("content://$authority/book/$newBookId")
            }
            categoryItem,categoryDir ->{
                val categoryId = db?.insert("Category", null, values)
                Uri.parse("content://$authority/category/$categoryId")
            }
            else -> null
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val db = dbHelper?.writableDatabase
        return when (uriMatch.match(uri)) {
            bookDir ->{
                db?.delete(TABLE_BOOK,selection,selectionArgs) ?: 0
            }
            bookItem ->{
                //getPathSegments会将UIR权限之后的部分以"/"切割并存入数组，其中第0个位置是路径，第一个位置就是id了
                val bookId = uri.pathSegments[1]
                db?.delete(TABLE_BOOK,"id=?", arrayOf(bookId) ) ?: 0
            }
            categoryDir ->{
                db?.delete(TABLE_CATEGORY, selection, selectionArgs) ?: 0
            }
            categoryItem ->{
                val categoryId = uri.pathSegments[1]
                db?.delete(TABLE_CATEGORY, "id=?", arrayOf(categoryId)) ?: 0
            }
            else -> 0
        }
    }
    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        val db = dbHelper?.writableDatabase
        return when (uriMatch.match(uri)) {
            bookDir ->{
                db?.update(TABLE_BOOK,values,selection,selectionArgs) ?: 0
            }
            bookItem ->{
                val id = uri.pathSegments[1]
                db?.update(TABLE_BOOK,values,"id=?", arrayOf(id)) ?: 0
            }
            categoryDir ->{
                db?.update(TABLE_CATEGORY,values,selection,selectionArgs) ?: 0
            }
            categoryItem ->{
                val id = uri.pathSegments[1]
                db?.update(TABLE_CATEGORY,values,"id=?", arrayOf(id)) ?: 0
            }
            else -> 0
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                              selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val db = dbHelper?.writableDatabase
        return when (uriMatch.match(uri)) {
            bookDir ->{
                db?.query(TABLE_BOOK,projection,selection,selectionArgs,null,null,sortOrder)
            }
            bookItem ->{
                val bookId = uri.pathSegments[1]
                db?.query(TABLE_BOOK,projection,"id = ?", arrayOf(bookId),null,null,sortOrder)
            }
            categoryDir ->{
                db?.query(TABLE_CATEGORY,projection,selection,selectionArgs,null,null,sortOrder)
            }
            categoryItem ->{
                val categoryId = uri.pathSegments[1]
                db?.query(TABLE_CATEGORY,projection,"id = ?", arrayOf(categoryId),null,null,sortOrder)
            }
            else -> null
        }
    }
}


/**
 *  访问AllInOne程序中的DatabaseProvider内容
 * */
/*

class ContentProviderAccesserActivity : AppCompatActivity() {

    companion object{
        const val authority = "com.justap.floc.provider"
    }
    lateinit var vBinding:ActivityContentProviderAccesserBinding
    var bookId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityContentProviderAccesserBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        vBinding.btnAddData.setOnClickListener {
            val uri = Uri.parse("content://$authority/book")
            val values = contentValuesOf("name" to "A Clash of Kings",
                "author" to "George Martin","pages" to "111")
            val newUri = contentResolver.insert(uri,values)
            bookId = newUri?.pathSegments?.get(1)
        }

        vBinding.btnDeleteData.setOnClickListener {
            val uri = Uri.parse("content://$authority/book/$bookId")
            contentResolver.delete(uri,null,null)
        }

        vBinding.btnUpdataData.setOnClickListener {
            val uri = Uri.parse("content://$authority/book/$bookId")
            val values = contentValuesOf("name" to "A Storm of Swords",
                "author" to "George Martin","pages" to "222")
            contentResolver.update(uri,values,null,null)
        }

        vBinding.btnQueryData.setOnClickListener {
            val uri = Uri.parse("content://$authority/book")
            val cursor = contentResolver.query(uri, null, null, null, null)
            val sb = StringBuilder()
            while (cursor?.moveToNext() == true) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val author = cursor.getString(cursor.getColumnIndex("author"))
                val pages = cursor.getString(cursor.getColumnIndex("pages"))
                sb.append("name is $name,author is $author, pages is $pages \n")

            }
            vBinding.tvInfoShow.setText(sb.toString())
        }
    }
}*/
