package com.justap.floc.persistenceStorage

import android.content.Context
import java.io.*
import java.lang.StringBuilder

class FileUtils {
    companion object{

        //将字符串以文件形式储存:使用Context类提供的openFileOutput方法，获得一个FileOutputStream对象
        //存在data/data/包名/files目录下
        fun save2flie(context: Context, input: String, fileName: String) {
            try {
                val ot = context.openFileOutput(fileName, Context.MODE_PRIVATE)//覆盖内容
//            val ot = openFileOutput("fileName", Context.MODE_APPEND)//追加内容
                val writer = BufferedWriter(OutputStreamWriter(ot))
                //内置扩展函数use：保证lambda内代码执行完后自动关闭外层流
                writer.use {
                    it.write(input)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        //获取data/data/包名/files目录下的文件内容
        @JvmStatic
        fun getFile(context: Context, fileName: String):String {
            val res = StringBuilder()
            try {
                val input = context.openFileInput(fileName)
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    it.forEachLine {
                        res.append(it)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return res.toString()
        }
    }
}