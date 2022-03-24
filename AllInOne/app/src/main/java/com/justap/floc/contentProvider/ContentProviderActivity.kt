package com.justap.floc.contentProvider

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import floc.databinding.ActivityContentProviderBinding
import kotlinx.android.synthetic.main.activity_content_provider.*

/**
 * 1.权限申请的基本操作：
 *   a.Manifest中注册权限 “CALL_PHONE”
 *   b.申请运行时权限：通过ContextCompat的checkSelfPermission方法检查权限；然后ActivityCompat的
 *   requestPermissions方法请求权限；最后重写onRequestPermissionsResult处理请求结果。
 *
 * 2.使用contentProvider读取通讯录（详见方法：readContacts()）——需要先获取权限
 *  a.ContextWrapper的getContentResolver方法获取ContentResolver对象；
 *  b.通过ContentResolver的insert、delete、update、query实现增删改查
 *
 * */

class ContentProviderActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_CALL_PHONE = 1
        const val REQUEST_CODE_READ_CONTACTS = 2
    }

    private lateinit var vBinding: ActivityContentProviderBinding
    private lateinit var adapter:ArrayAdapter<String>
    private val contactsList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityContentProviderBinding.inflate(layoutInflater)
        setContentView(vBinding.root)

        initListener()

    }

    private fun initListener() {
        vBinding.btnMakeCall.setOnClickListener {
            //申请运行时权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_DENIED
            ) {
                makePhoneCall()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE_CALL_PHONE
                )
            }
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,contactsList)
        lv_contacts.adapter = adapter
        //读取通讯录
        vBinding.btnGetContacts.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_DENIED
            ) {
                readContacts()
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_READ_CONTACTS
                )
            }
        }
    }

    //通过contentProvider读取通讯录
    private fun readContacts() {
        val cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)
        while (cursor?.moveToNext() == true) {
            val name =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            contactsList.add("$name\n$number")
        }
        adapter.notifyDataSetChanged()
        cursor?.close()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CALL_PHONE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall()
                } else {
                    Toast.makeText(this, "你拒绝了打电话权限", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_CODE_READ_CONTACTS ->{
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "你拒绝了读取通讯录权限", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun makePhoneCall() {
        try {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:10086") //创建打电话给指定号码的intent
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}