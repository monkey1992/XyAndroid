package com.xy.android.plugin.host

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle

open class BaseProxyActivity : Activity() {

    var plugin: Plugin? = null
    var activityName: String? = null
    private var pluginAssetManager: AssetManager? = null
    private var pluginResources: Resources? = null
    private var pluginTheme: Resources.Theme? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pluginName = intent.getStringExtra(EXTRA_PLUGIN_NAME)
        if (pluginName.isNullOrBlank()) {
            throw Exception("Plugin name is invalid")
        }
        plugin = PluginsManager.getPlugin(pluginName) ?: throw Exception("Plugin not found")
        activityName = intent.getStringExtra(EXTRA_ACTIVITY_NAME)
            ?: throw Exception("Activity name is invalid")
        handlePluginResources()
    }

    override fun getResources(): Resources {
        return pluginResources ?: super.getResources()
    }

    override fun getAssets(): AssetManager {
        return pluginAssetManager ?: super.getAssets()
    }

    private fun handlePluginResources() {
        try {
            pluginAssetManager = AssetManager::class.java.newInstance()
            val addAssetPathMethod =
                pluginAssetManager?.javaClass?.getMethod("addAssetPath", String::class.java)
            addAssetPathMethod?.invoke(pluginAssetManager, plugin?.path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        pluginResources = Resources(
            pluginAssetManager,
            super.getResources().displayMetrics,
            super.getResources().configuration
        )
        pluginTheme = pluginResources?.newTheme()
        pluginTheme?.setTo(super.getTheme())
    }

    companion object {

        const val EXTRA_PLUGIN_NAME = "pluginName"
        const val EXTRA_ACTIVITY_NAME = "activityName"
    }
}