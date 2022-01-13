package com.justap.floc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import floc.R
import kotlinx.android.synthetic.main.activity_main.*

//《第一行代码》实例练习

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val entryList = mutableListOf<EntryItem>()
        initList(entryList)
        val layoutManager = GridLayoutManager(this,4)
        rv_menu.layoutManager = layoutManager
        rv_menu.adapter = MenuAdapter(entryList)
}

    private fun initList(entryList: MutableList<EntryItem>) {
        entryList.add(EntryItem("测试", R.drawable.star_empty_grey) {
            Toast.makeText(this,"测试",Toast.LENGTH_SHORT).show()
        })

        entryList.add(EntryItem("测试2", R.drawable.star_empty_grey) {
//            Toast.makeText(this,"测试2",Toast.LENGTH_SHORT).show()
        })
    }

}