package com.kd100.jetpacktest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.contentValuesOf
import com.kd100.jetpacktest.databinding.ActivityContentProviderAccesserBinding
import java.lang.StringBuilder

/**
 *  访问AllInOne程序中的DatabaseProvider内容
 * */

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
}