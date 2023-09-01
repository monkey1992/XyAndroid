package com.xy.android.sdk.activity.task

import android.content.Intent
import android.os.Bundle
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityDocumentBinding
import org.jetbrains.anko.toast

class DocumentActivity : BackActivity() {

    private lateinit var binding: ActivityDocumentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.tvTaskInfo.text =
            "Activity: $this,\r\nhashCode:${hashCode()},\r\ntaskId: $taskId,\r\nisTaskRoot: $isTaskRoot"

        toast("$this onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toast("$this onNewIntent")
    }
}