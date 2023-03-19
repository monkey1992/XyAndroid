package com.xy.android.plugin

import android.content.Context
import android.widget.Toast

class CommonClass {

    fun invoke(context: Context) {
        Toast.makeText(
            context,
            "我是 pluginapk 插件中普通类 ${CommonClass::class.java.simpleName} 的实例",
            Toast.LENGTH_SHORT
        ).show()
    }
}