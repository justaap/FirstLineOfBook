package com.justap.floc

import android.view.View
import floc.R

/**
 * 菜单按钮model
 * 使用高阶函数传递点击事件
 */

class EntryItem(
    val title: String,
    val icon: Int = R.drawable.star_empty_black,
//    val listener: View.OnClickListener
    val listener: (()->Unit)?
    )