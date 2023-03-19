package com.xy.android.plugin

import android.app.Activity
import android.os.Bundle

interface IPluginActivity {

    fun attach(proxy: Activity)

    fun onCreate(savedInstanceState: Bundle?)

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()
}