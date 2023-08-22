package com.xy.android.sdk.storage

import android.os.Bundle
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityStorageBinding

class StorageActivity : BackActivity() {

    private lateinit var binding: ActivityStorageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStorageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.btnWriteExternalStorage.setOnClickListener {
            getExternalFilesDir(null)
        }

        binding.btnReadExternalStorage.setOnClickListener {

        }
    }

    companion object {

        const val TAG = "StorageActivity"
    }
}