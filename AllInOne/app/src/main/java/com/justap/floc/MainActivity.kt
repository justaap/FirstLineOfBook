package com.justap.floc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.justap.floc.brocastRcver.BroadcastMainAct
import floc.R
import kotlinx.android.synthetic.main.activity_main.*

//《第一行代码》实例练习

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val entryList = mutableListOf<EntryItem>()
        initList(entryList)
        val layoutManager = GridLayoutManager(this,3)
        rv_menu.layoutManager = layoutManager
        rv_menu.adapter = MenuAdapter(entryList)
}

    private fun initList(entryList: MutableList<EntryItem>) {
        entryList.add(EntryItem("广播\nBroadcast", R.drawable.star_empty_grey) {
            startActivity(Intent(this, BroadcastMainAct::class.java))
        })

        for(i in 1..30){
            entryList.add(EntryItem("测试$i", R.drawable.star_empty_grey) {
                Toast.makeText(this,"测试$i",Toast.LENGTH_SHORT).show()
            })
        }
    }

}