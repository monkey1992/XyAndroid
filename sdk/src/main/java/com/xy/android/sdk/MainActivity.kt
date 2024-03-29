package com.xy.android.sdk

import android.content.Intent
import android.os.Bundle
import com.xy.android.sdk.bluetooth.BluetoothActivity
import com.xy.android.sdk.databinding.ActivityMainBinding
import com.xy.android.sdk.event.EventDispatchActivity
import com.xy.android.sdk.media.video.VideoPlayerActivity
import com.xy.android.sdk.storage.StorageActivity
import com.xy.android.sdk.activity.task.TaskEnterActivity
import com.xy.android.sdk.animation.AnimationActivity
import com.xy.android.sdk.view.ViewActivity

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val content = binding.content

        content.btnEventDispatch.setOnClickListener { view ->
            startActivity(Intent(this, EventDispatchActivity::class.java))
        }

        content.btnMediaPlayer.setOnClickListener { view ->
            startActivity(Intent(this, VideoPlayerActivity::class.java))
        }

        content.btnView.setOnClickListener { view ->
            startActivity(Intent(this, ViewActivity::class.java))
        }

        content.btnBluetooth.setOnClickListener { view ->
            startActivity(Intent(this, BluetoothActivity::class.java))
        }

        content.btnStorage.setOnClickListener { view ->
            startActivity(Intent(this, StorageActivity::class.java))
        }

        content.btnTask.setOnClickListener { view ->
            startActivity(Intent(this, TaskEnterActivity::class.java))
        }

        content.btnAnimation.setOnClickListener { view ->
            startActivity(Intent(this, AnimationActivity::class.java))
        }
    }
}