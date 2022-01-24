package com.justap.floc.persistenceStorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import floc.databinding.ActivityMainPersistenceBinding

class MainPersistenceActivity : AppCompatActivity() {

    private lateinit var vbinding: ActivityMainPersistenceBinding
    val fileName = "fileName"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化ViewBinding
        vbinding = ActivityMainPersistenceBinding.inflate(layoutInflater)
        setContentView(vbinding.root)

        //加载本地文件的内容并展示在editText中
        FileUtils.getFile(this,fileName).let {
            vbinding.etFromFile.setText(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        FileUtils.save2flie(this,vbinding.etFromFile.text.toString(),fileName)
    }
}