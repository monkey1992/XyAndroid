package com.xy.android.plugin.host

import android.app.Activity
import android.content.Intent

class ProxyActivity : Activity() {

    companion object {

        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, ProxyActivity::class.java))
        }
    }
}