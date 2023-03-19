package com.xy.android.plugin.host

/**
 * 插件
 * @param name 插件名称
 * @param classLoader 插件[ClassLoader]
 */
data class Plugin(
    val name: String,
    val classLoader: ClassLoader
)