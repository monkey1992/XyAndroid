package com.xy.android.plugin.host

import android.app.Application

class PluginHostApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PluginsManager.init(this)
    }
}