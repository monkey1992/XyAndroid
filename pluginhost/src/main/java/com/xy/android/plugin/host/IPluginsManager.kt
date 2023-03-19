package com.xy.android.plugin.host

import android.content.Context
import java.io.InputStream

/**
 * 插件管理接口
 */
interface IPluginsManager {

    /**
     * 初始化
     */
    fun init(context: Context)

    /**
     * 安装插件
     * @param name 插件名称
     * @param inputStream 输入流
     */
    fun installPlugin(name: String, inputStream: InputStream)

    /**
     * 卸载插件
     * @param name 插件名称
     */
    fun uninstallPlugin(name: String)
}