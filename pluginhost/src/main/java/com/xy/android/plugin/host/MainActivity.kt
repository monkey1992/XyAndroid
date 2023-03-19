package com.xy.android.plugin.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xy.android.plugin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.content.btnInstallPlugin.setOnClickListener {
            PluginsManager.installPlugin("plugin", assets.open("plugin.apk"))
        }

        binding.content.btnUninstallPlugin.setOnClickListener {
            PluginsManager.uninstallPlugin("plugin")
        }

        binding.content.btnToPluginActivity.setOnClickListener { view ->

        }
    }
}