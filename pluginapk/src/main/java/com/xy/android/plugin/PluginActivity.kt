package com.xy.android.plugin

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.xy.android.plugin.databinding.ActivityMainBinding

class PluginActivity : BasePluginActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}