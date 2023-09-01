package com.xy.android.sdk.activity.task

import android.content.Intent
import android.os.Bundle
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityTaskEnterBinding

class TaskEnterActivity : BackActivity() {

    private lateinit var binding: ActivityTaskEnterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskEnterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.tvTaskInfo.text =
            "Activity: $this,\r\nhashCode:${hashCode()},\r\ntaskId: $taskId,\r\nisTaskRoot: $isTaskRoot"

        binding.btnStartTaskSingleInstanceActivity.setOnClickListener {
            val intent = Intent(this, TaskSingleInstanceActivity::class.java)
            startActivity(intent)

            binding.btnStartTaskSingleInstanceActivity.postDelayed({
                startActivity(intent)
            }, 3000)
        }

        binding.btnStartTaskSingleTaskActivity.setOnClickListener {
            val intent = Intent(this, TaskSingleTaskActivity::class.java)
            startActivity(intent)

            binding.btnStartTaskSingleTaskActivity.postDelayed({
                startActivity(intent)
            }, 3000)
        }

        binding.btnStartDocumentActivity.setOnClickListener {
            val intent = Intent(this, DocumentActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT) // 创建文档
//                .addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) // 统始终会以目标 Activity 为根来创建新任务
            startActivity(intent)
        }
    }
}