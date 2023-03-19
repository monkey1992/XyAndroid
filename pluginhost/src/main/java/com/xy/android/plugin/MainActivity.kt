package com.xy.android.plugin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.xy.android.plugin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.content.btnToPluginActivity.setOnClickListener { view ->
            Snackbar.make(view, R.string.plugin_host_to_plugin_activity, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}