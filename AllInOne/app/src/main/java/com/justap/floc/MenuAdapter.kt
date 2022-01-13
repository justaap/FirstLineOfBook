package com.justap.floc

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import floc.R

class MenuAdapter(val entryList: List<EntryItem>) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

//    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entry: LinearLayout = itemView.findViewById(R.id.ll_entry)
        val entryIcon: ImageView = itemView.findViewById(R.id.iv_entry_icon)
        val entryTitle: TextView = itemView.findViewById(R.id.tv_entry_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.entry.setOnClickListener(entryList[position].listener)
        holder.entry.setOnClickListener{
            entryList[position].listener?.invoke()
            Log.d("menuAdapter",position.toString())
        }
        holder.entryIcon.setImageResource(entryList[position].icon)
        holder.entryTitle.text = entryList[position].title
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

}