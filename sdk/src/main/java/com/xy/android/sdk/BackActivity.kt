package com.xy.android.sdk

open class BackActivity : BaseActivity() {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}