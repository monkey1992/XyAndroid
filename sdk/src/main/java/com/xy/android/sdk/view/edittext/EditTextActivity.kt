package com.xy.android.sdk.view.edittext

import android.os.Bundle
import android.text.InputType
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityEditTextBinding

class EditTextActivity : BackActivity() {

    private lateinit var binding: ActivityEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // 代码实现
        // 密码
        binding.editTextCodePassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        // 数字
        binding.editTextCodeNumber.inputType = InputType.TYPE_CLASS_NUMBER

        // 小数
        binding.editTextCodeDecimal.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        // 负数
        binding.editTextCodeSigned.inputType =
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
    }
}