package com.xy.android.plugin.host

import android.app.Activity
import android.os.Bundle
import com.xy.android.plugin.databinding.ActivityMainBinding
import com.xy.android.plugin.host.PluginsManager.PLUGIN_NAME_PLUGIN

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val content = binding.content
        content.btnInstallPlugin.setOnClickListener {
            PluginsManager.installPlugin(PLUGIN_NAME_PLUGIN, assets.open("plugin.apk"))
        }
        content.btnUninstallPlugin.setOnClickListener {
            PluginsManager.uninstallPlugin(PLUGIN_NAME_PLUGIN)
        }
        content.btnToPluginActivity.setOnClickListener {
            ProxyActivity.start(this, PLUGIN_NAME_PLUGIN, "com.xy.android.plugin.PluginActivity")
        }
    }
}