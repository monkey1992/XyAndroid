package com.xy.android.sdk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.xy.android.sdk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.content.btnEventStream.setOnClickListener { view ->
            Snackbar.make(view, "点击了 btn_event_stream 按钮", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }
}