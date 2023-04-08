package com.xy.android.sdk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.xy.android.sdk.databinding.ActivityMainBinding
import com.xy.android.sdk.event.EventDispatchActivity
import com.xy.android.sdk.media.video.VideoPlayerActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.content.btnEventDispatch.setOnClickListener { view ->
            startActivity(Intent(this, EventDispatchActivity::class.java))
        }
        binding.content.btnMediaPlayer.setOnClickListener { view ->
            startActivity(Intent(this, VideoPlayerActivity::class.java))
        }
    }
}