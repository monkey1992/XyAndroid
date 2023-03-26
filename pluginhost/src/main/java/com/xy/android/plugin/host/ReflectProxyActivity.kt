package com.xy.android.plugin.host

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import java.lang.reflect.Method

class ReflectProxyActivity : BaseProxyActivity() {

    private var clazz: Class<Activity>? = null
    private var pluginActivity: Activity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clazz = plugin?.classLoader?.loadClass(activityName) as? Class<Activity>
        pluginActivity = clazz?.newInstance()
        if (pluginActivity == null) {
            throw Exception("Plugin activity not found!")
        }
        getMethod("attach")?.invoke(pluginActivity)
        getMethod("onCreate", Bundle::class.java)?.invoke(pluginActivity, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        getMethod("onStart")?.invoke(pluginActivity)
    }

    override fun onResume() {
        super.onResume()
        getMethod("onResume")?.invoke(pluginActivity)
    }

    override fun onPause() {
        super.onPause()
        getMethod("onPause")?.invoke(pluginActivity)
    }

    override fun onStop() {
        super.onStop()
        getMethod("onStop")?.invoke(pluginActivity)
    }

    override fun onDestroy() {
        super.onDestroy()
        getMethod("onDestroy")?.invoke(pluginActivity)
    }

    private fun getMethod(name: String, vararg parameterTypes: Class<*>): Method? {
        return clazz?.getMethod(name, *parameterTypes)
    }

    companion object {

        fun start(activity: Activity, pluginName: String, activityName: String) {
            activity.startActivity(
                Intent(activity, ReflectProxyActivity::class.java)
                    .putExtra(EXTRA_PLUGIN_NAME, pluginName)
                    .putExtra(EXTRA_ACTIVITY_NAME, activityName)
            )
        }
    }
}