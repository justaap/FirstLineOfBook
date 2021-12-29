package com.kd100.networktech.saxhelper

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.lang.StringBuilder
import java.security.acl.AclEntry

class SAXHandler : DefaultHandler() {

    private var nodeName = ""
    private lateinit var id: StringBuilder
    private lateinit var name: StringBuilder
    private lateinit var version: StringBuilder
    //xml解析开始时调用
    override fun startDocument() {
        id = StringBuilder()
        name = StringBuilder()
        version = StringBuilder()
    }

    //解析某节点时调用
    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
        nodeName = localName
        Log.d("SAXHandler","uri is $uri, localName is $localName, qName is $qName, attributes is $attributes")
    }

    //获取节点内容时调用
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        when (nodeName) {
            "id" -> id.append(ch, start, length)
            "name" -> name.append(ch, start, length)
            "version" -> version.append(ch, start, length)
        }
    }

    //某节点解析完成时调用
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if (localName == "app") {
            Log.d("SAXHandler","id is ${id.toString().trim()}")
            Log.d("SAXHandler","name is ${name.toString().trim()}")
            Log.d("SAXHandler","version is ${version.toString().trim()}")
            id.clear()
            name.setLength(0)
            version.clear()
        }
    }

    //xml文档解析结束时调用
    override fun endDocument() {
    }
}