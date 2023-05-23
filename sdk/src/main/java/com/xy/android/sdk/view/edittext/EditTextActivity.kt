package com.xy.android.sdk.view.edittext

import android.os.Bundle
import android.text.InputType
import android.text.method.NumberKeyListener
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

        // KeyListener 实现
        // 数字
        binding.editTextKeyListenerNumber.keyListener = object : NumberKeyListener() {
            override fun getInputType(): Int {
                // 返回 InputType
                return InputType.TYPE_CLASS_NUMBER
            }

            override fun getAcceptedChars(): CharArray {
                // 返回允许输入的内容
                return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            }
        }

        // 小数
        binding.editTextKeyListenerDecimal.keyListener = object : NumberKeyListener() {
            override fun getInputType(): Int {
                return InputType.TYPE_CLASS_NUMBER
            }

            override fun getAcceptedChars(): CharArray {
                return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.')
            }
        }

        // 负数
        binding.editTextKeyListenerSigned.keyListener = object : NumberKeyListener() {
            override fun getInputType(): Int {
                return InputType.TYPE_CLASS_NUMBER
            }

            override fun getAcceptedChars(): CharArray {
                return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-')
            }
        }

        // 字母
        binding.editTextKeyListenerLetter.keyListener = object : NumberKeyListener() {
            override fun getInputType(): Int {
                return InputType.TYPE_CLASS_TEXT
            }

            override fun getAcceptedChars(): CharArray {
                return charArrayOf(
                    'A', 'B', 'C', 'D', 'E', 'F', 'G',
                    'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U',
                    'V', 'W', 'X', 'Y', 'Z',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u',
                    'v', 'w', 'x', 'y', 'z',
                )
            }
        }
    }
}