package com.justap.floc.persistenceStorage

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import floc.databinding.ActivityMainPersistenceBinding
import kotlinx.android.synthetic.main.activity_main_persistence.*

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

        //pref1.获取Context的SharedPreferences，
        //  还可以获取Activity的Preferences-by getPreferences()，不过不可以自定义文件名(用的当前类名
        //存在data/data/包名/shared_prefs 目录下
        val pref = getSharedPreferences("sharedPre_data", Context.MODE_PRIVATE)

        //pref2.保存 sharedPreferences中的值
        vBinding.btnLogin.setOnClickListener {
            val editor = pref.edit()
            var pwd = ""
            var name = ""
            var isRemenbered = false
            if (cb_remember_pwd.isChecked) {
                name = et_name.text.toString()
                pwd = et_pwd.text.toString()
                isRemenbered = true
            }
            editor.putString("name", name)
            editor.putString("password", pwd)
            editor.putBoolean("isRemenbered", isRemenbered)
            editor.apply()//最后需要apply一下
            Toast.makeText(this,"登录成功"+isRemenbered+"保存密码",Toast.LENGTH_SHORT).show()
        }

        //pref3.获取 sharedPreferences中的值
        if (pref.getBoolean("isRemenbered", false)) {
            et_name.setText(pref.getString("name", ""))
            et_pwd.setText(pref.getString("password", ""))
            cb_remember_pwd.isChecked = true
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //将editText中的内容保存到文件中
        FileUtils.save2flie(this,vBinding.etFromFile.text.toString(),fileName)
    }
}