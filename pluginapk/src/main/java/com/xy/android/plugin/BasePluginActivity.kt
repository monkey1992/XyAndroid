package com.xy.android.plugin

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater

open class BasePluginActivity : Activity(), IPluginActivity {

    private var proxyActivity: Activity? = null

    override fun attach(proxy: Activity) {
        proxyActivity = proxy
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        runWithProxyActivity({
            super.onCreate(savedInstanceState)
        })
    }

    override fun onStart() {
        runWithProxyActivity({
            super.onStart()
        })
    }

    override fun onResume() {
        runWithProxyActivity({
            super.onResume()
        })
    }

    override fun onPause() {
        runWithProxyActivity({
            super.onPause()
        })
    }

    override fun onStop() {
        runWithProxyActivity({
            super.onStop()
        })
    }

    override fun onDestroy() {
        runWithProxyActivity({
            super.onDestroy()
        })
    }

    override fun setContentView(layoutResID: Int) {
        runWithProxyActivity({
            super.setContentView(layoutResID)
        }) { setContentView(layoutResID) }
    }

    override fun getResources(): Resources? {
        return runWithProxyActivity({
            super.getResources()
        }) { resources }
    }

    override fun getTheme(): Resources.Theme? {
        return runWithProxyActivity({
            super.getTheme()
        }) { theme }
    }

    override fun getLayoutInflater(): LayoutInflater {
        return runWithProxyActivity({
            super.getLayoutInflater()
        }) { layoutInflater }!!
    }

    private fun <T> runWithProxyActivity(
        fallback: (() -> T),
        proxyActivityAction: (Activity.() -> T)? = null,
    ): T? {
        if (proxyActivity == null) {
            return fallback()
        }
        return proxyActivityAction?.invoke(proxyActivity!!)
    }
}