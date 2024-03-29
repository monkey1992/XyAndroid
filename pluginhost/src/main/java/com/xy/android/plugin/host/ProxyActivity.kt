package com.xy.android.plugin.host

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.xy.android.plugin.IPluginActivity

class ProxyActivity : BaseProxyActivity() {

    private var pluginActivity: IPluginActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pluginActivity =
            plugin?.classLoader?.loadClass(activityName)?.newInstance() as? IPluginActivity
        if (pluginActivity == null) {
            throw Exception("Plugin activity not found!")
        }
        pluginActivity?.attach(this)
        pluginActivity?.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        pluginActivity?.onStart()
    }

    override fun onResume() {
        super.onResume()
        pluginActivity?.onResume()
    }

    override fun onPause() {
        super.onPause()
        pluginActivity?.onPause()
    }

    override fun onStop() {
        super.onStop()
        pluginActivity?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginActivity?.onDestroy()
    }

    companion object {

        fun start(activity: Activity, pluginName: String, activityName: String) {
            activity.startActivity(
                Intent(activity, ProxyActivity::class.java)
                    .putExtra(EXTRA_PLUGIN_NAME, pluginName)
                    .putExtra(EXTRA_ACTIVITY_NAME, activityName)
            )
        }
    }
}