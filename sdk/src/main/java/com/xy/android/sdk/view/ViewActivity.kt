package com.xy.android.sdk.view

import android.content.Intent
import android.os.Bundle
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityViewBinding
import com.xy.android.sdk.view.edittext.EditTextActivity
import com.xy.android.sdk.view.webview.WebViewActivity

class ViewActivity : BackActivity() {

    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.btnEditText.setOnClickListener {
            startActivity(Intent(this, EditTextActivity::class.java))
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(this, WebViewActivity::class.java))
        }
    }
}